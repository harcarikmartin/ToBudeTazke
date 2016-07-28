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
	<p>Players total: ${playersCount}</p>
	<p>Games total: ${gamesCount}</p>
	<p>Ratings total: ${ratingsCount}</p>
		<c:forEach items="${gameCountRatings}" var="gameCR">
			<li>${gameCR.game} was rated ${gameCR.countRatings} times.</li>
		</c:forEach>
	<p>Comments total: ${commentsCount}</p>
		<c:forEach items="${gameCountComments}" var="gameCC">
			<li>${gameCC.game} was commented ${gameCC.countComments} times.</li>
		</c:forEach>
	<p>Games played total: ${scoresCount}</p>
		<c:forEach items="${gameCountScores}" var="gameCS">
			<li>${gameCS.game} was played ${gameCS.countScores} times.</li>
		</c:forEach>
	<!-- <p>Most active player: ${mostActivePlayer}</p> -->
	<br><p>to <a href="/ToBudeTazke/Gamestudio">Gamestudio</a></p>
</body>
</html>