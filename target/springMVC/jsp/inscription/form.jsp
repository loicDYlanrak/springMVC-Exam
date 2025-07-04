<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscription Adhérent</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../layout/template.jsp"/>

    <div class="container mt-4">
        <h2>Inscription d'un nouvel adhérent</h2>

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
                        <h5>Inscription Adhérent</h5>
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
                                <label for="idTypeAdherent" class="form-label">Type d'adhérent</label>
                                <select class="form-select" id="idTypeAdherent" name="idTypeAdherent" required>
                                    <option value="">-- Sélectionnez un type --</option>
                                    <c:forEach items="${typesAdherent}" var="type">
                                        <option value="${type.idTypeAdherent}">${type.libelle}</option>
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
                        <h5>Création d'abonnement</h5>
                    </div>
                    <div class="card-body">
                        <form action="<c:url value='/inscription/abonnement'/>" method="post">
                            <div class="mb-3">
                                <label for="idAdherent" class="form-label">ID Adhérent</label>
                                <input type="number" class="form-control" id="idAdherent" name="idAdherent" required>
                            </div>
                            <div class="mb-3">
                                <label for="dateDebut" class="form-label">Date de début</label>
                                <input type="date" class="form-control" id="dateDebut" name="dateDebut" required>
                            </div>
                            <div class="mb-3">
                                <label for="dateFin" class="form-label">Date de fin</label>
                                <input type="date" class="form-control" id="dateFin" name="dateFin" required>
                            </div>
                            <button type="submit" class="btn btn-primary">Créer abonnement</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>