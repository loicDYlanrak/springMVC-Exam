<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" value="Ajouter un Bibliothécaire" scope="request" />
<c:set var="content" value="bibliothecaire/add-content" scope="request" />
<jsp:include page="/jsp/template.jsp" />

<c:remove var="content" />
<c:remove var="title" />

<template id="bibliothecaire/add-content">
    <h1 class="mb-4">Ajouter un Bibliothécaire</h1>
    
    <form action="<c:url value='/bibliothecaires/save'/>" method="post">
        <div class="mb-3">
            <label for="nom" class="form-label">Nom</label>
            <input type="text" class="form-control" id="nom" name="nom" required>
        </div>
        <div class="mb-3">
            <label for="pwd" class="form-label">Mot de passe</label>
            <input type="password" class="form-control" id="pwd" name="pwd" required>
        </div>
        <button type="submit" class="btn btn-primary">Enregistrer</button>
        <a href="<c:url value='/bibliothecaires'/>" class="btn btn-secondary">Annuler</a>
    </form>
</template>