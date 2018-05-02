<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>JSP练习</title>
</head>
<body>
<h2>Hello World!</h2>

<%
    out.println("你的 IP 地址" + request.getRemoteAddr());
%>
<p>今天的日期是 <%= (new java.util.Date().toLocaleString())%></p>
</body>
</html>
