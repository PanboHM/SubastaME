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
        <c:import url="/jsp/inc/navBar.jsp"/>
        <div class="container text-center">
            <div class="alert alert-${requestScope.status}">
                <p><c:out value="${requestScope.mensaje}" default="No hay mensaje?"/></p>
            </div>
            <a href="<c:url value="/index.jsp"/>"><button type="button" class="btn btn-default botonVerde">Inicio</button></a>
        </div>
        <c:import url="/jsp/inc/footer.jsp"/>
    </body>
</html>
