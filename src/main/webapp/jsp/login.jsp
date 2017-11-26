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
    </head>
    <body>
        <h1>LOGIN: </h1>
        <form action="<c:url value="/RegistroLogin"/>" method="post" id="login">
            <fieldset>
                <label for="email">Correo electrónico: </label>
                <input type="mail" name="email" id="email" required="" class="form-control"/>
                <label for="password">Contraseña: </label>
                <input type="password" name="password" id="password" required="" class="form-control"/>
                <input type="reset"/> 
                <button type="submit" name="enviar" value="login">Logear</button>
        </form> 
        <c:import url="/jsp/inc/footer.jsp"/>
    </body>
</html>
