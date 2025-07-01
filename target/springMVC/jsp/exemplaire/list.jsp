<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" value="Liste des Exemplaires" scope="request" />
<c:set var="content" value="exemplaire/list-content" scope="request" />
<jsp:include page="/jsp/template.jsp" />

<c:remove var="content" />
<c:remove var="title" />

<template id="exemplaire/list-content">
    <h1 class="mb-4">Liste des Exemplaires</h1>
    <a href="<c:url value='/exemplaires/new'/>" class="btn btn-primary mb-3">Ajouter un Exemplaire</a>
    
    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Livre</th>
                <th>Auteur</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${exemplaires}" var="exemplaire">
                <tr>
                    <td>${exemplaire.id_exemplaire}</td>
                    <td>${exemplaire.livre.titre}</td>
                    <td>${exemplaire.livre.auteur}</td>
                    <td>
                        <a href="<c:url value='/exemplaires/edit/${exemplaire.id_exemplaire}'/>" class="btn btn-sm btn-warning">Modifier</a>
                        <a href="<c:url value='/exemplaires/delete/${exemplaire.id_exemplaire}'/>" class="btn btn-sm btn-danger" onclick="return confirm('Êtes-vous sûr?')">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</template>