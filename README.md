# Cash Balancer API

API para gestión de rendiciones de caja para restaurantes.

## Estructura del Proyecto (Clean Architecture)

```
src/main/kotlin/com/osaka/cashbalancerapi/
├── CashBalancerApiApplication.kt   # Punto de entrada de la aplicación
├── config/                          # Configuraciones
│   ├── OpenApiConfig.kt            # Swagger/OpenAPI
│   └── AotConfig.kt                # AOT para GraalVM
├── entities/                        # Entidades de dominio (sin dependencias de framework)
│   ├── User.kt
│   └── CashReconciliation.kt
├── repositories/                    # Capa de persistencia
│   └── mongodb/                    # Spring Data MongoDB
│       ├── UserDocument.kt         # Documentos MongoDB
│       ├── CashReconciliationDocument.kt
│       ├── UserMongoRepository.kt  # Spring Data Repositories
│       └── CashReconciliationMongoRepository.kt
├── services/                        # Lógica de negocio
│   ├── interfaces/
│   │   └── ICashReconciliationService.kt
│   └── implementations/
│       └── CashReconciliationService.kt
└── shared/                          # Enums y tipos compartidos
    ├── Location.kt
    ├── Shift.kt
    └── ReconciliationStatus.kt
```

## Convenciones

- **Interfaces de servicios**: Prefijo `I` (ej: `ICashReconciliationService`)
- **Implementaciones**: Sin prefijo ni sufijo (ej: `CashReconciliationService`)
- **Entidades de dominio**: Sin dependencias de framework (puro Kotlin)
- **Documentos MongoDB**: Sufijo `Document` (ej: `UserDocument`)
- **Mapeo**: En los servicios con funciones de extensión privadas
- **Sin capa de repositorio**: Los servicios usan directamente Spring Data Repositories

## Tecnologías

- **Kotlin 2.2.21**
- **Spring Boot 3.5.9** (WebFlux - Reactive)
- **Java 24** (toolchain)
- **MongoDB Reactive**
- **Coroutines** (suspend functions)
- **java.time API** (LocalDate, LocalDateTime - JSR-310)
- **UUID** para IDs
- **SpringDoc OpenAPI 2.7.0**
- **JaCoCo** (coverage 80%)
- **GraalVM Native** (preparado para native image)

## Configuración

### application.yaml
```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/cash-balancer
```

### OpenAPI/Swagger
- Docs: http://localhost:8080/api-docs
- UI: http://localhost:8080/swagger-ui.html

## Compilar

```bash
./gradlew clean build
```

## Ejecutar

```bash
./gradlew bootRun
```

## Cobertura

```bash
./gradlew jacocoTestReport
# Ver reporte en: build/reports/jacoco/test/html/index.html
```

## Native Image

```bash
./gradlew nativeCompile
```

## Modelo de Dominio

### User
- Entidad simple que representa usuarios del sistema
- Pueden crear y cerrar reconciliaciones

### CashReconciliation
- Agregado raíz para reconciliaciones de caja
- **Estados**: DRAFT (editable) → CLOSED (read-only)
- **Campos requeridos**: businessDate, location, shift, createdBy
- **Campos opcionales**: todos los montos, observaciones, etc.
- **Reglas de negocio**:
  - Solo se puede editar en estado DRAFT
  - Solo se puede cerrar si está en DRAFT
  - Una vez CLOSED, es inmutable

## Enums

- **Location**: PUERTO_MADERO, COLEGIALES
- **Shift**: AM, PM
- **DeliveryPlatform**: RAPPI, PEDIDOS_YA, NORI_TACO
- **BigBoxExperienceType**: GRAND_CUISINE, PREMIADOS, XPS_BENTO_BOX, XS_TO_GO
- **InvoiceType**: A (empresas), B (consumidor final)

## Próximos Pasos

1. Implementar controllers (REST endpoints)
2. Agregar DTOs para request/response
3. Implementar validaciones
4. Agregar tests unitarios e integración
5. Configurar Spring Security
6. Implementar paginación y búsqueda

