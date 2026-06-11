# inventory-backend

API REST construida con **Spring Boot 3.4 · Java 17 · PostgreSQL**.

Provee autenticación JWT, control de acceso basado en roles (RBAC), gestión de inventario, ventas, órdenes de servicio, clientes, reportes JasperReports y auditoría.

---

## Tabla de Contenidos

1. [Requisitos](#requisitos)
2. [Configuración rápida](#configuración-rápida)
3. [Variables de entorno](#variables-de-entorno)
4. [Ejecución en desarrollo](#ejecución-en-desarrollo)
5. [Ejecución con Docker](#ejecución-con-docker)
6. [API Endpoints principales](#api-endpoints-principales)
7. [Swagger UI](#swagger-ui)
8. [Estructura del proyecto](#estructura-del-proyecto)
9. [Base de datos](#base-de-datos)
10. [Producción](#producción)

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
6. Activar HTTPS mediante un proxy inverso (Nginx, Traefik, etc.).

---

## Licencia

MIT
