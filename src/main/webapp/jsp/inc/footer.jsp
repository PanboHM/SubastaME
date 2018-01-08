<%-- 
    Document   : footer
    Created on : 20-nov-2017, 10:26:44
    Author     : jesus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<footer class="container-fluid text-center">
    <div class="row">
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
            <h5>¿Quiénes somos?</h5>
            <p>Somos una empresa formal, seria, dedicaba a subastar artículos.</p>
        </div>
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
            <h5>Contacto:</h5>
            <p><a href="mailto:jesush_panbo@hotmail.com">Email</a></p>
            <p><a href="tel:+34612345678">Teléfono</a></p>
        </div>
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
            <h5>Social:</h5>
            <a href="https://www.youtube.com/"><img src="<c:url value="/img/icons/youtube.png"/>" alt="youtube"></a>
            <a href="https://www.facebook.com/"><img src="<c:url value="/img/icons/facebook.png"/>" alt="facebook"></a>
        </div>
    </div>
</footer>