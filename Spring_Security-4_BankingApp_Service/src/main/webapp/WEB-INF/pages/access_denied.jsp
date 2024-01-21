<%@page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>INVALID USER PAGE</title>
</head>
<body>
	<h1 style='color: red; text-align: cener'>
		Authorisation Failed for
		<%=SecurityContextHolder.getContext().getAuthentication().getName()%>
	</h1>
	<br/>
	
	<a href="./">HOME</a>
</body>
</html>