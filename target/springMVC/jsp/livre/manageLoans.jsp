<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gérer les prêts - Exemplaire ${exemplaire.id_exemplaire}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
        <h2>Gérer les prêts pour l'exemplaire ${exemplaire.id_exemplaire}</h2>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Adhérent</th>
                    <th>Date de prêt</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pretsEnCours}" var="pret">
                    <tr>
                        <td>${pret.adherent.nom}</td>
                        <td>${pret.date_pret}</td>
                        <td>
                            <button type="button" class="btn btn-success btn-sm" data-bs-toggle="modal"
                                data-bs-target="#retourModal" data-pret-id="${pret.id_pret}">
                                Retourner
                            </button>
                            <button type="button" class="btn btn-info btn-sm" data-bs-toggle="modal"
                                data-bs-target="#prolongerModal" data-pret-id="${pret.id_pret}">
                                Prolonger
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="<c:url value='/livre/exemplaires/${exemplaire.livre.id_livre}'/>" class="btn btn-secondary">Retour</a>
    </div>

    <!-- Modal for returning a book -->
    <div class="modal fade" id="retourModal" tabindex="-1" aria-labelledby="retourModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="retourModalLabel">Retourner un exemplaire</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="<c:url value='/livre/retour'/>" method="post">
                    <div class="modal-body">
                        <input type="hidden" name="id_pret" id="modalPretId">
                        <div class="mb-3">
                            <label for="modalRetourDate" class="form-label">Date de retour</label>
                            <input type="date" class="form-control" id="modalRetourDate" name="date_retour" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                        <button type="submit" class="btn btn-primary">Valider</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Modal for prolonging a loan -->
    <div class="modal fade" id="prolongerModal" tabindex="-1" aria-labelledby="prolongerModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="prolongerModalLabel">Prolonger un prêt</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="<c:url value='/livre/prolonger'/>" method="post">
                    <div class="modal-body">
                        <input type="hidden" name="id_pret" id="modalProlongerPretId">
                        <div class="mb-3">
                            <label for="modalProlongerDate" class="form-label">Nouvelle date de retour</label>
                            <input type="date" class="form-control" id="modalProlongerDate" name="date_retour" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                        <button type="submit" class="btn btn-primary">Valider</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Script to handle the modal for returning a book
        document.addEventListener('DOMContentLoaded', function () {
            const retourModal = document.getElementById('retourModal');
            if (retourModal) {
                retourModal.addEventListener('show.bs.modal', function (event) {
                    const button = event.relatedTarget;
                    const pretId = button.getAttribute('data-pret-id');
                    const modal = this;
                    modal.querySelector('#modalPretId').value = pretId;
                });
            }
        });

        // Script to handle the modal for prolonging a loan
        document.addEventListener('DOMContentLoaded', function () {
            const prolongerModal = document.getElementById('prolongerModal');
            if (prolongerModal) {
                prolongerModal.addEventListener('show.bs.modal', function (event) {
                    const button = event.relatedTarget;
                    const pretId = button.getAttribute('data-pret-id');
                    const modal = this;
                    modal.querySelector('#modalProlongerPretId').value = pretId;
                });
            }
        });
    </script>
</body>
</html>
