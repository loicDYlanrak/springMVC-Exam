<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" value="Modifier un Livre" scope="request" />
<c:set var="content" value="livre/edit-content" scope="request" />
<jsp:include page="/jsp/template.jsp" />

<c:remove var="content" />
<c:remove var="title" />

<template id="livre/edit-content">
    <h1 class="mb-4">Modifier un Livre</h1>
    
    <form action="<c:url value='/livres/save'/>" method="post">
        <input type="hidden" name="id_livre" value="${livre.id_livre}">
        <div class="mb-3">
            <label for="titre" class="form-label">Titre</label>
            <input type="text" class="form-control" id="titre" name="titre" value="${livre.titre}" required>
        </div>
        <div class="mb-3">
            <label for="auteur" class="form-label">Auteur</label>
            <input type="text" class="form-control" id="auteur" name="auteur" value="${livre.auteur}" required>
        </div>
        <div class="mb-3">
            <label for="age_minimum" class="form-label">Âge minimum</label>
            <input type="number" class="form-control" id="age_minimum" name="age_minimum" value="${livre.age_minimum}" required>
        </div>
        <div class="mb-3">
            <label for="annee_publication" class="form-label">Année de publication</label>
            <input type="number" class="form-control" id="annee_publication" name="annee_publication" value="${livre.annee_publication}">
        </div>
        <button type="submit" class="btn btn-primary">Enregistrer</button>
        <a href="<c:url value='/livres'/>" class="btn btn-secondary">Annuler</a>
    </form>
</template>