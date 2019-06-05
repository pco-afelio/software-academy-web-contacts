<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Contacts</title>
</head>
<body>

<c:set var="contacts" value="${requestScope.contacts}" />
<c:set var="countries" value="${requestScope.countries}" />
<c:set var="tags" value="${requestScope.tags}" />

<c:forEach var="c" items="${contacts}">
	<p> 
		<c:out value="${c.firstname} ${c.name}" /> 
		<c:if test="${not empty c.country}"> (<c:out value="${c.country.abbreviation}" />) </c:if>
		<c:if test="${f:length(c.tags) gt 0}">: </c:if>
		<c:forEach var="t" items="${c.tags}" varStatus="status">
			<c:out value="${t.value}" />
			<c:if test="${not status.last}">, </c:if>
		</c:forEach>
		<a href="/contacts/app?what_to_do=contact_delete&id=${c.id}"> supprimer </a> 
	</p>
</c:forEach>

<hr/>

<h4>Ajouter un contact</h4>

	<form method="post" action="/contacts/app">
		<input type="hidden" name="what_to_do" value="contact_add"/>
		<p>Prénom: <input type="text" name="firstname"/></p>
		<p>Nom: <input type="text" name="name"/></p>
		<p>Email: <input type="text" name="email"/></p>
		<p>Pays:
			<select name="countryId">
				<option value="none">aucun</option>
				<c:forEach var="c" items="${countries}">
					<option value="${c.id}"><c:out value="${c.name}"/></option>
				</c:forEach>
			</select>
		</p>
		<p>Tags:
			<select name="tagIds" multiple="multiple">
				<option value="none">aucun</option>
				<c:forEach var="t" items="${tags}">
					<option value="${t.id}"><c:out value="${t.value}"/></option>
				</c:forEach>
			</select>
		</p>		
		<input type="submit" value="ajouter"/>
	</form>

<p><a href="/contacts/">index</a></p>

</body>
</html>