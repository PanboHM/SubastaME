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
        <c:out value="${requestScope.mensaje}" default="no hay mensaje?"/>
        <a href="<c:url value="/index.jsp"/>">Inicio</a>
        <c:import url="/jsp/inc/footer.jsp"/>
    </body>
</html>
