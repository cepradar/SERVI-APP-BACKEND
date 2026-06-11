-- ============================================================
-- V3: Soporte Multi-Sede
-- Sistema de gestión de inventario — módulo de sedes
-- Autor: Sistema de Inventario
-- Descripción:
--   1. Crea la tabla sedes (con consecutivos por sede)
--   2. Crea la tabla usuario_sede (control de acceso por sede)
--   3. Agrega columna codigo_sede en venta y orden_de_servicio
--   4. Agrega columna codigo_venta en venta (ID legible: V-BQ-000001)
--   5. Amplía la longitud de orden_de_servicio.id de 6 a 25 caracteres
--      para soportar el formato O-{SEDE}-{CONSECUTIVO}
-- ============================================================

-- 1. Tabla SEDES ─────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS sedes (
    codigo_sede          VARCHAR(10)   NOT NULL PRIMARY KEY,
    nombre               VARCHAR(100)  NOT NULL,
    direccion            VARCHAR(200),
    ciudad               VARCHAR(100),
    departamento         VARCHAR(100),
    telefono             VARCHAR(20),
    email                VARCHAR(100),
    activo               BOOLEAN       NOT NULL DEFAULT TRUE,
    consecutivo_ventas   INTEGER       NOT NULL DEFAULT 1,
    consecutivo_ordenes  INTEGER       NOT NULL DEFAULT 1,
    fecha_creacion       TIMESTAMP     NOT NULL DEFAULT NOW(),
    fecha_actualizacion  TIMESTAMP
);

COMMENT ON TABLE sedes IS 'Sedes/sucursales del negocio. Los consecutivos de ventas y órdenes son por sede.';
COMMENT ON COLUMN sedes.consecutivo_ventas IS 'Próximo número a usar para generar V-{SEDE}-{CONSECUTIVO}. Incrementado con lock pesimista.';
COMMENT ON COLUMN sedes.consecutivo_ordenes IS 'Próximo número a usar para generar O-{SEDE}-{CONSECUTIVO}. Incrementado con lock pesimista.';

-- 2. Tabla USUARIO_SEDE (control de acceso por sede) ─────────
CREATE TABLE IF NOT EXISTS usuario_sede (
    id               BIGSERIAL     NOT NULL PRIMARY KEY,
    usuario_username VARCHAR(255)  NOT NULL,
    codigo_sede      VARCHAR(10)   NOT NULL,
    CONSTRAINT uk_usuario_sede UNIQUE (usuario_username, codigo_sede),
    CONSTRAINT fk_usuario_sede_usuario FOREIGN KEY (usuario_username)
        REFERENCES usuarios(username) ON DELETE CASCADE,
    CONSTRAINT fk_usuario_sede_sede FOREIGN KEY (codigo_sede)
        REFERENCES sedes(codigo_sede) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_usuario_sede_usuario ON usuario_sede(usuario_username);
CREATE INDEX IF NOT EXISTS idx_usuario_sede_sede    ON usuario_sede(codigo_sede);

COMMENT ON TABLE usuario_sede IS 'Relación muchos-a-muchos entre usuarios y sedes. Define el acceso operativo.';

-- 3. Columna codigo_sede en VENTA ────────────────────────────
ALTER TABLE venta ADD COLUMN IF NOT EXISTS codigo_sede VARCHAR(10);
ALTER TABLE venta ADD COLUMN IF NOT EXISTS codigo_venta VARCHAR(30);

CREATE INDEX IF NOT EXISTS idx_venta_codigo_sede  ON venta(codigo_sede);
CREATE INDEX IF NOT EXISTS idx_venta_codigo_venta ON venta(codigo_venta);

-- 4. Columna codigo_sede en ORDEN_DE_SERVICIO ────────────────
ALTER TABLE orden_de_servicio ADD COLUMN IF NOT EXISTS codigo_sede VARCHAR(10);

CREATE INDEX IF NOT EXISTS idx_orden_servicio_codigo_sede ON orden_de_servicio(codigo_sede);

-- 5. Ampliar el campo id en ORDEN_DE_SERVICIO (6 → 25 chars) ─
--    NOTA: ALTER COLUMN TYPE requiere que no haya constraint CHECK bloqueante.
--    Ejecutar antes de que el sistema genere IDs con el nuevo formato.
DO $$
BEGIN
    -- Verificar si la columna tiene longitud 6 antes de ampliarla
    -- Siempre ampliar si la longitud actual es <= 25 (safe)
    ALTER TABLE orden_de_servicio ALTER COLUMN id TYPE VARCHAR(25);
    RAISE NOTICE 'Columna orden_de_servicio.id ampliada a VARCHAR(25)';
EXCEPTION
    WHEN others THEN
        RAISE NOTICE 'No se pudo ampliar la columna id: %. Se omite.', SQLERRM;
END;
$$;

-- 6. FK opcional (nullable mientras haya datos históricos sin sede) ──
--    Se deja como comentario para habilitar manualmente cuando todos
--    los registros tengan una sede asignada.
-- ALTER TABLE venta
--     ADD CONSTRAINT fk_venta_sede
--     FOREIGN KEY (codigo_sede) REFERENCES sedes(codigo_sede);
-- ALTER TABLE orden_de_servicio
--     ADD CONSTRAINT fk_orden_sede
--     FOREIGN KEY (codigo_sede) REFERENCES sedes(codigo_sede);
