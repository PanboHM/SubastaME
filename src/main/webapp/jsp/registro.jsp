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
                $("#email").on("blur", function () {
                    $.post("<c:url value='/RegistroLogin'/>", {
                        datoEmail: $("#email").val()
                    }, function (respuesta) {
                        if (respuesta == "true") {
                            $("#enUso").html("<p class='bg-danger'>Usuario en uso</p>");
                        } else {
                            $("#enUso").html("<p class='bg-success'>Usuario disponible</p>");
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <h1>Registro</h1>
        <form action="<c:url value="/RegistroLogin"/>" method="post">
            <fieldset>
                <legend>Datos de acceso</legend>
                <label for="email">Correo electrónico: </label>
                <input type="mail" name="email" id="email" required="" class="form-control"/>
                <div id="enUso"></div>
                <label for="password">Contraseña: </label>
                <input type="password" name="password" id="password" required="" class="form-control"/>
                <label for="password">Confirmar contraseña: </label>
                <input type="password" name="passwordA" id="passwordA" required="" class="form-control"/>
            </fieldset>
            <fieldset>
                <legend>Datos personales</legend>
                <label for="nombre">Nombre: </label>
                <input type="text" name="nombre" id="nombre" required="" class="form-control"/>
                <label for="apellido1">Primer apellido: </label>
                <input type="text" name="apellido1" id="apellido1" required="" class="form-control"/>
                <label for="apellido2">Segundo apellido: </label>
                <input type="text" name="apellido2" id="apellido2" class="form-control"/>
                <label for="nif">DNI:</label>
                <input type="text" name="nif" id="nif" required="" class="form-control"/>
                <label for="direccion">Direccion: </label>
                <input type="text" name="direccion" id="direccion" required="" class="form-control"/>
                <label for="telefono">Teléfono: </label>
                <input type="tel" name="telefono" id="telefono" class="form-control"/>
            </fieldset>
            <input type="reset" class="btn btn-default"/> 
            <button type="submit" name="enviar" value="registro" class="btn btn-default">Registrarse</button>
        </form> 
        <c:import url="/jsp/inc/footer.jsp"/>
    </body>
</html>
