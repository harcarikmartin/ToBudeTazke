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
  	<div class = "container">
  		<div class = "row">
			<div class="col-sm-2 col-md-3 col-lg-3"></div>
			<div class="col-sm-8 col-md-6 col-lg-6">
				<h1><a href="/ToBudeTazke/Gamestudio">Welcome to Gamestudio</a></h1></div>
			<div class="col-sm-2 col-md-3 col-lg-3"></div>
		</div>
		<div class = "row">
			<div class="col-sm-2 col-md-3 col-lg-3"></div>
			<div class="col-sm-8 col-md-6 col-lg-6">
				<p>Choose the game you want to play</p></div>
			<div class="col-sm-2 col-md-3 col-lg-3"></div>
		</div>
		<div class = "row">
			<div class="col-sm-2 col-md-3 col-lg-3"></div>
			<div class="col-sm-8 col-md-6 col-lg-6">
				<table>
					<tr>
						<td></td>
						<c:forEach items="${games}" var="game">
							<td class="gameIcon"><a class="menuItem"
								href="?action=play&game=${game.gameName}"><img
									alt="${game.gameName}" src="images/${game.gameName}.png"></a></td>
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
			</div>
			<div class="col-sm-2 col-md-3 col-lg-3"></div>
		</div>
	</div>
	
  	<div class = "row">
		<div class="col-sm-2 col-md-3 col-lg-3"></div>
			<div class="col-sm-8 col-md-6 col-lg-6">
			<jsp:include page="/${gamePlay}"></jsp:include>
		</div>
		<div class="col-sm-2 col-md-3 col-lg-3"></div>
	</div>
	
	<div class = "row">
		<div class="col-sm-3 col-md-4 col-lg-4"></div>
		<div class="col-sm-3 col-md-2 col-lg-2">
			<jsp:include page="scores.jsp" />
		</div>
		<div class="col-sm-3 col-md-2 col-lg-2">
			<jsp:include page="comments.jsp" />
		</div>
		<div class="col-sm-3 col-md-4 col-lg-4"></div>
	</div>
	
	<div class="row">
		<div class="col-sm-3 col-md-4 col-lg-4"></div>
		<div class="col-sm-6 col-md-4 col-lg-4">
			<jsp:include page="addCommentAndRating.jsp"></jsp:include></div>
		<div class="col-sm-3 col-md-4 col-lg-4"></div>
	</div>
</body>
</html>