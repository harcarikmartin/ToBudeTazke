<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' href='bootstrap/css/bootstrap.css' type='text/css'>
<link rel="stylesheet" href="stylesheetBootstrap.css" type="text/css" media="screen">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Gamestudio</title>
</head>
<body>
<div><h1><a href="/ToBudeTazke/Gamestudio">Welcome to Gamestudio ${player.playerName}</a></h1></div>
<div>
	<form>
		<c:if test="${error == '1'}">
			<p class="warning">Passwords must match!</p>
		</c:if>
		<c:if test="${error == '2'}">
			<p class="warning">Password must be at least 6 digits long!</p>
		</c:if>
		<c:if test="${error == '3'}">
			<p class="warning">Username already exists!</p>
		</c:if>
		<c:if test="${error == '4'}">
			<p class="warning">Not registered yet!</p>
		</c:if>
		
		<div>Username: <input type="text" name="user"></div>
		<div>Password: <input type="password" name="password"></div>
		<c:if test="${login != null}">
		<input type="hidden" name="action" value="logMe">
		<div><input type="submit" value="Login"></div>
		</c:if>
		<c:if test="${register != null}">
		<input type="hidden" name="action" value="registerMe">
		<div>Retype password: <input type="password" name="passwordR"></div>
		<div><input type="submit" value="Register"></div>
		</c:if>
	</form>
</div>
</body>
</html>
