<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" value="Ajouter un Exemplaire" scope="request" />
<c:set var="content" value="exemplaire/add-content" scope="request" />
<jsp:include page="/jsp/template.jsp" />

<c:remove var="content" />
<c:remove var="title" />

<template id="exemplaire/add-content">
    <h1 class="mb-4">Ajouter un Exemplaire</h1>
    
    <form action="<c:url value='/exemplaires/save'/>" method="post">
        <div class="mb-3">
            <label for="livre" class="form-label">Livre</label>
            <select class="form-select" id="livre" name="livre.id_livre" required>
                <option value="">Sélectionner un livre</option>
                <c:forEach items="${livres}" var="livre">
                    <option value="${livre.id_livre}">${livre.titre} (${livre.auteur})</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Enregistrer</button>
        <a href="<c:url value='/exemplaires'/>" class="btn btn-secondary">Annuler</a>
    </form>
</template>