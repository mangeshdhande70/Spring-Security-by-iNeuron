<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.Random"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SHOW BALANCE</title>
</head>
<body>
	
	<h1 style='color:blue;text-align: center'>SHOW BALANCE PAGE</h1>
	<b>
		BALANCE :: <%= new Random().nextInt(10000000) %>
	</b>
	<a href='logout'>Logout</a>	
</body>
</html>