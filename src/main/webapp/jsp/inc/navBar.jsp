<%-- 
    Document   : navBar
    Created on : 29-nov-2017, 15:31:46
    Author     : jesus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Barra de navegación -->
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>                        
            </button>
            <a class="navbar-brand" href="<c:url value="/Cruce?donde=index"/>" style="color: #74D14C">SubástaME</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav" >
                <c:choose>
                    <c:when test="${sessionScope.usuario!=null}">
                        <li><a href="<c:url value="/Cruce?donde=crearSubasta"/>">Crear Subasta</a></li>
                        <li><a href="<c:url value="/Cruce?donde=indexCatalogo"/>">Catálogo de subastas</a></li>
                        </c:when>
                        <c:otherwise>
                        <li><a href="#" data-toggle="popover" data-placement="bottom" data-trigger="focus" data-content="¡Necesitas iniciar sesión para acceder a esta función!">Crear Subasta</a></li>
                        <li><a href="#" data-toggle="popover" data-placement="bottom" data-trigger="focus" data-content="¡Necesitas iniciar sesión para acceder a esta función!">Catálogo de subastas</a></li>
                        </c:otherwise>
                    </c:choose>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${sessionScope.usuario!=null}">
                        <li><a href="<c:url value="/PrevControlPanel"/>"><span class="glyphicon glyphicon-tasks"></span> Panel de Control</a></li>
                        <li><a href="<c:url value="/RegistroLogin?enviar=logout"/>"><span class="glyphicon glyphicon-log-out"></span> Salir</a></li>
                        </c:when>
                        <c:otherwise>
                        <li><a href="<c:url value="/Cruce?donde=registro"/>"><span class="glyphicon glyphicon-user"></span> Regístrate</a></li>
                        <li><a href="#" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-log-in"></span> Entra</a></li>
                        </c:otherwise>
                    </c:choose>
            </ul>
        </div>
    </div>
</nav>
