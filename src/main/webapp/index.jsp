<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Redirige vers login.jsp en utilisant le bon chemin
    response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
%>
