<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter un Exemplaire</title>
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
            <h1 class="mb-4">Ajouter un Exemplaire</h1>
            <form action="<c:url value='/exemplaires/save'/>" method="post">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="livre" class="form-label">Livre</label>
                        <select class="form-select" id="livre" name="livre.id_livre" required>
                            <option value="">Sélectionner un livre</option>
                            <c:forEach items="${livres}" var="livre">
                                <option value="${livre.id_livre}">${livre.titre} (${livre.auteur})</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="mb-3">
                    <button type="submit" class="btn btn-primary">Enregistrer</button>
                    <a href="<c:url value='/exemplaires'/>" class="btn btn-secondary">Annuler</a>
                </div>
            </form>
        </div>
    </main>
    <footer>
        <!-- Include your footer content here -->
    </footer>
</body>
</html>