<%-- 
    Document   : index
    Created on : 17-nov-2017, 15:27:46
    Author     : jesus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
        <div class="container efecto3D">
            <ul class="nav nav-tabs nav-justified">
                <li class="active"><a data-toggle="tab" href="#perfil">Perfil</a></li>
                <li><a data-toggle="tab" href="#misSubastas">Mis subastas</a></li>
                <li><a data-toggle="tab" href="#misPujas">Mis pujas</a></li>
            </ul>

            <div class="tab-content">
                <div id="perfil" class="tab-pane fade in active">
                    <c:if test="${requestScope.mensaje!=null}">
                        <div class="alert alert-${requestScope.status}">
                            ${requestScope.mensaje}
                        </div>
                    </c:if>
                    <div class="row text-center">
                        <div class="col-lg-2">
                            <img src="<c:url value="/img/subidas/avatar/${usuario.clienteU.avatar}"/>" class="img-responsive" style="width:100%" alt="Avatar"/>
                            <button data-toggle="modal" data-target="#modalAvatar" class="btn btn-default botonVerde"><span class="glyphicon glyphicon-retweet"></span> Cambiar</button>
                        </div>
                        <div class="col-lg-10">
                            <h3>${sessionScope.usuario.clienteU.nombre} ${sessionScope.usuario.clienteU.apellido1} ${sessionScope.usuario.clienteU.apellido2}</h3>
                            <h4>Ultimo acceso: <fmt:formatDate type="both" dateStyle="long" value="${sessionScope.usuario.ultimoAcceso}"/></h4>
                        </div>
                    </div>
                    <form action="<c:url value="/ControlPanel"/>" method="post">
                        <fieldset>
                            <legend>Datos de acceso:</legend>
                            <p><strong>Email:</strong> ${usuario.email}</p>
                            <div class="form-group">
                                <label for="passwordActual">Contraseña actual:</label>
                                <input type="password" name="passwordActual" id="passwordActual" class="form-control"/>
                                <label for="passwordNew">Nueva contraseña:</label>
                                <input type="password" name="passwordNew" id="passwordNew" class="form-control"/>
                                <label for="passwordNewRepeat">Repite la nueva contraseña:</label>
                                <input type="password" name="passwordNewRepeat" id="passwordNewRepeat" class="form-control"/>
                            </div>
                            <button type="submit" name="controlPanel" value="cambiarPassword" class="btn btn-default botonVerde">Cambiar contraseña</button>
                        </fieldset>
                    </form>
                    <form action="<c:url value="/ControlPanel"/>" method="post">
                        <fieldset>
                            <legend>Datos personales</legend>
                            <div class="form-group">
                                <label for="nombre">Nombre: </label>
                                <input type="text" name="nombre" value="${usuario.clienteU.nombre}" id="nombre" required="" class="form-control" pattern="^([A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]{0,8}[\s]?){1,2}$" title="De la A a la Z máximo 20 carácteres"/>
                            </div>
                            <div class="form-group">
                                <label for="apellido1">Primer apellido: </label>
                                <input type="text" name="apellido1" value="${usuario.clienteU.apellido1}" id="apellido1" required="" class="form-control" pattern="^[A-ZÁÉÍÓÚ]{1}[a-zñáéíóú ]{0,19}$" title="De la A a la Z máximo 20 carácteres"/>
                            </div>
                            <div class="form-group">
                                <label for="apellido2">Segundo apellido: </label>
                                <input type="text" name="apellido2" value="${usuario.clienteU.apellido2}" id="apellido2" class="form-control" pattern="^[A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]{0,19}$" title="De la A a la Z máximo 20 carácteres"/>
                            </div>
                            <div class="form-group">
                                <label for="nif">DNI:</label>
                                <input type="text" name="nif" value="${usuario.clienteU.nif}" id="nif" required="" class="form-control" pattern="^[\d]{8}[TRWAGMYFPDXBNJZSQVHLCKET]{1}$" title="8 números seguidos de una letra mayúscula"/>
                            </div>
                            <div class="form-group">
                                <label for="direccion">Direccion: </label>
                                <input type="text" name="direccion" value="${usuario.clienteU.direccion}" id="direccion" required="" class="form-control" pattern="^[A-ZÁÉÍÓÚa-zñáéíóú0-9 ]{1,45}$" title="Máximo 45 caracteres"/>
                            </div>
                            <div class="form-group">
                                <label for="telefono">Teléfono: </label>
                                <input type="tel" name="telefono" value="${usuario.clienteU.telefono}" id="telefono" class="form-control" pattern="^[9|6|7]{1}[\d]{8}$"/>
                            </div>
                        </fieldset>
                        <button type="submit" name="controlPanel" value="cambiarDatos" class="btn btn-default botonVerde">Modificar datos</button>
                    </form>
                </div>
                <div id="misSubastas" class="tab-pane fade">
                    <h2>Subastas finalizadas</h2>
                    <c:choose>
                        <c:when test="${misSubastasFinalizadas!=null}">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered table-striped">
                                    <tr>
                                        <th>Titulo</th><th>Puja actual</th><th>Ir a subasta</th>
                                    </tr>
                                    <c:forEach items="${misSubastasFinalizadas}" var="subasta">
                                        <tr>
                                            <td>${subasta.descripcionCorta}</td>
                                            <c:choose>
                                                <c:when test="${subasta.pujas[0].importe!=0.0}">
                                                    <td>${subasta.pujas[0].importe} €</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>No ha pujado nadie</td>
                                                </c:otherwise>
                                            </c:choose>
                                            <td>
                                                <a href="<c:url value="/SCatalogoSubastas?idSubasta=${subasta.idArticulo}&idCategoria=${subasta.idCategoria}"/>">
                                                    <button class="btn btn-default botonVerde">Ir a subasta</button>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <h3>Ninguna subasta finalizada</h3>
                        </c:otherwise>
                    </c:choose>
                    <h2>Subastas activas</h2>
                    <c:choose>
                        <c:when test="${misSubastasActivas!=null}">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered table-striped">
                                    <tr>
                                        <th>Titulo</th><th>Puja actual</th><th>Ir a subasta</th>
                                    </tr>
                                    <c:forEach items="${misSubastasActivas}" var="subasta">
                                        <tr>
                                            <td>${subasta.descripcionCorta}</td>
                                            <c:choose>
                                                <c:when test="${subasta.pujas[0].importe!=0.0}">
                                                    <td>${subasta.pujas[0].importe} €</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>No ha pujado nadie</td>
                                                </c:otherwise>
                                            </c:choose>
                                            <td>
                                                <a href="<c:url value="/SCatalogoSubastas?idSubasta=${subasta.idArticulo}&idCategoria=${subasta.idCategoria}"/>">
                                                    <button class="btn btn-default botonVerde">Ir a subasta</button>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <h3>Ninguna subasta activa</h3>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div id="misPujas" class="tab-pane fade">
                    <c:choose>
                        <c:when test="${misPujas!=null}">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered table-striped">
                                    <tr>
                                        <th>Titulo</th><th>Puja actual</th><th>Ir a subasta</th>
                                    </tr>
                                    <c:forEach items="${misPujas}" var="subasta">
                                        <tr>
                                            <td>${subasta.descripcionCorta}</td>
                                            <td>${subasta.pujas[0].importe} €</td>
                                            <td>
                                                <a href="<c:url value="/SCatalogoSubastas?idSubasta=${subasta.idArticulo}&idCategoria=${subasta.idCategoria}"/>">
                                                    <button class="btn btn-default botonVerde">Ir a subasta</button>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <h3>No has pujado por nada.</h3>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <!--Inicio modal cambiar avatar-->
        <div id="modalAvatar" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal contenido-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Selecciona una imagen:</h4>
                    </div>
                    <div class="modal-body">
                        <c:if test="${requestScope.mensajeError!=null}">
                            <div class="alert alert-danger">
                                <p><strong>¡Atencion!</strong> Ha ocurrido el siguiente error:</p>
                                ${requestScope.mensajeError}
                            </div>
                        </c:if>
                        <form action="<c:url value="/ControlPanel"/>" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
                            <div class="form-group">
                                <label for="avatar">Avatar:</label>
                                <input type="file" id="avatar" name="avatar" required=""/>
                            </div>
                            <button type="reset" class="btn btn-default botonNaranja">Borrar</button>
                            <button type="submit" name="controlPanel" value="cambiarAvatar" class="btn btn-default botonVerde">Entrar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- Fin Modal -->
        <c:import url="/jsp/inc/footer.jsp"/>
    </body>
</html>
