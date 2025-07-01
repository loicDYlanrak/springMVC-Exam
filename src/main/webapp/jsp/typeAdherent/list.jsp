<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" value="Liste des Types d'Adhérent" scope="request" />
<c:set var="content" value="typeAdherent/list-content" scope="request" />
<jsp:include page="/jsp/template.jsp" />

<c:remove var="content" />
<c:remove var="title" />

<template id="typeAdherent/list-content">
    <h1 class="mb-4">Liste des Types d'Adhérent</h1>
    <a href="<c:url value='/types-adherent/new'/>" class="btn btn-primary mb-3">Ajouter un Type</a>
    
    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Libellé</th>
                <th>Durée Prêt</th>
                <th>Quota</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${typesAdherent}" var="type">
                <tr>
                    <td>${type.id_type_adherent}</td>
                    <td>${type.libelle}</td>
                    <td>${type.duree_pret} jours</td>
                    <td>${type.quota}</td>
                    <td>
                        <a href="<c:url value='/types-adherent/edit/${type.id_type_adherent}'/>" class="btn btn-sm btn-warning">Modifier</a>
                        <a href="<c:url value='/types-adherent/delete/${type.id_type_adherent}'/>" class="btn btn-sm btn-danger" onclick="return confirm('Êtes-vous sûr?')">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</template>