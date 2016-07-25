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
	
	
	<p>Choose the game you want to play</p>
	
	<table>
		<tr>
				<td>Game</td>
			<c:forEach items="${games}" var="game">
				<td><a class="menuItem"
					href="?action=play&game=${game.gameName}">${game.gameName}</a></td>
			</c:forEach>
		</tr>
		<tr>
				<td>Average Rating</td>
			<c:forEach items="${avgRatings}" var="avgRating">
				<td>${avgRating}</td>
			</c:forEach>
		</tr>
		<tr>
				<td>Number of Ratings</td>
			<c:forEach items="${ratingsCounts}" var="ratingsCount">
				<td>${ratingsCount}</td>
			</c:forEach>
		</tr>
	</table>
	<br>
	<jsp:include page="/${gamePlay}"></jsp:include>
	<br>
	<jsp:include page="comments.jsp" />
	
	<!--
	<p>Logged as: ${logged}</p>
	 -->
</body>
</html>