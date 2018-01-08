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
        <%@include file="inc/ifNotLogin.jsp" %>
        <c:import url="/jsp/inc/navBar.jsp"/>
        <div class="container-fluid text-center">    
            <h2>CATEGORÍAS</h2>
            <div class="row">
                <c:forEach items="${applicationScope.categorias}" var="categoria">
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                        <h2>${categoria.denominacion}</h2>
                        <a href="<c:url value="/SCatalogoSubastas?idCategoria=${categoria.idCategoria}&denominacionSelec=${categoria.denominacion}"/>">
                            <img src="<c:url value="/img/categorias/${categoria.imagen}"/>" class="img-responsive" style="width:100%" alt="${categoria.denominacion}"/>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
        <c:import url="/jsp/inc/footer.jsp"/>
    </body>
</html>
