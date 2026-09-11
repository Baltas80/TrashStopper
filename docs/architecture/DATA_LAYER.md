# Trash Stopper — Data Layer

## Objetivo
La aplicación debe poder identificar y decidir sobre una llamada sin depender de una red durante los segundos críticos de screening.

## Capas
- **Room/local DB:** números, reputación cacheada, reglas del usuario y eventos.
- **Repository:** fuente única para UI, screening y sincronización.
- **Sync worker:** actualización incremental cuando existe conectividad.
- **API:** reputación remota y recepción de reportes, sin ser dependencia del flujo de llamada.

## Principios
1. Local-first para decisiones de llamada.
2. Los hashes de número se usan cuando sea suficiente para reducir exposición.
3. Sin datos personales innecesarios.
4. Migraciones versionadas y testeadas.
5. La UI no accede directamente a Room.

## Contrato de fallo
Si la red falla, la base local sigue siendo operativa y la protección continúa con la última reputación disponible.
