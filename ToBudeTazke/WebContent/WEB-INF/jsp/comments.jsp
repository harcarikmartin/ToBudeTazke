<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:if test="${comments != null}">
<hr>
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
			<td>${comment.comment}</td>
			<td>${comment.player.playerName}</td>
		</tr>
	</c:forEach>
</table>
</c:if>