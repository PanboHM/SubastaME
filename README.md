# SubastaME
Práctica SubastaME

### 2o commit 8 de Enero de 2018

* Se ha añadido más javadoc
* Se ha corregido un error que permitia insertar una puja inferior al importe Inicial si no habia ninguna puja en esa subasta

### Commit 8 de Enero de 2018

* Pueden visualizarse subastas
* Pueden realizarse pujas en subastas que no sean del subastador
* Puede verse un historial de subastas(finalizadas y sin finalizar) y pujas en el panel de control.

### Commit 16 de Diciembre de 2017

* Se ha cambiado la carga dinámica de categorías por AJAX a etiquetas JSTL, más eficiente.
* Se he creado una interfaz visual experimental
* Se ha añadido la posibilidad de crear una subasta una vez registrado (aún no pueden verse)
* Se ha optimizado código
* Panel de control:
 ** Cambio de contraseña
 ** Cambio de datos personales
 ** Cambio de avatar

### Commit 26 de Noviembre de 2017

#### UsuariosDAO
* Modificada la consulta sql del metodo login(ahora es correcta)
* Eliminada repetición de codigo en bloques "finally"

#### Nuevas funciones:

* Ahora el email solo se comprueba si está correctamente escrito

* Se ha añadido un Listener(es.jesushm.modelo.Inicio), éste se activa al inicio de la apliación y carga las categorías y características de la base de datos al contexto de la aplicación.
Se ha añadido el listener Inicio al web.xml

* Carga dinámica de las categorías por AJAX
* Carga dinámica de las características según categoría (más AJAX)
	Para estas dos características entran en juego los archivos: SCategorias.java(servlet) simulacroCats.jsp(vista)

#### Notas:
Para la transformacion del array en un arraryJSON hemos usado la dependencia: http://mavenrepository.com/artifact/org.json/json versión: 20160810

### Commit inicial: registro, login y ajax para comprobar usuario.