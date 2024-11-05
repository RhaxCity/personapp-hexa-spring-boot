# personapp-hexa-spring-boot
## Proyecto de Arquitectura Hexagonal

## Propósito
Este proyecto implementa una aplicación utilizando la arquitectura hexagonal, permitiendo la interacción a través de dos adaptadores de entrada (CLI y REST) y soportando dos bases de datos (MariaDB y MongoDB). La arquitectura hexagonal facilita la separación entre la lógica de negocio y los adaptadores, permitiendo cambiar los tipos de entrada/salida y la base de datos de manera flexible sin afectar la lógica central.

## Requisitos
- Java 11+
- Docker y Docker Compose

## Ejecución

1. **Levantar los servicios de bases de datos con Docker:**
   ```bash
   docker-compose up --build

Esto iniciará los contenedores de MariaDB y MongoDB.


bash
2. **Ejecutar el adaptador REST:**
    ```bash
    java -jar rest-input-adapter/target/rest-input-adapter-0.0.1-SNAPSHOT.jar

La API REST estará disponible en http://localhost:3000, y la documentación Swagger en http://localhost:3000/swagger-ui.html.

3. **Ejecutar el adaptador CLI:**
    ```bash
    java -jar cli-input-adapter/target/cli-input-adapter-0.0.1-SNAPSHOT.jar
    
Esto abrirá la interfaz de línea de comandos, permitiendo la gestión de datos directamente desde el terminal.
Con esta configuración, puedes seleccionar entre los adaptadores de entrada y las bases de datos para interactuar con la aplicación, según la arquitectura hexagonal implementada.