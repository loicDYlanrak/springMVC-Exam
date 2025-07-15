<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter un Livre</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="<c:url value='/'/>">Bibliothèque</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
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
    <main>
        <div class="container">
            <h1 class="mb-4">Ajouter un Livre</h1>
            <form action="<c:url value='/livres/save'/>" method="post">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="titre" class="form-label">Titre</label>
                        <input type="text" class="form-control" id="titre" name="titre" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="auteur" class="form-label">Auteur</label>
                        <input type="text" class="form-control" id="auteur" name="auteur" required>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="age_minimum" class="form-label">Âge minimum</label>
                        <input type="number" class="form-control" id="age_minimum" name="age_minimum" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="annee_publication" class="form-label">Année de publication</label>
                        <input type="number" class="form-control" id="annee_publication" name="annee_publication">
                    </div>
                </div>
                <div class="mb-3">
                    <button type="submit" class="btn btn-primary">Enregistrer</button>
                    <a href="<c:url value='/livres'/>" class="btn btn-secondary">Annuler</a>
                </div>
            </form>
        </div>
    </main>
    <footer>
        <!-- Include your footer content here -->
    </footer>
</body>
</html>