<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Gamestudio Statistics</title>
</head>
<body>
Dodajte stranku so statistikami o hrani v game studiu - 
pocet hracov, pocet hier, pocet zahrani hier (zo skore), pocet komentarov, pocet hodnoteni hier, najhranejsia hra, hrac co najviac hra -
 nadefinujte si servis a DTO objekt
 
	<h1>Stats</h1>
	<p>Amount of players: ${playersCount}</p>
	<p>Amount of games: ${gamesCount}</p>
	<p>Amount of ratings: ${ratingsCount}</p>
		<c:forEach items="${games}" var="game">
			<p>${game.gameName} was rated ${game.gameRatedTimes} times.</p>
		</c:forEach>
	<p>Amount of comments: ${commentsCount}</p>
		<c:forEach items="${games}" var="game">
			<p>${game.gameName} was commented ${game.gameCommentedTimes} times.</p>
		</c:forEach>
	<c:forEach items="${games}" var="game">
		<p>${game.gameName} was played ${game.gamePlayedTimes} times.</p>
	</c:forEach>
	<p>Most active player: ${mostActivePlayer}</p>
	<p><a href="/ToBudeTazke/Gamestudio">Back</a> to Gamestudio</p>
</body>
</html>