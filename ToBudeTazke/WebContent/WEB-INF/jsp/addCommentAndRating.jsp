<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<c:if test="${gamePlay != null}">
<form>
        	<input type="hidden" name="action" value="insert" />
        	<input type="hidden" name="game" value="${gamePlay}" />
        	<table>
        		<thead>
        			<tr>
        				<th colspan="2">Add comment and rating</th>
        			</tr>
        		</thead>
	        	<tr>
	        		<td>Name </td>
	        		<td><input type="text" name="userName"></td>
	        	</tr>
	        	<tr>
	        		<td>Comment</td>
	        		<td><input type="text" name="comment"></td>
	        	</tr>
	        	<tr>
	        		<td>Rating</td>
	        		<td class="ratingRadio">
	        			<input type="radio" name="rating" value="1">*<br>
	        			<input type="radio" name="rating" value="2">**<br>
	        			<input type="radio" name="rating" value="3">***<br>
	        			<input type="radio" name="rating" value="4">****<br>
	        			<input type="radio" name="rating" value="5">*****<br>
	        		</td>
	        	</tr>
        	</table>
        	<input type="submit" value="Add" />
    </form>
</c:if>