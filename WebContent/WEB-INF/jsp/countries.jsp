<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pays</title>
</head>
<body>

<c:set var="countries" value="${requestScope.countries}" />

<c:forEach var="c" items="${countries}">
	<p> <c:out value="${c.name} (${c.abbreviation})" /> <a href="/contacts/app?what_to_do=country_delete&id=${c.id}"> supprimer </a> </p>
</c:forEach>

<hr/>

<h4>Ajouter un Pays</h4>

	<form method="post" action="/contacts/app">
		<input type="hidden" name="what_to_do" value="country_add"/>
		<p>Nom: <input type="text" name="name"/></p>
		<p>Abreviation: <input type="text" name="abbreviation"/></p>
		<input type="submit" value="ajouter"/>
	</form>

<p><a href="/contacts/">index</a></p>

</body>
</html>