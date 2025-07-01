<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" value="Liste des Bibliothécaires" scope="request" />
<c:set var="content" value="bibliothecaire/list-content" scope="request" />
<jsp:include page="/jsp/template.jsp" />

<c:remove var="content" />
<c:remove var="title" />

<template id="bibliothecaire/list-content">
    <h1 class="mb-4">Liste des Bibliothécaires</h1>
    <a href="<c:url value='/bibliothecaires/new'/>" class="btn btn-primary mb-3">Ajouter un Bibliothécaire</a>
    
    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${bibliothecaires}" var="biblio">
                <tr>
                    <td>${biblio.id_biblio}</td>
                    <td>${biblio.nom}</td>
                    <td>
                        <a href="<c:url value='/bibliothecaires/edit/${biblio.id_biblio}'/>" class="btn btn-sm btn-warning">Modifier</a>
                        <a href="<c:url value='/bibliothecaires/delete/${biblio.id_biblio}'/>" class="btn btn-sm btn-danger" onclick="return confirm('Êtes-vous sûr?')">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</template>