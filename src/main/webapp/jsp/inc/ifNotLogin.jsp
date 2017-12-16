<%-- 
    Document   : ifNotLogin
    Created on : 09-dic-2017, 20:42:06
    Author     : jesus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:if test="${sessionScope.usuario==null}">
    <c:redirect url="/index.jsp"/>
</c:if>
