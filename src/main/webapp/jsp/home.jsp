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
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/">Bibliothèque</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/">Accueil</a>
                    </li>
                </ul>
                <div class="d-flex">
                    <c:choose>
                        <c:when test="${not empty sessionScope.adherent or not empty sessionScope.bibliothecaire}">
                            <a href="/logout" class="btn btn-outline-light">Déconnexion</a>
                        </c:when>
                        <c:otherwise>
                            <a href="/login" class="btn btn-outline-light me-2">Connexion</a>
                            <a href="/inscription" class="btn btn-primary">Inscription</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="search-box">
            <form action="/" method="get" class="d-flex">
                <input type="text" name="search" class="form-control me-2" placeholder="Rechercher un livre..." value="${param.search}">
                <button type="submit" class="btn btn-primary">Rechercher</button>
            </form>
        </div>

        <div class="row">
            <c:forEach items="${livres}" var="livre">
                <div class="col-md-4">
                    <div class="card book-card">
                        <img src="https://via.placeholder.com/300x200?text=${livre.titre}" class="card-img-top book-img" alt="${livre.titre}">
                        <div class="card-body">
                            <h5 class="card-title">${livre.titre}</h5>
                            <p class="card-text">Auteur: ${livre.auteur}</p>
                            <p class="card-text">Âge minimum: ${livre.age_minimum} ans</p>
                            <c:if test="${not empty livre.annee_publication}">
                                <p class="card-text">Année: ${livre.annee_publication}</p>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>