# SubastaME
Práctica SubastaME

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