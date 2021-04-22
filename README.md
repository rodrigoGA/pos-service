# Venta de productos - Evaluación Técnica

Se realizaron srevicios REST con springboot para cumplir con las propuesta de la evaluación técnica

## Consideraciones a tener en cuenta
* Se probee un archivo docker-compose para ejecutar la aplicación.
* Se utilizó mysql cómo motor de base de datos y H2 para los tests
* Se está utilizando liquidbase para la inicialización de la base de datos. 
* Los controles de concurrencia de stock se hacen bloqueando el numerador a nivel de base. De esta forma se probee una solución en la que se pueden escalar distitnas instancias de la aplicación sin que se conozcan.
* Según la descripción del problema sobre la auditoría parece que se quiere realizar más un un log de llamdadas que una auditoría de la aplicación. Entiendo que la idea era ver cómo solucionaba el problema de trabajar con distintas transacciones. Suponiendo esto lo que se hizo fue guardar el body plano de cada request al llegar una vetna.
De no ser así me gustaría conocer más sobre el problema para birndar una mejor solución.
* Se hizo stock y producto cómo entidades distitnas porque el modelo del problema así lo planteaba. Pero entiendo que solo puede existir una instancia de stock por producto. A la hora de crear el servicio de Productos el stock se trabajó cómo parte del producto.
* Es un proyecto muy simple, con una arquitectura en capas. Creo que para este problema utilizar algo más complejo cómo arquitectura hexagonal hubiese significado hacer una sobreingeniería

## Suposiciones
Se hicieron las siguientes suposiciones a la hora del desarrollo:
* La fecha al crear una  transacción se se toma como la actual, no es un parámetro del servicio
* No hay servicio de editar transacción, ni de eliminar. No sé si son necesarios.
* Cada vez que se crea un producto se inicializa con stock 10
* No hay operaciones de delete en ningún servicio, en caso de agregarlas en producto deberían ser delete lógico


## Cómo ejecutar el proyecto
Se pude ejecutar el proyecto de dos maneras

### Con Netbeans
En caso de usar netbeans:
* Abrir el proyecto
* Click derecho arriba del proyecto > Run Maven > Correr en compose

### Manual
El proyecto está dockerizado y con maven
* El primer paso es compilarlo con cualquier IDE java
* Luego ejecutar el comando `docker-compose up `

### Solución de problemas 
La primera vez que se levanta el docker-compose pude pasar que inicie el servicio de la aplicación antes de haber de terminado de crear la base, esto causa  una falla que la app no encuentra la base.
Basta con volver a repetir el paso y no vuelve a pasar.

## Uso de la API
Se inluyó documetnación de la API con OpenAPI. Una vez inicializada la aplicación entrar a http://localhost:8080/swagger-ui.html y se puden ver los detalles de cómo realizar las llamadas


## Query para ventas
Entiendo que lo que se pide es obtener la cantidad de ventas realizadas para cada producto en un período de fechas, una forma de hacer esto es la siguietne:
```sql
SELECT p.id as 'Producto ID', SUM(l.total) as 'Total'
FROM productos p 
JOIN lineas l ON l.producto_id = p.id
JOIN transaciones t on t.numero = l.transaccion_numero
WHERE t.fecha between ...  and  ....
GROUP BY p.id
```
### Optimización
Se podría agregar un índice por fecha en la base.
En caso de ser muchos datos incluso se podría particionar la tabla por fecha
