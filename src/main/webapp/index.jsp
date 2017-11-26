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
        <a href="jsp/registro.jsp">Registro</a><br/>
        <a href="jsp/login.jsp">Login</a><br/>
        <!--<a href="SCategorias">Cargar categorias en el contexto de la aplicacion</a><br/>-->
        <a href="jsp/simulacroCats.jsp">Ir a simulacro de categorias</a>
        <c:import url="/jsp/inc/footer.jsp"/>
    </body>
</html>
