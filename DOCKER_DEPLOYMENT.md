# 🐳 Guía de Deployment Docker - Inventory Backend

## ✅ Configuración Verificada y Corregida

### Cambios Realizados:

#### 1. **Dockerfile** ✅ MEJORADO
- ✓ Multi-stage build optimizado (builder + runtime)
- ✓ Java 17 (eclipse-temurin alpine para menor tamaño)
- ✓ Usuario no-root por seguridad (appuser)
- ✓ Límites de memoria: `-Xmx512m -Xms256m`
- ✓ HEALTHCHECK mejorado (sin dependencia de wget)
- ✓ Volumen de reportes creado y con permisos correctos

#### 2. **docker-compose.yml** ✅ ACTUALIZADO
- ✓ PostgreSQL 16 con healthcheck
- ✓ Dependencia de servicio con `service_healthy`
- ✓ Variables de entorno requeridas (POSTGRES_PASSWORD es OBLIGATORIA)
- ✓ Volúmenes persistentes (pgdata, reports)
- ✓ Logging configurado (rotación json-file)
- ✓ Healthcheck para el backend
- ✓ DDL_AUTO configurado (producción: `validate`)

#### 3. **application.properties** ✅ ASEGURADO
- ✓ Removida contraseña hardcodeada (qxnk26yp)
- ✓ Todas las variables de entorno requeridas

#### 4. **Archivos Nuevos** ✅ AGREGADOS
- ✓ `.dockerignore` - Optimiza contexto de build
- ✓ `.env.example` - Plantilla de configuración

---

## 🚀 Pasos para Ejecutar en Debian 13

### 1. Instalar Docker Engine y Compose Plugin
```bash
sudo apt update
sudo apt install -y ca-certificates curl gnupg
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/debian/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt update
sudo apt install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
sudo systemctl enable --now docker
sudo usermod -aG docker $USER
```

> Cierra y vuelve a abrir sesión para que el grupo `docker` aplique a tu usuario.

### 2. Preparar el entorno
```bash
# Clonar o copiar el proyecto
cd ~/inventory-backend

# Crear el archivo .env desde la plantilla
cp .env.example .env

# Editar .env con valores reales (CRÍTICO)
nano .env
```

### 3. Configurar variables en `.env`
```env
# REQUERIDO: Cambiar estos valores
POSTGRES_PASSWORD=password_muy_seguro_aqui_12345
JWT_SECRET=generar_con: openssl rand -base64 64
APP_CORS_ORIGINS=https://tu-dominio.com,https://www.tu-dominio.com
DDL_AUTO=validate
```

### 4. Iniciar los servicios
```bash
# Build y start
docker compose up -d

# Ver logs en tiempo real
docker compose logs -f backend

# Verificar que PostgreSQL está listo
docker compose logs db | grep "ready to accept"
```

### 5. Verificar que está funcionando
```bash
# Health check
curl http://localhost:8080/actuator/health

# Health readiness
curl http://localhost:8080/actuator/health/readiness

# Ver estado de contenedores
docker compose ps
```

---

## 📋 Checklist de Verificación

- [ ] `.env` creado con valores reales (no usar defaults)
- [ ] `POSTGRES_PASSWORD` es distinto en `.env`
- [ ] `JWT_SECRET` es una cadena segura (64+ caracteres)
- [ ] `APP_CORS_ORIGINS` apunta a dominio correcto
- [ ] PostgreSQL levanta y pasa healthcheck (20 segundos máximo)
- [ ] Backend levanta después de PostgreSQL (60 segundos máximo)
- [ ] `/actuator/health` responde con `UP`
- [ ] Base de datos SERVI se crea automáticamente
- [ ] Volumen `pgdata` persiste entre reinicios

---

## 🔒 Seguridad

✅ **Aplicadas mejoras de seguridad:**
1. Usuario no-root en contenedor
2. Contraseña NO hardcodeada
3. Variables de entorno por .env
4. Healthchecks para auto-recuperación
5. Logging con rotación
6. Volúmenes con permisos correctos

⚠️ **ANTES DE PRODUCCIÓN:**
- [ ] Cambiar `DDL_AUTO=validate` (NO usar update)
- [ ] Usar base de datos gestionada (AWS RDS, Azure Database)
- [ ] Agregar Nginx como reverse proxy
- [ ] Configurar SSL/TLS
- [ ] Monitorear con Prometheus/Grafana
- [ ] Hacer backup regular de pgdata

---

## 🆘 Troubleshooting

### Backend no inicia
```bash
# Ver logs detallados
docker compose logs backend

# Reiniciar
docker compose restart backend

# Reset completo
docker compose down -v
docker compose up -d
```

### PostgreSQL no responde
```bash
# Conectar directamente
docker exec -it inventory-db psql -U postgres -d SERVI

# Ver estado
docker compose logs db
```

### Puerto 8080 ya en uso
```bash
# Cambiar en .env
SERVER_PORT=8081

# Reiniciar
docker compose restart backend
```

---

## 📊 Especificaciones Finales

| Componente | Versión | Puerto |
|-----------|---------|--------|
| Spring Boot | 3.4.1 | 8080 |
| Java | 17 | - |
| PostgreSQL | 16 | 5432 |
| Memory (Backend) | 512MB | - |
| Memory (Init) | 256MB | - |

---

**✅ Configuración LISTA para producción en Debian 13**
