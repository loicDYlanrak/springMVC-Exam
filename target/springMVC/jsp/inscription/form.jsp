<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscription</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h3 class="text-center">Inscription Adhérent</h3>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty param.error}">
                            <div class="alert alert-danger">Erreur lors de l'inscription</div>
                        </c:if>
                        
                        <form action="/inscription" method="post">
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="nom" class="form-label">Nom complet</label>
                                    <input type="text" class="form-control" id="nom" name="nom" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="email" class="form-label">Email</label>
                                    <input type="email" class="form-control" id="email" name="email" required>
                                </div>
                            </div>
                            
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="pwd" class="form-label">Mot de passe</label>
                                    <input type="password" class="form-control" id="pwd" name="pwd" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="dateNaissance" class="form-label">Date de naissance</label>
                                    <input type="date" class="form-control" id="dateNaissance" name="dateNaissance" required>
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="id_type_adherent" class="form-label">Type d'adhérent</label>
                                <select class="form-select" id="id_type_adherent" name="id_type_adherent" required>
                                    <option value="">Sélectionner un type</option>
                                    <c:forEach items="${typesAdherent}" var="type">
                                        <option value="${type.id_type_adherent}">${type.libelle}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary">S'inscrire</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>