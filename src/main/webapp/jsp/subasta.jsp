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
                actualizarPujas();
                setInterval(actualizarPujas, 5000);

                $("#puja5, #puja10, #puja15").on("click", function () {
                    var importe = $(this).prop("value");
                    pujar(importe);
                    $("#modalPuja").modal();
                });
                $("#botonPujaManual").on("click", function () {
                    var importe = $("#pujaManual").prop("value");
                    if (/^\d+(\,\d{1,2})?$/.test(importe)) {
                        pujar(importe);
                    } else {
                        $("#mensajePuja").html("La puja introducida no tiene el formato correcto.");
                    }
                    $("#modalPuja").modal();
                });

                function actualizarPujas() {
                    $.ajax({
                        data: {
                            idArticulo: ${subasta.idArticulo},
                            donde: "actualizarPujas"
                        },
                        url: "<c:url value="/AJAXSubasta"/>",
                        success: function (data) {
                            if (data.length > 0) {
                                $("#pujas").empty();
                                $.each(data, function (i, puja) {
                                    $("#pujas").append("<li>Pujador " + puja.idCliente + ": " + puja.importe + "€<br/><small>Fecha: " + puja.fecha + "</small></li>");
                                    if (i == 0) {
                                        $("#pujaActual").html("<h3>Puja actual: " + puja.importe + "€</h3>");
                                        $("#puja5").prop("value", puja.importe + 5);
                                        $("#puja10").prop("value", puja.importe + 10);
                                        $("#puja15").prop("value", puja.importe + 15);
                                    }
                                });
                            } else {
                                $("#pujas").html("No hay pujas sobre este artículo actualmente, ¡Sé el primero en pujar!");
                                $("#puja5").prop("value",${subasta.importeSalida} + 5);
                                $("#puja10").prop("value",${subasta.importeSalida} + 10);
                                $("#puja15").prop("value",${subasta.importeSalida} + 15);
                            }
                        }
                    });
                }

                function pujar(importe) {
                    $.ajax({
                        data: {
                            idArticulo: ${subasta.idArticulo},
                            idSubastador: ${subasta.cliente.idCliente},
                            importePuja: importe,
                            donde: "pujar"
                        },
                        url: "<c:url value="/AJAXSubasta"/>",
                        success: function (data) {
                            switch (data) {
                                case "mismoIndividuo":
                                    $("#mensajePuja").html("No puedes pujar en tus subastas.");
                                    break;
                                case "correcto":
                                    $("#mensajePuja").html("Has pujado correctamente, deberías ver tu puja reflejada en la lista.");
                                    break;
                                case "error":
                                    $("#mensajePuja").html("Error al pujar, inténtelo de nuevo más tarde.");
                                    break;
                                case "inferior":
                                    $("#mensajePuja").html("Tu puja es igual o inferior a la puja actual.");
                                    break;
                                case "fuera":
                                    $("#mensajePuja").html("La subasta ha finalizado.");
                                    break;
                                default:
                                    console.log("ERROR EN EL SWITCH de pujar de ajax");
                                    break;
                            }
                        }
                    });
                }
            });
        </script>
    </head>
    <body>
        <%@include file="inc/ifNotLogin.jsp" %>
        <c:import url="/jsp/inc/navBar.jsp"/>
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-2 col-md-2 col-sm-1 hidden-xs">
                    <img src="<c:url value="/img/logo.png"/>" alt="logo" class="img-responsive" style="width:100%"/>
                </div>
                <div class="col-lg-8 col-md-8 col-sm-10 col-xs-12 efecto3D">
                    <h1 class="text-center">${subasta.descripcionCorta}</h1>
                    <div class="row">
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">

                            <div id="subastaCarousel" class="carousel slide sliderIndex" data-ride="carousel">

                                <!-- Wrapper for slides -->
                                <div class="carousel-inner text-center">
                                    <div class="item active">
                                        <img src="<c:url value="/img/subidas/articulos/${subasta.idArticulo}/${subasta.fotografias[0].fotografia}"/>" alt="${subasta.fotografias[0].fotografia}" style="height: 400px; display: inline" class="img-responsive">
                                    </div>
                                    <c:forEach items="${subasta.fotografias}" var="fotografiaFor" varStatus="i">
                                        <c:if test="${!i.first}">
                                            <div class="item">
                                                <img src="<c:url value="/img/subidas/articulos/${subasta.idArticulo}/${fotografiaFor.fotografia}"/>" alt="${fotografiaFor.fotografia}" style="height: 400px; display: inline" class="img-responsive">
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>

                                <!-- Left and right controls -->
                                <a class="left carousel-control" href="#subastaCarousel" data-slide="prev">
                                    <span class="glyphicon glyphicon-chevron-left"></span>
                                    <span class="sr-only">Previous</span>
                                </a>
                                <a class="right carousel-control" href="#subastaCarousel" data-slide="next">
                                    <span class="glyphicon glyphicon-chevron-right"></span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </div>
                            <h2>Datos del artículo: </h2>
                            <h3>${subasta.descripcion}</h3>
                            <c:forEach items="${categorias}" var="categoria">
                                <c:if test="${categoria.idCategoria==param.idCategoria}">
                                    <c:forEach items="${categoria.caracteristicas}" var="nombreCarac">
                                        <c:forEach items="${subasta.caracteristicas}" var="valorCarac">
                                            <c:if test="${nombreCarac.idCaracteristica==valorCarac.idCaracteristica}">
                                                <p><strong>${nombreCarac.denominacion}:</strong> ${valorCarac.valor}</p>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </c:if>
                            </c:forEach>
                            <h2>Datos del subastador: </h2>
                            <div class="row text-center">
                                <div class="col-lg-2">
                                    <img src="<c:url value="/img/subidas/avatar/${subasta.cliente.avatar}"/>" class="img-responsive" style="width:100%" alt="Avatar"/>
                                </div>
                                <div class="col-lg-10">
                                    <h3>${subasta.cliente.nombre} ${subasta.cliente.apellido1} ${subasta.cliente.apellido2}</h3>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
                            <p>Tiempo restante: </p>
                            <div id="countDown" class="timer"></div>
                            <script>
                                $("#countDown").countdown("${subasta.fechaFin}", function (event) {
                                    $(this).text(event.strftime('%D días y %H:%M:%S'));
                                }).on('finish.countdown', function () {
                                    $(this).text("Ha finalizado");
                                });
                            </script>
                            <span id="pujaActual"></span>
                            <h2>Pujas rápidas: </h2>
                            <button type="submit" value="" id="puja5" class="btn btn-default botonVerde">+5€</button>
                            <button type="submit" value="" id="puja10" class="btn btn-default botonVerde">+10€</button>
                            <button type="submit" value="" id="puja15" class="btn btn-default botonVerde">+15€</button>
                            <br/>
                            <h2>Puja manualmente: </h2>
                            <input type="text" name="puja" id="pujaManual"/> <button type="submit" id="botonPujaManual" class="btn btn-default botonVerde">Pujar</button>
                            <h2>Pujas: </h2>
                            <ul id="pujas"></ul>
                        </div>
                    </div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm1 hidden-xs">
                    <img src="<c:url value="/img/logo.png"/>" alt="logo" class="img-responsive" style="width:100%"/>
                </div>
            </div>
        </div>

        <!-- Modal que se usa para mostrar el mensaje de la puja.-->
        <div id="modalPuja" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal contenido-->
                <div class="modal-content">
                    <div class="modal-body" id="mensajePuja">

                    </div>
                </div>
            </div>
        </div>
        <!-- Fin Modal -->

        <c:import url="/jsp/inc/footer.jsp"/>
    </body>
</html>
