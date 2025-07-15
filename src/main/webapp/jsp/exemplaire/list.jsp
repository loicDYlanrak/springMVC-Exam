<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Exemplaires</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="<c:url value='/'/>">Bibliothèque</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/livre'/>">Livres</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/exemplaires'/>">Exemplaires</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h1 class="mb-4">Liste des Exemplaires</h1>
        <form action="<c:url value='/exemplaires'/>" method="get" class="mb-3">
            <div class="row">
                <div class="col-md-4">
                    <input type="text" name="search" class="form-control" placeholder="Rechercher un exemplaire..." value="${param.search}">
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary">Rechercher</button>
                </div>
            </div>
        </form>
        <a href="<c:url value='/exemplaires/new'/>" class="btn btn-primary mb-3">Ajouter un Exemplaire</a>
        
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Livre</th>
                    <th>Auteur</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${exemplaires}" var="exemplaire">
                    <tr>
                        <td>${exemplaire.id_exemplaire}</td>
                        <td>${exemplaire.livre.titre}</td>
                        <td>${exemplaire.livre.auteur}</td>
                        <td>
                            <a href="<c:url value='/exemplaires/edit/${exemplaire.id_exemplaire}'/>" class="btn btn-sm btn-warning">Modifier</a>
                            <a href="<c:url value='/exemplaires/delete/${exemplaire.id_exemplaire}'/>" class="btn btn-sm btn-danger" onclick="return confirm('Êtes-vous sûr?')">Supprimer</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>