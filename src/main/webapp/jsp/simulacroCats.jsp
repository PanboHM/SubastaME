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
//                Automaticamente cuando la página está lista cargamos las categorias en el select
                $.post("<c:url value='/SCategorias'/>", {
                    delContexto: true
                }, function (arrayJSON) {
                    if (arrayJSON != null) { //si el arrayJSON no está nulo es que hay categorias y las mostramos
                        //Parecido al for mejorado de java, recorremos el arrayJSON fila por fila.
                        $.each(arrayJSON, function (i, categoriaJSON) { //i es el indice, y categoriaJSON es la variable que recibe el atributo,objeto o Array que contiene la posicion
                            //por cada fila añadimos un optin al select
                            $("#sCategoria").append("<option value='" + categoriaJSON.denominacion + "'>" + categoriaJSON.denominacion + "</option>");
                        });
                    } else { //Si no hay categorías mostramos el siguiente mensaje en el select y lo deshabilitamos.
                        $("#sCategoria").html("<option value='nada'>No hay categorias disponibles</option>");
                        $("#sCategoria").prop("disabled", true);
                    }
                });
                $("#sCategoria").on("change", function () { //cuando el select cambia se activa el evento.
                    $("#caracAQUI").empty(); //Limpiamos el div para evitar que se nos acumulen las caracteristicas de varias categorias
                    var select = $("#sCategoria").val();
                    if (!(select == "porDefecto")) { //Si hemos seleccionado una categoria procedemos a mostrar las caracteristicas, si no no hacemos nada
                        $.post("<c:url value='/SCategorias'/>", {
                            delContexto: true
                        }, function (arrayJSON) {
                            $.each(arrayJSON, function (i, categoriaJSON) { //recorremos el array JSON
                                if (select == categoriaJSON.denominacion) { // en caso de que la categoria sea la misma que la seleccionada en el select:
                                    $.each(categoriaJSON.caracteristicas, function (u, caracteristicaJSON) { //cogemos el atributo caracteristicas(otra array) y la recorremos
//                                    var dnmncn = respuesta[i].caracteristicas[u].denominacion; TAMBIEN SE PUEDE ACCEDER A LO DESEADO DE ESTA MANERA, pero es menos vistosa
                                        //mostramos cada caracteristica de la categoria seleccionada
                                        var dnmncn = caracteristicaJSON.denominacion;
                                        $("#caracAQUI").append("<label for='" + dnmncn + "'>" + dnmncn + ": </label><input type='text' name='" + dnmncn + "' id='" + dnmncn + "' class='form-control'/>");
                                    });
                                }
                            });
                        });
                    }
                });
            });
        </script>
    </head>
    <body>
        <form action="<c:url value="/index.jsp"/>" method="post">
            <label for="text1">Dato: </label>
            <input type="text" id="text1" class="form-control"/>
            <label for="text2">Dato 2: </label>
            <input type="text" id="text2" class="form-control"/>

            <label for="sCategoria">Categoría: </label>
            <select id="sCategoria" class="form-control">
                <option value="porDefecto" selected="">Seleccione una categoría</option>
            </select>
            <div id="caracAQUI" class="bg-success"></div>
            <label for="text3">Dato 3: </label>
            <input type="text" id="text3" class="form-control"/>
            <label for="text4">Dato 4: </label>
            <input type="text" id="text4" class="form-control"/>
            <input type="reset"/> 
            <button type="submit" name="enviar" value="subastar">Subastar</button>
        </form> 
        <c:import url="/jsp/inc/footer.jsp"/>
    </body>
</html>
