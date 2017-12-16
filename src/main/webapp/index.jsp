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
            <c:if test="${requestScope.mensajeError!=null}">
                $("#myModal").on("shown.bs.modal", function () {
                    $("#passwordModal").focus();
                });
                $("#myModal").modal();
            </c:if>
            });
        </script>
    </head>
    <body>
        <c:import url="/jsp/inc/navBar.jsp"/>
        <c:import url="/jsp/inc/modalLogin.jsp"/>
        <c:choose>
            <c:when test="${sessionScope.usuario!=null}">
                <!-- Carousel -->
                <div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="10000">
                    <!-- Indicadores -->
                    <ol class="carousel-indicators">
                        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                        <li data-target="#myCarousel" data-slide-to="1"></li>
                    </ol>

                    <!-- Contenido -->
                    <div class="carousel-inner text-center" role="listbox">
                        <div class="item active">
                            <img src="<c:url value="/img/logo.png"/>" alt="Logo" style="height: 400px; display: inline" class="img-responsive"/>
                            <div class="carousel-caption">
                                <h3>Bienvenido de nuevo a SubástaME, ${usuario.clienteU.nombre}</h3>
                            </div>      
                        </div>

                        <div class="item">
                            <img src="https://placehold.it/150x80?text=IMAGE" alt="Image" style="height: 400px; display: inline" class="img-responsive"/>
                            <div class="carousel-caption">
                                <h3>Otra imagen</h3>
                                <p>Y otro texto</p>
                            </div>      
                        </div>
                    </div>

                    <!-- Flechas de direccion -->
                    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Anterior</span>
                    </a>
                    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Siguiente</span>
                    </a>
                </div>
                <!-- Fin Carousel -->
            </c:when>
            <c:otherwise>
                <div class="container-fluid text-center">
                    <h3>Bienvenido a SubástaME</h3>
                    <img src="<c:url value="/img/logo.png"/>" class="img-responsive" style="display:inline" alt="Logo" width="200" height="200">
                    <h3>¡A continuación te presentamos algunas categorías de los objetos subastados en nuestra web!</h3>
                </div>
            </c:otherwise>
        </c:choose>

        <div class="container-fluid text-center">    
            <h3>CATEGORÍAS</h3><br>
            <div class="row">
                <c:forEach items="${applicationScope.categorias}" var="categoria">
                    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                        <p>${categoria.denominacion}</p>
                        <img src="<c:url value="/img/categorias/${categoria.imagen}"/>" class="img-responsive" style="width:100%" alt="Image"/>
                    </div>
                </c:forEach>

            </div>
        </div><br/>
        <c:import url="/jsp/inc/footer.jsp"/>
    </body>
</html>
