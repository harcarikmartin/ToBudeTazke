<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<c:if test="${player != null}">
<c:if test="${gamePlay != null}">
<hr>
<form>
        	<input type="hidden" name="action" value="insertComment" />
        	<input type="hidden" name="game" value="${gamePlay}" />
        	<div class="addSubmit">
        		<div>
        			<p>Add comment</p>
        		</div>
        		<div>
        			<input type="text" placeholder="your comment" maxlength="200" name="comment">
        		</div>
        		<div>
	        		<input type="submit" value="Comment" />
	        	</div>
        	</div>
    </form>
</c:if>
</c:if>