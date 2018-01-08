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
        <%--<c:set var="catalogoSubastasV2" value="${requestScope.catalogoSubastas}" scope="request"/>--%>
        <div class="container-fluid">
            <p></p>
            <div class="row">
                <div class="col-lg-2 col-md-2 col-sm-1 hidden-xs">
                    <img src="<c:url value="/img/logo.png"/>" alt="logo" class="img-responsive" style="width:100%"/>
                </div>
                <div class="col-lg-8 col-md-8 col-sm-10 col-xs-12">
                    <div class="row">
                        <c:forEach items="${catalogoSubastas}" var="articulo">
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 catalogoSubastas text-center">
                                <div class="row">
                                    <img src="<c:url value="/img/subidas/articulos/${articulo.idArticulo}/${articulo.fotografias[0].fotografia}"/>" alt="${articulo.descripcionCorta}" class="img-responsive"/>
                                    <p>${articulo.descripcionCorta}</p>
                                    <p>Tiempo restante: </p>
                                    <div id="${articulo.idArticulo}"></div>
                                    <script>
                                        $("#${articulo.idArticulo}").countdown("${articulo.fechaFin}", function (event) {
                                            $(this).text(event.strftime('%D días y %H:%M:%S'));
                                        }).on('finish.countdown', function () {
                                            $(this).text("Ha finalizado");
                                        });
                                    </script>
                                    <a href="<c:url value="/SCatalogoSubastas?idSubasta=${articulo.idArticulo}&idCategoria=${param.idCategoria}"/>">
                                        <button class="btn btn-default botonVerde">Ir a la subasta</button>
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm1 hidden-xs">
                    <img src="<c:url value="/img/logo.png"/>" alt="logo" class="img-responsive" style="width:100%"/>
                </div>
            </div>
        </div>
        <c:import url="/jsp/inc/footer.jsp"/>
    </body>
</html>
