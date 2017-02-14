<%-- 
    Document   : index
    Created on : 15/10/2014, 11:05:06 PM
    Author     : Leo Gutierrez R. <leogutierrezramirez@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="expr4j" uri="/WEB-INF/tlds/exp4jtld" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <h1>exp4j with tags</h1>

        <expr4j:expr expr="sin(x)" x="4">
        </expr4j:expr>

        <hr/>
        
    <expr4j:expr x="2">
        <jsp:attribute name="expr">
           2  + x^2
        </jsp:attribute>
    </expr4j:expr>

</body>
</html>
