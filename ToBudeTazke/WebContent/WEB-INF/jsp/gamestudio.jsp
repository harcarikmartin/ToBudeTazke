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
				<h1><a href="/ToBudeTazke/Gamestudio">Welcome to Gamestudio ${player.playerName}</a></h1></div>
			<div class="col-sm-2 col-md-3 col-lg-3">
				<c:if test="${player == null}">
					<div class="inline">
						<form>
							<input type="hidden" name="action" value="login">
							<input type="submit" value="Login">
						</form>
					</div>
				</c:if>
			</div>
		</div>
		
		<div class="row">
			<div class="col-sm-3 col-md-4 col-lg-4"></div>
			<div class="col-sm-6 col-md-4 col-lg-4">
				
			</div>
			<div class="col-sm-3 col-md-4 col-lg-4"></div>
		</div>
		
		<div class = "row">
			<div class="col-sm-2 col-md-3 col-lg-3"></div>
			<div class="col-sm-8 col-md-6 col-lg-6">
				<c:forEach items="${games}" var="game">
					<div class="float">
						<a class="menuItem"
								href="?action=play&game=${game.gameName}"><img
									alt="${game.gameName}" src="images/${game.gameName}.png"></a>
					</div>
				</c:forEach>
				<c:forEach items="${avgRatings}" var="avgRating">
					<div class="float">
						<img src="images/stars/${avgRating}.png">
					</div>
				</c:forEach>
				<c:forEach items="${ratingsCounts}" var="ratingsCount">
					<div class="float">
						Rated by ${ratingsCount} players.
					</div>
				</c:forEach>
			</div>
			<div class="col-sm-2 col-md-3 col-lg-3"></div>
		</div>
	</div>
	
	<hr>
	<div class = "row">
			<div class="col-sm-1 col-md-1 col-lg-1"></div>
			<div class="col-sm-6 col-md-6 col-lg-6">
				
				<p class=caps><c:if test="${gamePlay == 'gtn'}">guess my number</c:if></p>
				<p class=caps><c:if test="${gamePlay != 'gtn'}">${gamePlay}</c:if></p>
				<jsp:include page="/${gamePlay}"></jsp:include>
			</div>
			<div class="col-sm-5 col-md-5 col-lg-5">
					<c:if test="${player != null}">
						<div>
						<p><c:if test="${gamePlay == 'gtn'}">Rate Guess my number</c:if></p>
						<p><c:if test="${gamePlay != 'gtn'}">Rate ${gamePlay}</c:if></p>
						<a href="?action=insertRating&rating=1&game=${game.gameName}"><img src="images/stars/star1.png"></a>
						<a href="?action=insertRating&rating=2&game=${game.gameName}"><img src="images/stars/star2.png"></a>
						<a href="?action=insertRating&rating=3&game=${game.gameName}"><img src="images/stars/star3.png"></a>
						<a href="?action=insertRating&rating=4&game=${game.gameName}"><img src="images/stars/star4.png"></a>
						<a href="?action=insertRating&rating=5&game=${game.gameName}"><img src="images/stars/star5.png"></a>
						</div>
						<br>
						<div>
						<p><c:if test="${gamePlay == 'gtn'}">Add comment for Guess my number</c:if></p>
						<p><c:if test="${gamePlay != 'gtn'}">Add comment for ${gamePlay}</c:if></p>
						<form>
				        	<input type="hidden" name="action" value="insertComment" />
				        	<input type="hidden" name="game" value="${gamePlay}" />
				        	<div class="addSubmit">
				        		<div>					
				        		    <input type="text" placeholder="your comment" maxlength="200" name="comment">
					        	</div>
					        	<div>
						        	<input type="submit" value="Comment" />
						        </div>
					        </div>
						</form>
						</div>
					</c:if>			
				
			
			</div>
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
</body>
</html>