# Hotel Sling - API de Disponibilidad de Hoteles

Sistema distribuido para buscar disponibilidad de hoteles y contar búsquedas similares.

## ¿Qué hace?

1. **Buscador (Puerto 8080)**: Recibe búsquedas → valida checkIn < checkOut → envía a Kafka
2. **Contador (Puerto 8081)**: Consume Kafka → guarda en PostgreSQL → cuenta búsquedas similares

## Arquitectura

```
[Cliente] → POST /api/v1/searches (8080) → Kafka → GET /api/v1/searches/count (8081) → PostgreSQL
```

## Puertos y Servicios

| Servicio | Puerto | URL |
|----------|--------|-----|
| Buscador API | 8080 | http://localhost:8080 |
| Contador API | 8081 | http://localhost:8081 |
| Kafka UI | 8082 | http://localhost:8082 |
| Adminer (PostgreSQL) | 8083 | http://localhost:8083 |
| Kafka | 9092 | http://localhost:9092 |
| PostgreSQL | 5432 | jdbc:postgresql://localhost:5432/hotel_db |

## Endpoints

### POST /api/v1/searches (Puerto 8080)

Crea una búsqueda de disponibilidad.

```bash
curl -X POST http://localhost:8080/api/v1/searches \
  -H "Content-Type: application/json" \
  -d '{
    "hotelId": "1234aBc",
    "checkIn": "29/12/2023",
    "checkOut": "31/12/2023",
    "ages": [30, 29, 1, 3]
  }'
```

Response:
```json
{ "searchId": "uuid-generado" }
```

### GET /api/v1/searches/count?searchId={id} (Puerto 8081)

Retorna el conteo de búsquedas similares.

```bash
curl "http://localhost:8081/api/v1/searches/count?searchId=UUID-AQUI"
```

Response:
```json
{
  "searchId": "uuid",
  "search": {
    "hotelId": "1234aBc",
    "checkIn": "2023-12-29",
    "checkOut": "2023-12-31",
    "ages": [1, 3, 29, 30]
  },
  "count": 5
}
```

## Datos en Base de Datos

**Tabla:** `search_availability`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| search_id | VARCHAR | UUID único de la búsqueda |
| hotel_id | VARCHAR | Identificador del hotel |
| check_in | DATE | Fecha de entrada |
| check_out | DATE | Fecha de salida |
| ages | TEXT | Edades separadas por coma |

## Validaciones

- checkIn debe ser anterior a checkOut
- hotelId es obligatorio
- ages debe tener al menos un elemento
- Formato de fecha: dd/MM/yyyy

## Reglas de Negocio

- Dos búsquedas son iguales si tienen el mismo hotelId, checkIn, checkOut y conjunto de ages (orden no importa)
- El conteo incluye la búsqueda de referencia

## Documentación Swagger

- Buscador: http://localhost:8080/swagger-ui.html
- Contador: http://localhost:8081/swagger-ui.html

## Interfaces Gráficas

### Kafka UI (Puerto 8082)
- **URL:** http://localhost:8082
- Ver topics, mensajes, consumers y configuración de Kafka

### Adminer - PostgreSQL (Puerto 8083)
- **URL:** http://localhost:8083
- **Servidor:** postgres
- **Usuario:** postgres
- **Contraseña:** postgres
- **Base de datos:** hotel_db

## Consultar Topic de Kafka

```bash
# Ver topics disponibles
docker exec hotel-kafka kafka-topics --list --bootstrap-server localhost:9092

# Ver mensajes del topic
docker exec hotel-kafka kafka-console-consumer --topic hotel_availability_searches --from-beginning --bootstrap-server localhost:9092
```

## Levantar el Proyecto

```bash
# Desde la raíz del proyecto
docker-compose up -d --build
```

## Tecnologías

| Componente | Versión |
|------------|---------|
| Java | 21 |
| Spring Boot | 3.2.x |
| Gradle | 8.5 |
| Kafka | 7.5.0 |
| PostgreSQL | 16 |
| Jacoco | 0.8.11 |

## Tests y Coverage

```bash
# Ejecutar tests
cd microservicio-buscador && ./gradlew test
cd microservicio-contador && ./gradlew test

# Generar reporte de coverage
./gradlew jacocoTestReport
```

**Coverage:**
- Buscador: 87%
- Contador: 91%
