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
                var numcar = 2;
                $("button[id^='user']").on("click", function () {
                    var boton = $(this);
                    var idUsuario = $(this).val();
                    $.ajax({
                        data: {
                            idUsuario: idUsuario,
                            donde: "updateBloqueado"
                        },
                        url: "<c:url value="/Admin"/>",
                        success: function () {
                            if (boton.text() == "Bloquear") {
                                boton.text("Desbloquear").removeClass("btn-danger").addClass("btn-success");
                                $("#userblock" + idUsuario).text("s");
                            } else {
                                boton.text("Bloquear").removeClass("btn-success").addClass("btn-danger");
                                $("#userblock" + idUsuario).text("n");
                            }
                        }
                    });
                });
                $("#mas").on("click", function () {
                    $("#newCarac" + (numcar - 1)).after("<div class='form-group' id='newCarac" + numcar + "'><label for='carac" + numcar + "'>Característica " + numcar + ":</label><input type='text' name='carac' id='carac" + numcar + "' required class='form-control' pattern='^[A-ZÑÁÉÍÓÚa-zñáéíóú0-9 ]{1,50}$'/></div>");
                    numcar++;
                });
                $("#menos").on("click", function () {
                    if (numcar > 2) {
                        $("#newCarac" + (numcar - 1)).remove();
                        numcar--;
                    }
                });
            });
        </script>
    </head>
    <body>
        <%@include file="inc/ifNotLogin.jsp" %>
        <c:if test="${usuario.tipoAcceso=='u'}">
            <c:redirect url="/index.jsp"/>
        </c:if>
        <div class="container">
            <h2>Panel de administrador</h2>
            <div class="panel-group" id="admin">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#admin" href="#nuevaCat">
                                Crear nuevas categorías
                            </a>
                        </h4>
                    </div>
                    <div id="nuevaCat" class="panel-collapse collapse in">
                        <c:if test="${requestScope.mensaje!=null}">
                            <div class="alert alert-${requestScope.status}">
                                ${requestScope.mensaje}
                            </div>
                        </c:if>
                        <div class="panel-body">
                            <form action="<c:url value="/Admin?donde=nuevaCat"/>" method="post" enctype="multipart/form-data" accept-charset="UTF-8" id="newCat">
                                <div class="form-group">
                                    <label for="categoria">Categoría:</label>
                                    <input type="text" name="categoria" id="categoria" required="" class="form-control" pattern="^[A-ZÑÁÉÍÓÚa-zñáéíóú0-9 ]{1,100}$"/>
                                </div>
                                <div class="form-group">
                                    <label for="imagen">Imagen de la categoría:</label>
                                    <input type="file" id="imagen" name="imagen" required=""/>
                                </div>
                                <div class="form-group" id="newCarac1">
                                    <label for="carac1">Característica 1:</label>
                                    <input type="text" name="carac" id="carac1" required="" class="form-control" pattern="^[A-ZÑÁÉÍÓÚa-zñáéíóú0-9 ]{1,50}$"/>
                                </div>
                                <div class="form-group">
                                    <button type="button" class="btn btn-default botonNaranja" id="mas"><span class="glyphicon glyphicon-plus"></span> característica</button>
                                    <button type="button" class="btn btn-danger" id="menos"><span class="glyphicon glyphicon-minus"></span> característica</button>
                                </div>
                                <button type="submit" class="btn btn-default botonVerde">Agregar categoría y característica/s</button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#admin" href="#bloquear">
                                Desbloquear / Bloquear usuarios
                            </a>
                        </h4>
                    </div>
                    <div id="bloquear" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table table-bordered table-hover table-responsive table-striped">
                                <tr>
                                    <th>Email</th><th>Tipo</th><th>Estado</th><th>Acción</th>
                                </tr>
                                <c:forEach items="${listaUsuarios}" var="usuario">
                                    <tr>
                                        <td>${usuario.email}</td>
                                        <td>${usuario.tipoAcceso}</td><td id="userblock${usuario.idUsuario}">${usuario.bloqueado}</td> 
                                        <c:choose>
                                            <c:when test="${usuario.bloqueado=='n'}">
                                                <td><button class="btn btn-danger" value="${usuario.idUsuario}">Bloquear</button></td>
                                            </c:when>
                                            <c:otherwise>
                                                <td><button class="btn btn-success" value="${usuario.idUsuario}">Desbloquear</button></td>
                                            </c:otherwise>
                                        </c:choose>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <a href="<c:url value="/RegistroLogin?enviar=logout"/>"><button class="btn btn-info">Salir</button></a>
        </div>
    </body>
</html>
