<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<c:if test="${gamePlay != null}">
<hr>
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
	        		<td class="commentRating">Comment</td>
	        		<td class="commentRating">Rating</td>
	        	</tr>
	        	<tr>
	        		<td class="commentRating"><input type="text" placeholder="your comment" maxlength="200" name="comment"></td>
	        		<td class="commentRating">
	        			<input type="radio" name="rating" value="1">*<br>
	        			<input type="radio" name="rating" value="2">**<br>
	        			<input type="radio" name="rating" value="3">***<br>
	        			<input type="radio" name="rating" value="4">****<br>
	        			<input type="radio" name="rating" value="5">*****<br>
	        		</td>
	        	</tr>
        	</table>
        	<div class="addSubmit">
        	<input type="submit" value="Add" />
        	</div>
    </form>
</c:if>

<!--     


 -->