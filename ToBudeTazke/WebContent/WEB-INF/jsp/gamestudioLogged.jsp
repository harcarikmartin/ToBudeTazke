<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' href='stylesheet.css' type='text/css'>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Gamestudio</title>
</head>
<body>
	<h1>Welcome to Gamestudio</h1>
	
	<h3>Choose the game you want to play</h3>
	<c:forEach items="${games}" var="game">
		<a class="menuItem" href="?action=play&game=${game.gameName}">${game.gameName}</a>
	</c:forEach>
	
	<jsp:include page="/${gamePlay}"></jsp:include>
	<table>
		<thead><tr><th>Comment</th><th>Added by</th></tr></thead>
		<c:forEach items="${comments}" var="comment">
			<tr> <td>${comment.comment}</td><td>${comment.player.playerName}</td></tr>
		</c:forEach>
		
	</table>
	
	<p>Logged as: ${logged}</p>
</body>
</html>