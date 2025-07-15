
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Bibliothèque</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/bibliothecaires'/>">Bibliothécaires</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/types-adherent'/>">Types Adhérent</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/livres'/>">Livres</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/exemplaires'/>">Exemplaires</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        
        <jsp:include page="${content}" />
        
        <footer class="mt-5 mb-3 text-center">
            <p>© 2023 Bibliothèque - Tous droits réservés</p>
        </footer>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>