<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Prêt d'un exemplaire</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h4 class="text-center">Formulaire de prêt</h4>
                    </div>
                    <div class="card-body">
                        <form action="<c:url value='/livre/pret'/>" method="post">
                            <input type="hidden" name="id_livre" value="${idLivre}">
                            
                            <div class="mb-3">
                                <label for="id_exemplaire" class="form-label">Numéro d'exemplaire</label>
                                <input type="number" class="form-control" id="id_exemplaire" name="id_exemplaire" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="id_adherent" class="form-label">Numéro d'adhérent</label>
                                <input type="number" class="form-control" id="id_adherent" name="id_adherent" required>
                            </div>
                            
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">Valider le prêt</button>
                                <a href="<c:url value='/'/>" class="btn btn-secondary">Annuler</a>
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