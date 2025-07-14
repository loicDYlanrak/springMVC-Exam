<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscription Adherent</title>
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
        .reserve-btn {
            margin-bottom: 10px;
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

        <h2>Inscription d'un nouvel adherent</h2>

        <c:if test="${not empty message}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <div class="row">
            <div class="col-md-6">
                <div class="card mb-4">
                    <div class="card-header">
                        <h5>Inscription Adherent</h5>
                    </div>
                    <div class="card-body">
                        <form action="<c:url value='/inscription/adherent'/>" method="post">
                            <div class="mb-3">
                                <label for="nom" class="form-label">Nom complet</label>
                                <input type="text" class="form-control" id="nom" name="nom" required>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>
                            <div class="mb-3">
                                <label for="pwd" class="form-label">Mot de passe</label>
                                <input type="password" class="form-control" id="pwd" name="pwd" required>
                            </div>
                            <div class="mb-3">
                                <label for="dateNaissance" class="form-label">Date de naissance</label>
                                <input type="date" class="form-control" id="dateNaissance" name="dateNaissance" required>
                            </div>
                            <div class="mb-3">
                                <label for="idTypeAdherent" class="form-label">Type d'adherent</label>
                                <select class="form-select" id="idTypeAdherent" name="idTypeAdherent" required>
                                    <option value="">-- Selectionnez un type --</option>
                                    <c:forEach items="${typesAdherent}" var="type">
                                        <option value="${type.id_type_adherent}">${type.libelle}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">Inscrire</button>
                        </form>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h5>Creation d'abonnement</h5>
                    </div>
                    <div class="card-body">
                        <form action="<c:url value='/inscription/abonnement'/>" method="post">
                            <div class="mb-3">
                                <label for="modalBiblioAdherentId" class="form-label">Adhérent</label>
                                <select class="form-select" id="modalBiblioAdherentId" name="idAdherent" required>
                                    <option value="">-- Sélectionnez un adhérent --</option>
                                    <c:forEach items="${adherents}" var="adherent">
                                        <option value="${adherent.id_adherent}">
                                            ${adherent.nom} (ID: ${adherent.id_adherent})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="dateDebut" class="form-label">Date de debut</label>
                                <input type="date" class="form-control" id="dateDebut" name="dateDebut" required>
                            </div>
                            <div class="mb-3">
                                <label for="dateFin" class="form-label">Date de fin</label>
                                <input type="date" class="form-control" id="dateFin" name="dateFin" required>
                            </div>
                            <button type="submit" class="btn btn-primary">Creer abonnement</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>