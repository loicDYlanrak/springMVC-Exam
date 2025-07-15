<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Réservations en cours</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
      <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="<c:url value='/'/>">Bibliothèque</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/'/>">Accueil</a>
                    </li>
                    <c:if test="${not empty sessionScope.bibliothecaire}">
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/inscription/form'/>">Inscrire Adhérent</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/inscription/form'/>">Entrer Abonnement</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/reservations'/>">Réservations en cours</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/penalites/statistiques'/>">Statistiques des pénalités</a>
                        </li>
                    </c:if>
                </ul>
                <div class="d-flex">
                    <c:choose>
                        <c:when test="${not empty sessionScope.adherent}">
                            <div class="text-title">Connecté en tant qu'adhérent</div>
                            <a href="<c:url value='/logout'/>" class="btn btn-outline-light ms-2">Déconnexion</a>
                        </c:when>
                        <c:when test="${not empty sessionScope.bibliothecaire}">
                            <div class="text-title">Connecté en tant que bibliothécaire</div>
                            <a href="<c:url value='/logout'/>" class="btn btn-outline-light ms-2">Déconnexion</a>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value='/login'/>" class="btn btn-outline-light me-2">Connexion Adhérent</a>
                            <a href="<c:url value='/login'/>" class="btn btn-primary">Connexion Bibliothécaire</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </nav>
    <c:if test="${not empty param.message}">
        <div class="alert alert-info alert-dismissible fade show mt-3" role="alert">
            ${param.message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <div class="container mt-4">
        <h2>Réservations en cours</h2>
        <form method="get" action="<c:url value='/reservations'/>" class="mb-3">
            <div class="row">
                <div class="col-md-4">
                    <input type="text" name="search" class="form-control" placeholder="Rechercher par adhérent ou livre">
                </div>
                <div class="col-md-4">
                    <select name="filter" class="form-select">
                        <option value="">-- Filtrer par état --</option>
                        <option value="valide">Validé</option>
                        <option value="non_valide">Non validé</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <button type="submit" class="btn btn-primary">Rechercher</button>
                </div>
            </div>
        </form>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Adhérent</th>
                    <th>Livre</th>
                    <th>Date de réservation</th>
                    <th>État</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${reservations}" var="reservation">
                    <tr>
                        <td>${reservation.adherent.nom}</td>
                        <td>${reservation.exemplaire.livre.titre}</td>
                        <td>${reservation.date_reservation}</td>
                        <td>
                            <c:choose>
                                <c:when test="${reservation.valide == null}">
                                    En attente de validation
                                </c:when>
                                <c:when test="${reservation.valide == false}">
                                    Refusée
                                </c:when>
                                <c:otherwise>
                                    Validé
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${reservation.valide == null}">
                                <form action="<c:url value='/reservations/valider/${reservation.id_reservation}'/>" method="post" style="display:inline;">
                                    <button type="submit" class="btn btn-success btn-sm">Valider</button>
                                </form>
                                <form action="<c:url value='/reservations/refuser/${reservation.id_reservation}'/>" method="post" style="display:inline; margin-left:5px;">
                                    <button type="submit" class="btn btn-danger btn-sm">Refuser</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
