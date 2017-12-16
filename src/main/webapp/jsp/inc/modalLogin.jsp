<%-- 
    Document   : modalLogin
    Created on : 05-dic-2017, 18:58:07
    Author     : jesus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal contenido-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Entra:</h4>
            </div>
            <div class="modal-body">
                <c:if test="${requestScope.mensajeError!=null}">
                    <div class="alert alert-danger">
                        <p><strong>¡Atencion!</strong> Ha ocurrido el siguiente error:</p>
                        ${requestScope.mensajeError}
                    </div>
                </c:if>
                <form action="<c:url value="/RegistroLogin"/>" method="post" id="login">
                    <div class="form-group">
                        <label for="emailModal">Correo electrónico: </label>
                        <input type="mail" name="email" id="emailModal" required="" value="${requestScope.email}" class="form-control" autofocus="" pattern="^[a-z0-9][a-z0-9._%+-]*@[a-z0-9.-]+\.[a-z]{2,4}$" title="example@example.com"/>
                    </div>
                    <div class="form-group">
                        <label for="passwordModal">Contraseña: </label>
                        <input type="password" name="password" id="passwordModal" required="" class="form-control"/>
                    </div>
                    <button type="reset" class="btn btn-default botonNaranja">Borrar</button> 
                    <button type="submit" name="enviar" value="login" class="btn btn-default botonVerde">Entrar</button>
                </form>
            </div>
            <div class="modal-footer">
                <p>¿No tienes cuenta? <a href="<c:url value="/jsp/registro.jsp"/>">¡Regístrate!</a></p>
            </div>
        </div>
    </div>
</div>
<!-- Fin Modal -->
