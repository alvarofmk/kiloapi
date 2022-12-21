# KiloAPI

API Rest programada en Java con el framework [Spring boot](https://spring.io/projects/spring-boot), con base de datos en memoria h2. Documentación hecha con [OpenApi 3.0 y visualizada con Swagger-ui](https://swagger.io/specification/). Este repositorio consta también de una colección de [POSTMAN](https://www.postman.com/downloads/) para practicar sus endpoints.

## URL base

- http://localhost:8080

## ENDPOINTS

### Tipos de alimentos

```
GET http://localhost:8080/tipoAlimento/ -> Se obtiene todos los tipos de alimento.

POST http://localhost:8080/tipoAlimento/ -> Se crea un tipo de alimento.

PUT http://localhost:8080/tipoAlimento/{id} -> Se modifica un tipo de alimento existente.

DELETE http://localhost:8080/tipoAlimento/{id} -> Se borra un tipo de alimento existente.

GET http://localhost:8080/tipoAlimento/{id} -> Se obtienen los detalles de un tipo de alimento existente.
```

### Clases

```
GET http://localhost:8080/clase/ -> Se obtiene todas las clases.

POST http://localhost:8080/clase/ -> Se crea una clase.

PUT http://localhost:8080/clase/{id} -> Se modifica una clase existente.

DELETE http://localhost:8080/clase/{id} -> Se borra una clase existente.

GET http://localhost:8080/clase/{id} -> Se obtienen los detalles de una clase existente.
```

### Aportaciones

```
GET http://localhost:8080/aportacion/ -> Se obtienen todas las aportaciones.

GET http://localhost:8080/aportacion/clase/{idClase} -> Se obtienen todas las aportaciones de una clase existente.

POST http://localhost:8080/aportacion/ -> Se crea una aportación.

PUT http://localhost:8080/{id}/linea/{numLinea}/kg/{numKg} -> Se modifican los kilos de una línea concreta de una aportación.

DELETE http://localhost:8080/aportacion/{id} -> Se borra una aportación existente.

DELETE http://localhost:8080/aportacion/{id}/linea/{numLinea} -> Se borra una línea de una aportación existente.

GET http://localhost:8080/aportacion/{id} -> Se obtienen los detalles de una aportación existente.
```

### Kilos disponibles

```
GET http://localhost:8080/kilosDisponibles/ -> Se obtienen todos los kilos disponibles.

GET http://localhost:8080/kilosDisponibles/{idTipoAlimento} -> Se obtienen los kilos disponibles de un alimento existente.
```

### Ranking

```
GET http://localhost:8080/ranking/ -> Se obtiene una lista con las clases ordenadas por las aportaciones totales.
```

### Destinatarios

```
GET http://localhost:8080/destinatario/ -> Se obtienen todos los destinatarios.

POST http://localhost:8080/destinatario/ -> Se crea un destinatario.

PUT http://localhost:8080/destinatario/{id} -> Se modifica un destinatario existente.

DELETE http://localhost:8080/destinatario/{id} -> Se borra un destinatario existente.

GET http://localhost:8080/destinatario/{id} -> Se obtienen los detalles de un destinatario existente.

GET http://localhost:8080/destinatario/{id}/detalle -> Se obtienen los detalles de un destinatario, con los detalles de las cajas asignadas.
```

### Cajas

```
GET http://localhost:8080/caja/ -> Se obtienen todas las cajas.

POST http://localhost:8080/caja/ -> Se crea una caja.

POST http://localhost:8080/caja/{id}/tipo/{idTipoAlimento}/kg/{cantidad} -> Se añade a una caja existente una cantidad de un tipo de alimento.

PUT http://localhost:8080/caja/{id} -> Se modifica una caja existente.

PUT http://localhost:8080/caja/{id}/tipo/{tipoAlimento}/kg/{cantidad} -> Se modifica la cantidad de kilos de un tipo alimento en una caja existente.

DELETE http://localhost:8080/caja/{id} -> Se borra una caja existente.

DELETE http://localhost:8080/caja/{id}/tipo/{idTipoAlimento} -> Se borra un tipo de alimento de una caja existente.

GET http://localhost:8080/caja/{id} -> Se obtienen los detalles de una caja existente.
```

## Documentación de la API

- [Visualización de la documentación con SwaggerUI](http://localhost:8080/swagger-ui-apikilo.html)

## Colección POSTMAN

- Este repositorio cuenta con una colección POSTMAN para probar endpoints en formato [JSON](https://github.com/alvarofmk/Kiloapi/blob/master/Kilo.postman_collection.json)

## Créditos

- [Antonio Jimenez Infante](https://github.com/ProgramadorIV)

- [Álvaro Franco Martínez](https://github.com/alvarofmk)

- [Antonio Joaquín Montero García](https://github.com/Antoniomonforma99)

- [Carlos Jesús Durbán Viloca](https://github.com/Durbanban)

