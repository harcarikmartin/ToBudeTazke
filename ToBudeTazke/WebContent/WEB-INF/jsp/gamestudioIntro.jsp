<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' href='stylesheetBootstrap.css' type='text/css'>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Gamestudio</title>
</head>
<body>
	<h1>Welcome to Gamestudio</h1>
	<!-- 
	<a href="/ToBudeTazke/minesweeper">Minesweeper</a>
	 -->
	<h3>Type your name</h3> 
	<form>
		<input type="hidden" name="action" value="login">
		Username: <input type="text" name="user">
		<input type="submit" value="Login">
	</form>

	
</body>
</html>