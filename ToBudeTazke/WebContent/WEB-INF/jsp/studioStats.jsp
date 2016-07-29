<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' href='bootstrap/css/bootstrap.css' type='text/css'>
<link rel="stylesheet" href="stylesheetBootstrap.css" type="text/css" media="screen">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Gamestudio Statistics</title>
</head>
<body class="stats">
	<h3>Gamestudio stats</h3>
	<ol>
	<li>Registered players: <b>${playersCount}</b></li>
	<li>Games total: <b>${gamesCount}</b></li>
	<li>Ratings count: <b>${ratingsCount}</b></li>
	<ul>
		<c:forEach items="${gameCountRatings}" var="gameCR">
			<li><b>${gameCR.game}</b> was rated <b>${gameCR.countRatings}</b>  times.</li>
		</c:forEach>
	</ul>
	<li>Comments count: <b>${commentsCount}</b> </li>
	<ul>
		<c:forEach items="${gameCountComments}" var="gameCC">
			<li><b>${gameCC.game}</b>  was commented <b>${gameCC.countComments}</b>  times.</li>
		</c:forEach>
	</ul>
	<li>Gameplay total: <b>${scoresCount}</b> </li>
	<ul>
		<c:forEach items="${gameCountScores}" var="gameCS">
			<li><b>${gameCS.game}</b> was played <b>${gameCS.countScores}</b>  times.</li>
		</c:forEach>
	</ul>
	<li>Activity for players: </li>
	<ul>
		<c:forEach items="${mostActivePlayer}" var="ma">
			<li><b>${ma.game}</b> played <b>${ma.countScores}</b> times.</li>
		</c:forEach>
	</ul>
	 <!-- -->
	</ol>
	
	<br><p>to <a href="/ToBudeTazke/Gamestudio">Gamestudio</a></p>
</body>
</html>