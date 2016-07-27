<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<c:if test="${scores != null}">
<hr>
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
			<td>${score.player.playerName}</td>
			<td>${score.score}</td>
		</tr>
	</c:forEach>
</table>
</c:if>