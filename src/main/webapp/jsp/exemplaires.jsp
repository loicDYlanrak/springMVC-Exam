<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exemplaires - ${livre.titre}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .navbar-brand {
            font-weight: bold;
        }
        .text-title {
            color: white;
            text-align: center;
            padding: 7px;
        }
        .table-container {
            margin-top: 20px;
        }
    </style>
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

    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissible fade show mt-3" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <div class="container mt-4">
        <h2>Exemplaires du livre : ${livre.titre}</h2>
        <div class="table-container">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID Exemplaire</th>
                        <th>Statut</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${exemplaires}" var="exemplaire">
                        <tr>
                            <td>${exemplaire.id_exemplaire}</td>
                            
                                <td>
                                    
                                    <c:if test="${not empty exemplaire.currentStatus}">
                                        ${exemplaire.currentStatus.etat.libelle}
                                    </c:if>
                                </td>

     
                            
                            <td>
                                <c:choose>
                                    <c:when test="${exemplaire.statusExemplaires[0].etat.libelle eq 'disponible'}">
                                        Disponible
                                    </c:when>
                                    <c:otherwise>
                                        Non disponible
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${not empty sessionScope.adherent}">
                                    <c:if test="${exemplaire.statusExemplaires.stream().anyMatch(s -> s.etat.libelle == 'disponible')}">
                                        <form action="<c:url value='/livre/reserver'/>" method="post">
                                            <input type="hidden" name="id_exemplaire" value="${exemplaire.id_exemplaire}">
                                            <input type="hidden" name="id_adherent" value="${sessionScope.adherent.id_adherent}">
                                            <div class="mb-3">
                                                <label for="date_reservation_${exemplaire.id_exemplaire}" class="form-label">Date de réservation</label>
                                                <input type="datetime-local" class="form-control" id="date_reservation_${exemplaire.id_exemplaire}" name="date_reservation" required>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Réserver</button>
                                        </form>
                                    </c:if>
                                </c:if>
                                <c:if test="${not empty sessionScope.bibliothecaire}">
                                    <a href="<c:url value='/livre/retour/${exemplaire.id_exemplaire}'/>" class="btn btn-warning btn-sm">Retour</a>
                                    <a href="<c:url value='/livre/prolonger/${exemplaire.id_exemplaire}'/>" class="btn btn-info btn-sm">Prolonger</a>
                                    <form action="<c:url value='/livre/reserver'/>" method="post" class="d-inline">
                                        <input type="hidden" name="id_exemplaire" value="${exemplaire.id_exemplaire}">
                                        <div class="mb-3">
                                            <label for="id_adherent_${exemplaire.id_exemplaire}" class="form-label">ID Adhérent</label>
                                            <input type="number" class="form-control" id="id_adherent_${exemplaire.id_exemplaire}" name="id_adherent" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="date_reservation_${exemplaire.id_exemplaire}" class="form-label">Date de réservation</label>
                                            <input type="datetime-local" class="form-control" id="date_reservation_${exemplaire.id_exemplaire}" name="date_reservation" required>
                                        </div>
                                        <button type="submit" class="btn btn-primary btn-sm">Réserver</button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>