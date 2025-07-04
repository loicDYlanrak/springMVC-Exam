<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bibliothèque - Accueil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .navbar-brand {
            font-weight: bold;
        }
        .search-box {
            margin: 20px 0;
        }
        .book-card {
            margin-bottom: 20px;
            height: 100%;
        }
        .book-img {
            height: 200px;
            object-fit: cover;
        }
        .text-title {
            color: white;
            text-align: center;
            padding: 7px;
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
                    <c:if test="${not empty sessionScope.bibliothecaire}">
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/inscription/form'/>">Inscrire Adhérent</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/inscription/form'/>">Entrer Abonnement</a>
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
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissible fade show mt-3" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <div class="container mt-4">
        <div class="search-box">
            <form action="<c:url value='/'/>" method="get" class="d-flex">
                <input type="text" name="search" class="form-control me-2" placeholder="Rechercher un livre..." value="${param.search}">
                <button type="submit" class="btn btn-primary">Rechercher</button>
            </form>
        </div>

        <div class="row">
            <c:forEach items="${livres}" var="livre">
                <div class="col-md-4 mb-4">
                    <div class="card book-card">
                        <div class="card-body">
                            <h5 class="card-title">${livre.titre}</h5>
                            <p class="card-text">Auteur: ${livre.auteur}</p>
                            <p class="card-text">Âge minimum: ${livre.age_minimum} ans</p>
                            <c:if test="${not empty livre.annee_publication}">
                                <p class="card-text">Année: ${livre.annee_publication}</p>
                            </c:if>
                        </div>
                        <div class="card-footer">
                            <a href="<c:url value='/livre/exemplaires/${livre.id_livre}'/>" class="btn btn-primary">Voir les exemplaires</a>
                            
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>