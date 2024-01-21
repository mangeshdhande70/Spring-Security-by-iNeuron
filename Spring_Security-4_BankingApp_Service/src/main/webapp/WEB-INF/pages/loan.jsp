<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Loan Amount</title>
</head>
<body>
	<h1 style="color: blue; text-align: center;">LOAN APPROVAL PAGE</h1>
	<pre>
			<b> U r approved for a loan amount :: <%=new Random().nextInt(100000)%></b>
			<a href="./">HOME</a>
	</pre>
	<a href='logout'>logout</a>
</body>
</html>

