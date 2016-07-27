<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Comments</title>
</head>
<body>
	<table>
		<c:forEach items="${scores}" var="score">
                <tr>
                    <td>${student.firstName}
                    <td>${student.lastName}
                    <td><a href="?action=view&id=${student.id}">View</a>
                    <td><a href="?action=edit&id=${student.id}">Edit</a>
                </tr>
            </c:forEach>
	</table>
</body>
</html>