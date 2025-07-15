<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" value="Modifier un Bibliothécaire" scope="request" />
<c:set var="content" value="bibliothecaire/edit-content" scope="request" />
<jsp:include page="/jsp/layout/template.jsp" />

<c:remove var="content" />
<c:remove var="title" />

<template id="bibliothecaire/edit-content">
    <h1 class="mb-4">Modifier un Bibliothécaire</h1>
    
    <form action="<c:url value='/bibliothecaires/save'/>" method="post">
        <input type="hidden" name="id_biblio" value="${bibliothecaire.id_biblio}">
        <div class="mb-3">
            <label for="nom" class="form-label">Nom</label>
            <input type="text" class="form-control" id="nom" name="nom" value="${bibliothecaire.nom}" required>
        </div>
        <div class="mb-3">
            <label for="pwd" class="form-label">Mot de passe</label>
            <input type="password" class="form-control" id="pwd" name="pwd" value="${bibliothecaire.pwd}" required>
        </div>
        <button type="submit" class="btn btn-primary">Enregistrer</button>
        <a href="<c:url value='/bibliothecaires'/>" class="btn btn-secondary">Annuler</a>
    </form>
</template>