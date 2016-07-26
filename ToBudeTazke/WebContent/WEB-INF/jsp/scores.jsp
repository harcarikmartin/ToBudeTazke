<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<c:if test="${scores != null}">
<hr>
<table>
	<thead>
		<tr>
			<th colspan="2">List of scores</th>
		</tr>
		<tr>
			<th>Score</th>
			<th>Player</th>
		</tr>
	</thead>
	<c:forEach items="${scores}" var="score">
		<tr>
			<td>${score.score}</td>
			<td>${score.player.playerName}</td>
		</tr>
	</c:forEach>
</table>
</c:if>