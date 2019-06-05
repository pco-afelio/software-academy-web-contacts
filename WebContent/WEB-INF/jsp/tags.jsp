<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tags</title>
</head>
<body>

<c:set var="tags" value="${requestScope.tags}" />

<c:forEach var="t" items="${tags}">
	<form method="post" action="/contacts/app">
		<c:out value="${t.value}" />
		<input type="hidden" name="what_to_do" value="tag_delete"/>
		<input type="hidden" name="id" value="${t.id}"/>
		<input type="submit" value="supprimer"/>
	</form>
</c:forEach>

<hr/>

<h4>Ajouter un tag</h4>

	<form method="post" action="/contacts/app">
		<input type="hidden" name="what_to_do" value="tag_add"/>
		<input type="text" name="value"/>
		<input type="submit" value="ajouter"/>
	</form>

<p><a href="/contacts/">index</a></p>

</body>
</html>