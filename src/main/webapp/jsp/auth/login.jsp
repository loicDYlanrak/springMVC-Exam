<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h3 class="text-center">Connexion</h3>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty param.error}">
                            <div class="alert alert-danger">Identifiants incorrects</div>
                        </c:if>
                        <c:if test="${not empty param.success}">
                            <div class="alert alert-success">Inscription réussie! Veuillez vous connecter</div>
                        </c:if>
                        
                        <ul class="nav nav-tabs" id="loginTabs" role="tablist">
                            <li class="nav-item" role="presentation">
                                <button class="nav-link active" id="adherent-tab" data-bs-toggle="tab" data-bs-target="#adherent" type="button" role="tab">Adhérent</button>
                            </li>
                            <li class="nav-item" role="presentation">
                                <button class="nav-link" id="bibliothecaire-tab" data-bs-toggle="tab" data-bs-target="#bibliothecaire" type="button" role="tab">Bibliothécaire</button>
                            </li>
                        </ul>
                        
                        <div class="tab-content mt-3" id="loginTabsContent">
                            <div class="tab-pane fade" id="adherent" role="tabpanel">
                                <form action="<c:url value='/login/adherent'/>" method="post">
                                    <div class="mb-3">
                                        <label for="email" class="form-label">Email</label>
                                        <input type="email" class="form-control" id="email" name="email" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="password" class="form-label">Mot de passe</label>
                                        <input type="password" class="form-control" id="password" name="password" required>
                                    </div>
                                    <button type="submit" class="btn btn-primary w-100">Se connecter</button>
                                </form>
                            </div>
                            
                            <div class="tab-pane fade  show active" id="bibliothecaire" role="tabpanel">
                                <form action="<c:url value='/login/bibliothecaire'/>" method="post">
                                    <div class="mb-3">
                                        <label for="nom" class="form-label">Nom</label>
                                        <input type="text" class="form-control" id="nom" name="nom" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="passwordBiblio" class="form-label">Mot de passe</label>
                                        <input type="password" class="form-control" id="passwordBiblio" name="password" required>
                                    </div>
                                    <button type="submit" class="btn btn-primary w-100">Se connecter</button>
                                </form>
                            </div>
                        </div>
                        
                        <div class="mt-3 text-center">
                            <p>Pas encore inscrit? <a href="<c:url value='/inscription'/>">Créer un compte</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>