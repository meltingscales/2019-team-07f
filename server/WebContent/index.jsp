<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	Random random = new Random();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>:)</title>
</head>
<body>
	<main>
	<p>Hello world!</p>

	<p>Here are your ten lucky numbers. Go play the lottery.</p>
	<ul>
		<%
			for (int i = 0; i < 10; i++) {
				out.print("<li>" + random.nextInt(100) + "</li>");
			}
		%>

	</ul>
	</main>
</body>
</html>