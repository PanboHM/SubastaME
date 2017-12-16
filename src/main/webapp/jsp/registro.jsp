<%-- 
    Document   : index
    Created on : 17-nov-2017, 15:27:46
    Author     : jesus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/inc/meta.jsp"/>
        <c:import url="/jsp/inc/header.jsp"/>
        <script>
            $(document).ready(function () {
                $("#email").on("blur", function () { //al quitar el foco de "usuario" este metodo comprueba su disponibilidad en la base de datos
                    if (/^[a-z0-9][a-z0-9._%+-]*@[a-z0-9.-]+\.[a-z]{2,4}$/.test($(this).val())) { //antes de llamar a AJAX se comprueba si el email es correcto
                        $.post("<c:url value='/RegistroLogin'/>", {//lama al servlet que le responderá "true" o "false"
                            datoEmail: $(this).val()
                        }, function (respuesta) { //en funcion de la respuesta se mostrará un mensaje u otro
                            if (respuesta == "true") {
                                $("#enUso").html("<p class='bg-danger'>Usuario en uso</p>");
                            } else {
                                $("#enUso").html("<p class='bg-success'>Usuario disponible</p>");
                            }
                        });
                    } else { //si el email no es correcto se le indica al usuario
                        $("#enUso").html("<p class='bg-warning'>Formato no correcto</p>");
                    }
                });
            });
        </script>
    </head>
    <body>
        <c:import url="/jsp/inc/navBar.jsp"/>
        <c:import url="/jsp/inc/modalLogin.jsp"/>
        <div class="row">
            <div class="col-lg-4 col-md-4 col-sm-4 hidden-xs">
                <img src="<c:url value="/img/logo.png"/>" alt="logo" class="img-responsive" style="width:100%"/>
            </div>
            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
                <h1>Registro</h1>
                <c:if test="${requestScope.mensajeError!=null}">
                    <div class="alert alert-danger">
                        <p><strong>¡Atencion!</strong> Ha habido un error al introducir los datos.</p>
                        ${requestScope.mensajeError}
                    </div>
                </c:if>
                <form action="<c:url value="/RegistroLogin"/>" method="post">
                    <fieldset>
                        <legend>Datos de acceso</legend>
                        <div class="form-group">
                            <label for="email">* Correo electrónico: </label>
                            <input type="mail" name="email" value="${usuario.email}" id="email" required="" class="form-control" pattern="^[a-z0-9][a-z0-9._%+-]*@[a-z0-9.-]+\.[a-z]{2,4}$" title="example@example.com"/>
                        </div>
                        <div id="enUso"></div>
                        <div class="form-group">
                            <label for="password">* Contraseña: </label>
                            <input type="password" name="password" id="password" required="" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="passwordA">* Confirmar contraseña: </label>
                            <input type="password" name="passwordA" id="passwordA" required="" class="form-control"/>
                        </div>
                    </fieldset>
                    <fieldset>
                        <legend>Datos personales</legend>
                        <div class="form-group">
                            <label for="nombre">* Nombre: </label>
                            <input type="text" name="nombre" value="${usuario.clienteU.nombre}" id="nombre" required="" class="form-control" pattern="^([A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]{0,8}[\s]?){1,2}$" title="De la A a la Z máximo 20 carácteres"/>
                        </div>
                        <div class="form-group">
                            <label for="apellido1">* Primer apellido: </label>
                            <input type="text" name="apellido1" value="${usuario.clienteU.apellido1}" id="apellido1" required="" class="form-control" pattern="^[A-ZÁÉÍÓÚ]{1}[a-zñáéíóú ]{0,19}$" title="De la A a la Z máximo 20 carácteres"/>
                        </div>
                        <div class="form-group">
                            <label for="apellido2">Segundo apellido: </label>
                            <input type="text" name="apellido2" value="${usuario.clienteU.apellido2}" id="apellido2" class="form-control" pattern="^[A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]{0,19}$" title="De la A a la Z máximo 20 carácteres"/>
                        </div>
                        <div class="form-group">
                            <label for="nif">* DNI:</label>
                            <input type="text" name="nif" value="${usuario.clienteU.nif}" id="nif" required="" class="form-control" pattern="^[\d]{8}[TRWAGMYFPDXBNJZSQVHLCKET]{1}$" title="8 números seguidos de una letra mayúscula"/>
                        </div>
                        <div class="form-group">
                            <label for="direccion">* Direccion: </label>
                            <input type="text" name="direccion" value="${usuario.clienteU.direccion}" id="direccion" required="" class="form-control" pattern="^[A-ZÁÉÍÓÚa-zñáéíóú0-9 ]{1,45}$" title="Máximo 45 caracteres"/>
                        </div>
                        <div class="form-group">
                            <label for="telefono">Teléfono: </label>
                            <input type="tel" name="telefono" value="${usuario.clienteU.telefono}" id="telefono" class="form-control" pattern="^[9|6|7]{1}[\d]{8}$"/>
                        </div>
                    </fieldset>
                    <button type="reset" class="btn btn-default botonNaranja">Borrar</button>
                    <button type="submit" name="enviar" value="registro" class="btn btn-default botonVerde">Registrarse</button>
                </form> <br/>
            </div>
            <div class="col-lg-4 col-md-4 col-sm-4 hidden-xs">
                <img src="<c:url value="/img/logo.png"/>" alt="logo" class="img-responsive" style="width:100%"/>
            </div>
        </div>
        <c:import url="/jsp/inc/footer.jsp"/>
    </body>
</html>
