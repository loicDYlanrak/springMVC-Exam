<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" value="Liste des Livres" scope="request" />
<c:set var="content" value="livre/list-content" scope="request" />
<jsp:include page="/jsp/template.jsp" />

<c:remove var="content" />
<c:remove var="title" />

<template id="livre/list-content">
    <h1 class="mb-4">Liste des Livres</h1>
    <a href="<c:url value='/livres/new'/>" class="btn btn-primary mb-3">Ajouter un Livre</a>
    
    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Titre</th>
                <th>Auteur</th>
                <th>Âge minimum</th>
                <th>Année</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${livres}" var="livre">
                <tr>
                    <td>${livre.id_livre}</td>
                    <td>${livre.titre}</td>
                    <td>${livre.auteur}</td>
                    <td>${livre.age_minimum}</td>
                    <td>${livre.annee_publication}</td>
                    <td>
                        <a href="<c:url value='/livres/edit/${livre.id_livre}'/>" class="btn btn-sm btn-warning">Modifier</a>
                        <a href="<c:url value='/livres/delete/${livre.id_livre}'/>" class="btn btn-sm btn-danger" onclick="return confirm('Êtes-vous sûr?')">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</template>