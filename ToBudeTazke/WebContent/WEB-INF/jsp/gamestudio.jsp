<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' href='bootstrap/css/bootstrap.css' type='text/css'>
<link rel="stylesheet" href="stylesheetBootstrap.css" type="text/css" media="screen">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Gamestudio</title>
</head>
<body>
  	
  		<div class = "row">
			<div class="col-sm-2 col-md-3 col-lg-3"></div>
			<div class="col-sm-8 col-md-6 col-lg-6">
				<h1><a href="/ToBudeTazke/Gamestudio"><c:if test="${player.playerName != null}">${player.playerName}, w</c:if><c:if test="${player.playerName == null}">W</c:if>elcome to Gamestudio</a></h1></div>
			<div class="col-sm-2 col-md-3 col-lg-3">
				<c:if test="${player == null}">
					<div class="inline">
						<c:if test="${defaultLog == '1'}">
						<form>
							<input type="hidden" name="action" value="login">
							<input type="submit" value="Login">
						</form>
						</c:if>
						<c:if test="${showLogin == '1'}">
						
							<form action="Gamestudio" method="post">
								<c:if test="${error == '1'}">
									<p class="warning">Passwords must match!</p>
								</c:if>
								<c:if test="${error == '2'}">
									<p class="warning">Password must be at least 8 digits long!</p>
								</c:if>
								<c:if test="${error == '3'}">
									<p class="warning">Username already exists!</p>
								</c:if>
								<c:if test="${error == '4'}">
									<p class="warning">Not registered yet!</p>
								</c:if>
								<c:if test="${error == '5'}">
									<p class="warning">Wrong password!</p>
								</c:if>
								
								<div>Username: <input type="text" name="user" autofocus></div>
								<div>Password: <input type="password" name="password" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$"></div>
								<c:if test="${login != null}">
								<input type="hidden" name="action" value="logMe">
								<div><input type="submit" value="Login"></div>
								</c:if>
								<c:if test="${register != null}">
								<input type="hidden" name="action" value="registerMe">
								<div>Password: <input type="password" name="passwordR" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$"></div>
								<div><p>* Password must be 8 or more characters long, contain at least 1 uppercase letter, 1 lowercase letter and 1 number.</p></div>
								<div><input type="submit" value="Register"></div>
								</c:if>
							</form>
						</c:if>
					</div>
				</c:if>
				<c:if test="${player != null}">
					<div class="inline">
						<a href="?action=logout">Logout ${player.playerName}</a>
					</div>
				</c:if>
			</div>
		</div>
		
		<div class = "row">
			<div class="col-sm-2 col-md-2 col-lg-2"></div>
			<div class="col-sm-8 col-md-8 col-lg-8">
				<c:forEach items="${games}" var="game">
					<div class="float">
						<a class="menuItem"
								href="?action=play&amp;game=${game.gameName}"><img
									alt="${game.gameName}" src="images/${game.gameName}.png"></a>
					</div>
				</c:forEach>
				<c:forEach items="${avgRatings}" var="avgRating">
					<div class="float">
						<img alt="star" src="images/stars/${avgRating}.png">
					</div>
				</c:forEach>
				<c:forEach items="${ratingsCounts}" var="ratingsCount">
					<div class="float">
						Rated <c:if test="${ratingsCount == 1}"><b>once</b></c:if><c:if test="${ratingsCount != 1}"><b>${ratingsCount}</b> times</c:if>
					</div>
				</c:forEach>
			</div>
			<div class="col-sm-2 col-md-2 col-lg-2"></div>
		</div>
	
	
	<hr>
	<div class = "row">
			<div class="col-sm-1 col-md-1 col-lg-1"></div>
			<div class="col-sm-5 col-md-5 col-lg-5">
				<p class=caps><c:if test="${gamePlay == 'gtn'}">guess my number</c:if></p>
				<p class=caps><c:if test="${gamePlay != 'gtn'}">${gamePlay}</c:if></p>
				<jsp:include page="/${gamePlay}"></jsp:include>
			</div>
			<div class="col-sm-6 col-md-6 col-lg-6">
				<div class="inline2">
					<c:if test="${scores != null}">
						<table>
							<thead>
								<tr>
									<th colspan="2">Top 10</th>
								</tr>
								<tr>
									<th>Player</th>
									<th>Score</th>
								</tr>
							</thead>
							<c:forEach items="${scores}" var="score">
								<tr>
									<td><div class="comment">${score.player.playerName}</div></td>
									<td><div class="comment">${score.score}</div></td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
				</div>
				<div class="inline2">
					<c:if test="${comments != null}">
						<table>
							<thead>
								<tr>
									<th colspan="2">List of comments</th>
								</tr>
								<tr>
									<th>Comment</th>
									<th>Added by</th>
								</tr>
							</thead>
							<c:forEach items="${comments}" var="comment">
								<tr>
									<td><div class="comment">${comment.comment}</div></td>
									<td><div class="comment">${comment.player.playerName}</div></td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
				</div>
				<div class="spolu">
					<c:if test="${player != null}">
					<div>
					<c:if test="${gamePlay != null}">
						<div class="inlineL">
							<p><c:if test="${gamePlay == 'gtn'}">Rate Guess my number</c:if></p>
							<p><c:if test="${gamePlay != 'gtn'}">Rate ${gamePlay}</c:if></p>
							<a href="?action=insertRating&amp;rating=1&amp;game=${gamePlay}"><img alt="star1" src="images/stars/star1.png"></a>
							<a href="?action=insertRating&amp;rating=2&amp;game=${gamePlay}"><img alt="star2" src="images/stars/star2.png"></a>
							<a href="?action=insertRating&amp;rating=3&amp;game=${gamePlay}"><img alt="star3" src="images/stars/star3.png"></a>
							<a href="?action=insertRating&amp;rating=4&amp;game=${gamePlay}"><img alt="star4" src="images/stars/star4.png"></a>
							<a href="?action=insertRating&amp;rating=5&amp;game=${gamePlay}"><img alt="star5" src="images/stars/star5.png"></a>
						</div>
						<div class="inlineL">
						<p><c:if test="${gamePlay == 'gtn'}">Add comment for Guess my number</c:if></p>
						<p><c:if test="${gamePlay != 'gtn'}">Add comment for ${gamePlay}</c:if></p>
						<form>
				        	<input type="hidden" name="action" value="insertComment" />
				        	<input type="hidden" name="game" value="${gamePlay}" />
				        	<div class="addSubmit">
				        		<div>					
				        		    <textarea placeholder="your comment" rows="4" cols="30" maxlength="200" name="comment"></textarea>
					        	</div>
					        	<div>
						        	<input type="submit" value="Comment" />
						        </div>
					        </div>
						</form>
						</div>
						</c:if>	
						</div>
					</c:if>			
				</div>
			</div>
	</div>
	<div class="row">
			<div class="col-sm-3 col-md-4 col-lg-4"></div>
			<div class="col-sm-6 col-md-4 col-lg-4">
				<a href="/ToBudeTazke/Studiostats">Gamestudio statistics</a>
			</div>
			<div class="col-sm-3 col-md-4 col-lg-4"></div>
		</div>
		
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>	
	<script>window.jQuery || document.write('<script src="js/jquery.min.js"><\/script>')</script>
</body>
</html>