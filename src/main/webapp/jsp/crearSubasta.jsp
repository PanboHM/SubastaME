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
                $("#sCategoria").on("change", function () { //cuando el select cambia se activa el evento.
                    $("#caracAQUI").empty(); //Limpiamos el div para evitar que se nos acumulen las caracteristicas de varias categorias
                    var select = $("#sCategoria").val();
                    if (!(select == "nada")) { //Si hemos seleccionado una categoria procedemos a mostrar las caracteristicas, si no no hacemos nada
                        $.post("<c:url value='/SCategorias'/>", {
                            delContexto: true
                        }, function (arrayJSON) {
                            $("#caracAQUI").append("<fieldset><legend>Características del producto</legend>");
                            $.each(arrayJSON, function (i, categoriaJSON) { //recorremos el array JSON
                                if (select == categoriaJSON.idCategoria) { // en caso de que la categoria sea la misma que la seleccionada en el select:
                                    $.each(categoriaJSON.caracteristicas, function (u, caracteristicaJSON) { //cogemos el atributo caracteristicas(otra array) y la recorremos
                                        //mostramos cada caracteristica de la categoria seleccionada
                                        var dnmncn = caracteristicaJSON.denominacion;
                                        $("#caracAQUI").append("<div class='form-group'><label for='" + dnmncn + "'>" + dnmncn + ":</label><input type='text' name='carac" + caracteristicaJSON.idCaracteristica + "' id='" + dnmncn + "' class='form-control' required/></div>");
                                    });
                                }
                            });
                            $("#caracAQUI").append("</fieldset>");
                        });
                    }
                });
            });
        </script>
    </head>
    <body>
        <%@include file="inc/ifNotLogin.jsp" %>
        <c:import url="/jsp/inc/navBar.jsp"/>
        <div class="row">
            <div class="col-lg-4 col-md-4 col-sm-4 hidden-xs">
                <img src="<c:url value="/img/logo.png"/>" alt="logo" class="img-responsive" style="width:100%"/>
            </div>
            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 efecto3D">
                <h1 class="text-center">Datos de la subasta: </h1>
                <c:if test="${requestScope.mensajeError!=null}">
                    <div class="alert-danger">
                        ${requestScope.mensajeError}
                    </div>
                </c:if>
                <form action="<c:url value="/CrearSubasta"/>" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
                    <div class="form-group">
                        <label for="descripcionCorta">Descripción corta: </label>
                        <input type="text" id="descripcionCorta" name="descripcionCorta" value="${requestScope.articulo.descripcionCorta}" class="form-control" required="" pattern="^[A-ZÑÁÉÍÓÚa-zñáéíóú0-9 ]{1,100}$"/>
                    </div>
                    <div class="form-group">
                        <label for="descripcion">Descripción: </label>
                        <textarea id="descripcion" name="descripcion" class="form-control">${requestScope.articulo.descripcion}</textarea>
                    </div>
                    <div class="form-group">
                        <label for="imagen">Fotos del artículo:</label>
                        <input type="file" id="imagen" name="imagen" multiple="" required=""/>
                    </div>
                    <div class="form-group">
                        <label for="sCategoria">Categoría: </label>
                        <select id="sCategoria" name="idCategoria" class="form-control">
                            <c:choose>
                                <c:when test="${applicationScope.categorias!=null}">
                                    <option value="nada" selected="">Seleccione una opcion</option>
                                    <c:forEach items="${applicationScope.categorias}" var="categoria">
                                        <option value="${categoria.idCategoria}">${categoria.denominacion}</option>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <option value="nada" selected="" disabled="">No hay categorias disponibles</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </div>
                    <div id="caracAQUI"></div>
                    <div class="form-group">
                        <label for="fechaInicio">Fecha de inicio: </label>
                        <input type="date" name="fechaInicio" id="fechaInicio" class="form-control" required=""/>
                    </div>
                    <div class="form-group">
                        <label for="horaInicio">Hora de inicio: </label>
                        <input type="time" name="horaInicio" id="horaInicio" class="form-control" required=""/>
                    </div>
                    <div class="form-group">
                        <label for="fechaFin">Fecha de fin: </label>
                        <input type="date" name="fechaFin" id="fechaFin" class="form-control" required=""/>
                    </div>
                    <div class="form-group">
                        <label for="horaFin">Hora de fin: </label>
                        <input type="time" name="horaFin" id="horaFin" class="form-control" required=""/>
                    </div>
                    <div class="form-group">
                        <label form="importeSalida">Importe de salida:</label>
                        <input type="text" name="importeSalida" id="importeSalida" class="form-control text-right" pattern="^[0-9]{1,6}([,][0-9]{1,2})?$" required=""/>
                    </div>
                    <button type="reset" class="btn btn-default botonNaranja">Borrar</button> 
                    <button type="submit" class="btn btn-default botonVerde">Subastar</button>
                </form> 
            </div>
            <div class="col-lg-4 col-md-4 col-sm-4 hidden-xs">
                <img src="<c:url value="/img/logo.png"/>" alt="logo" class="img-responsive" style="width:100%"/>
            </div>
        </div>
        <c:import url="/jsp/inc/footer.jsp"/>
    </body>
</html>
