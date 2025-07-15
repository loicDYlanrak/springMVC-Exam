<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" value="Modifier un Type d'Adhérent" scope="request" />
<c:set var="content" value="typeAdherent/edit-content" scope="request" />
<jsp:include page="/jsp/layout/template.jsp" />

<c:remove var="content" />
<c:remove var="title" />

<template id="typeAdherent/edit-content">
    <h1 class="mb-4">Modifier un Type d'Adhérent</h1>
    
    <form action="<c:url value='/types-adherent/save'/>" method="post">
        <input type="hidden" name="id_type_adherent" value="${typeAdherent.id_type_adherent}">
        <div class="mb-3">
            <label for="libelle" class="form-label">Libellé</label>
            <input type="text" class="form-control" id="libelle" name="libelle" value="${typeAdherent.libelle}" required>
        </div>
        <div class="mb-3">
            <label for="duree_pret" class="form-label">Durée de prêt (jours)</label>
            <input type="number" class="form-control" id="duree_pret" name="duree_pret" value="${typeAdherent.duree_pret}" required>
        </div>
        <div class="mb-3">
            <label for="quota" class="form-label">Quota</label>
            <input type="number" class="form-control" id="quota" name="quota" value="${typeAdherent.quota}" required>
        </div>
        <div class="mb-3">
            <label for="nb_reservation_max" class="form-label">Nombre max de réservations</label>
            <input type="number" class="form-control" id="nb_reservation_max" name="nb_reservation_max" value="${typeAdherent.nb_reservation_max}" required>
        </div>
        <div class="mb-3">
            <label for="duree_penalite" class="form-label">Durée de pénalité (jours)</label>
            <input type="number" class="form-control" id="duree_penalite" name="duree_penalite" value="${typeAdherent.duree_penalite}" required>
        </div>
        <div class="mb-3">
            <label for="nb_jour_max_prologement" class="form-label">Nombre max de jours de prolongement</label>
            <input type="number" class="form-control" id="nb_jour_max_prologement" name="nb_jour_max_prologement" value="${typeAdherent.nb_jour_max_prologement}" required>
        </div>
        <button type="submit" class="btn btn-primary">Enregistrer</button>
        <a href="<c:url value='/types-adherent'/>" class="btn btn-secondary">Annuler</a>
    </form>
</template>