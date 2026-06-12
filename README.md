# inventory-backend

API REST construida con **Spring Boot 3.4 · Java 17 · PostgreSQL**.

Provee autenticación JWT, control de acceso basado en roles (RBAC), gestión de inventario, ventas, órdenes de servicio, clientes, reportes JasperReports y auditoría.

---

## Tabla de Contenidos

1. [Requisitos](#requisitos)
2. [Configuración rápida](#configuración-rápida)
3. [Variables de entorno](#variables-de-entorno)
4. [Ejecución en desarrollo](#ejecución-en-desarrollo)
5. [Instalación directa en servidor (sin Docker)](#instalación-directa-en-servidor-sin-docker)
6. [Ejecución con Docker](#ejecución-con-docker)
7. [API Endpoints principales](#api-endpoints-principales)
8. [Swagger UI](#swagger-ui)
9. [Estructura del proyecto](#estructura-del-proyecto)
10. [Base de datos](#base-de-datos)
11. [Producción](#producción)

---

## Requisitos

| Herramienta | Versión mínima |
|---|---|
| Java (JDK) | 17 |
| Maven | 3.9+ (incluido el wrapper `mvnw`) |
| PostgreSQL | 14+ |
| Docker *(opcional)* | 24+ |

---

## Configuración rápida

```bash
# 1. Clonar el repositorio
git clone https://github.com/TU_USUARIO/inventory-backend.git
cd inventory-backend

# 2. Copiar el archivo de ejemplo y ajustar credenciales
cp .env.example .env
# Editar .env con tu editor preferido

# 3. Arrancar en desarrollo
./mvnw spring-boot:run          # Linux/macOS
mvnw.cmd spring-boot:run        # Windows
```

---

## Variables de entorno

Copia `.env.example` a `.env` y ajusta los valores:

| Variable | Descripción | Valor por defecto |
|---|---|---|
| `DB_URL` | JDBC URL de PostgreSQL | `jdbc:postgresql://localhost:5432/SERVI` |
| `DB_USER` | Usuario PostgreSQL | `postgres` |
| `DB_PASSWORD` | Contraseña PostgreSQL | *(vacío)* |
| `JWT_SECRET` | Clave secreta JWT (mínimo 256 bits) | *(requerida)* |
| `JWT_EXPIRATION_MS` | Expiración del token en ms | `36000000` (10 h) |
| `APP_CORS_ORIGINS` | Orígenes CORS permitidos (coma) | `http://localhost:5173` |
| `SERVER_PORT` | Puerto HTTP del servidor | `8080` |
| `REPORTS_STORAGE_PATH` | Ruta de almacenamiento de reportes | `./reports-storage` |
| `DDL_AUTO` | Estrategia Hibernate DDL | `update` |

> **Producción:** Establece `DDL_AUTO=validate` y gestiona el esquema con Flyway/Liquibase.

---

## Ejecución en desarrollo

```bash
# Compilar sin tests
./mvnw compile -DskipTests

# Ejecutar con Maven
./mvnw spring-boot:run

# Ejecutar jar empaquetado
./mvnw package -DskipTests
java -jar target/inventory-backend-*.jar
```

El servidor arranca en `http://localhost:8080`.

---

## Instalación directa en servidor (sin Docker)

Estas instrucciones dejan el backend corriendo como servicio `systemd` en Debian/Ubuntu. La aplicación ya soporta esta modalidad con `.env` y un `jar` empaquetado.

### 1. Instalar dependencias base

```bash
sudo apt update
sudo apt install -y openjdk-17-jre-headless postgresql-client curl
java -version
```

> Si PostgreSQL también vivirá en el mismo servidor, instala además `postgresql` y crea la base `SERVI`.

### 2. Empaquetar la aplicación

```bash
./mvnw package -DskipTests
```

El artefacto generado queda en `target/` con nombre `inventory-backend-<version>.jar`.

### 3. Crear estructura de despliegue

```bash
sudo useradd --system --home /opt/inventory-backend --shell /usr/sbin/nologin inventory
sudo mkdir -p /opt/inventory-backend /var/lib/inventory-backend/reports-storage
sudo chown -R inventory:inventory /opt/inventory-backend /var/lib/inventory-backend

sudo cp "$(find target -maxdepth 1 -name 'inventory-backend-*.jar' ! -name '*.original' | head -n1)" /opt/inventory-backend/app.jar
sudo cp .env.example /opt/inventory-backend/.env
sudo chown inventory:inventory /opt/inventory-backend/app.jar /opt/inventory-backend/.env
sudo chmod 640 /opt/inventory-backend/.env
```

### 4. Configurar variables

Edita `/opt/inventory-backend/.env` con valores reales:

```env
DB_URL=jdbc:postgresql://127.0.0.1:5432/SERVI
DB_USER=postgres
DB_PASSWORD=tu_password
JWT_SECRET=tu_secreto_jwt_muy_seguro_de_64_chars_o_mas
APP_CORS_ORIGINS=https://tu-frontend.com
DDL_AUTO=validate
SERVER_PORT=8080
REPORTS_STORAGE_PATH=/var/lib/inventory-backend/reports-storage
```

### 5. Registrar el servicio

El repositorio incluye una unidad base en `deploy/inventory-backend.service`.

```bash
sudo cp deploy/inventory-backend.service /etc/systemd/system/inventory-backend.service
sudo systemctl daemon-reload
sudo systemctl enable --now inventory-backend
```

### 6. Verificar

```bash
sudo systemctl status inventory-backend --no-pager
curl http://localhost:8080/actuator/health
curl http://localhost:8080/actuator/health/readiness
```

### 7. Logs

```bash
journalctl -u inventory-backend -f
```

---

## Ejecución con Docker

### Solo el backend (requiere PostgreSQL externo)

```bash
docker build -t inventory-backend .
docker run -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/SERVI \
  -e DB_USER=postgres \
  -e DB_PASSWORD=tu_password \
  -e JWT_SECRET=tu_secreto_jwt \
  inventory-backend
```

### Backend + PostgreSQL con Docker Compose

```bash
cp .env.example .env
# Editar .env
docker compose up -d
```

---

## API Endpoints principales

### Autenticación (público)

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/auth/login` | Obtener JWT |
| POST | `/auth/register` | Registrar usuario (admin) |

### Productos

| Método | Ruta | Rol requerido |
|---|---|---|
| GET | `/api/products/listar` | ADMIN, TECNICO, CLIENTE |
| POST | `/api/products/agregar` | ADMIN |
| PUT | `/api/products/actualizar/{id}` | ADMIN |
| DELETE | `/api/products/eliminar` | ADMIN |

### Ventas, Órdenes, Clientes, Reportes…

Ver documentación completa en [Swagger UI](#swagger-ui).

---

## Swagger UI

Con la aplicación corriendo, accede a:

```
http://localhost:8080/swagger-ui.html
http://localhost:8080/v3/api-docs
```

---

## Estructura del proyecto

```
src/main/java/com/inventory/
├── config/           # SecurityConfig, WebConfig, inicializadores
├── controller/       # Controladores REST (20 controladores)
├── dto/              # Data Transfer Objects
├── exception/        # Manejadores de excepciones globales
├── model/            # Entidades JPA
├── repository/       # Repositorios Spring Data JPA
├── service/          # Lógica de negocio
└── util/             # JwtFilter, JwtUtil, helpers

src/main/resources/
├── application.properties   # Configuración principal
├── db/migration/             # Scripts SQL (Flyway-ready)
└── reports/                  # Plantillas JasperReports (.jrxml)
```

---

## Base de datos

- Motor: **PostgreSQL 14+**
- Nombre por defecto: `SERVI`
- `spring.jpa.hibernate.ddl-auto=update` en desarrollo (crea/actualiza tablas automáticamente)
- Scripts de migración en `scripts/`:
  - `v2_venta_cliente_fk.sql`
  - `v3_rbac_expansion.sql`

---

## Producción

1. Configurar variables de entorno en el servidor/contenedor.
2. Cambiar `DDL_AUTO=validate`.
3. Generar secreto JWT seguro: `openssl rand -base64 64`.
4. Asegurarse de que `APP_CORS_ORIGINS` apunte al dominio del frontend.
5. Verificar `http://localhost:8080/actuator/health/readiness`.
6. Si se despliega sin Docker, registrar el backend como servicio `systemd`.
7. Activar HTTPS mediante un proxy inverso (Nginx, Traefik, etc.).

---

## Licencia

MIT
