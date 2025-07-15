<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Détails Exemplaire - ${livre.titre}</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <style>
                .navbar-brand {
                    font-weight: bold;
                }

                .details-container {
                    margin-top: 20px;
                }

                .table-container {
                    margin-top: 30px; 
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
                                    <a class="nav-link" href="<c:url value='/penalites/statistiques'/>">Statistiques des
                                        pénalités</a>
                                </li>
                            </c:if>
                        </ul>
                        <div class="d-flex">
                            <c:choose>
                                <c:when test="${not empty sessionScope.adherent}">
                                    <div class="text-title">Connecté en tant qu'adhérent</div>
                                    <a href="<c:url value='/logout'/>"
                                        class="btn btn-outline-light ms-2">Déconnexion</a>
                                </c:when>
                                <c:when test="${not empty sessionScope.bibliothecaire}">
                                    <div class="text-title">Connecté en tant que bibliothécaire</div>
                                    <a href="<c:url value='/logout'/>"
                                        class="btn btn-outline-light ms-2">Déconnexion</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/login'/>" class="btn btn-outline-light me-2">Connexion
                                        Adhérent</a>
                                    <a href="<c:url value='/login'/>" class="btn btn-primary">Connexion
                                        Bibliothécaire</a>
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

            <div class="container details-container">
                <h2>Détails de l'exemplaire #${exemplaire.id_exemplaire}</h2>
                <div class="card">
                    <div class="card-header">
                        <h4>${livre.titre} - ${livre.auteur}</h4>
                    </div>
                    <div class="card-body">
                        <p><strong>Année de publication :</strong> ${livre.annee_publication}</p>
                        <p><strong>Âge minimum requis :</strong> ${livre.age_minimum} ans</p>
                        <div class="actions mt-3">
                            <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                data-bs-target="#reservationModal" data-exemplaire-id="${exemplaire.id_exemplaire}">
                                Réserver
                            </button>
                            <button type="button" class="btn btn-success" data-bs-toggle="modal"
                                data-bs-target="#pretModal" data-exemplaire-id="${exemplaire.id_exemplaire}">
                                Prêter
                            </button>
                            <a href="<c:url value='/livre/exemplaire/loans/${exemplaire.id_exemplaire}'/>"
                                class="btn btn-warning">
                                Gérer Prêts
                            </a>
                        </div>
                    </div>
                </div>

                <div class="table-container">
                    <h4>Liste des réservations</h4>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Adhérent</th>
                                <th>Livre</th>
                                <th>Date de réservation</th>
                                <th>État</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${reservations}" var="reservation">
                                <tr>
                                    <td>${reservation.adherent.nom}</td>
                                    <td>${reservation.exemplaire.livre.titre}</td>
                                    <td>${reservation.date_reservation}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${reservation.valide == null}">
                                                En attente de validation
                                            </c:when>
                                            <c:when test="${reservation.valide == false}">
                                                Refusée
                                            </c:when>
                                            <c:otherwise>
                                                Validé
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${reservation.valide == null}">
                                            <form action="<c:url value='/reservations/valider/${reservation.id_reservation}'/>" method="post" style="display:inline;">
                                                <button type="submit" class="btn btn-success btn-sm">Valider</button>
                                            </form>
                                            <form action="<c:url value='/reservations/refuser/${reservation.id_reservation}'/>" method="post" style="display:inline; margin-left:5px;">
                                                <button type="submit" class="btn btn-danger btn-sm">Refuser</button>
                                            </form>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="table-container">
                    <h4>Historique des prêts</h4>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID Prêt</th>
                                <th>Adhérent</th>
                                <th>Date de prêt</th>
                                <th>Date de retour</th>
                                <th>Type de prêt</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${historiquePrets}" var="pret">
                                <tr>
                                    <td>${pret.id_pret}</td>
                                    <td>${pret.adherent.nom} (ID: ${pret.adherent.id_adherent})</td>
                                    <td>${pret.date_pret}</td>
                                    <td>${pret.date_retour != null ? pret.date_retour : 'En cours'}</td>
                                    <td>${pret.typePret.libelle}</td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty historiquePrets}">
                                <tr>
                                    <td colspan="5" class="text-center">Aucun historique de prêt pour cet exemplaire
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Modal for reservation -->
            <div class="modal fade" id="reservationModal" tabindex="-1" aria-labelledby="reservationModalLabel"
                aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="reservationModalLabel">Réserver un exemplaire</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="<c:url value='/livre/reserver'/>" method="post">
                            <div class="modal-body">
                                <input type="hidden" name="id_exemplaire" id="modalReservationExemplaireId">
                                <div class="mb-3">
                                    <label for="modalReservationAdherentId" class="form-label">Adhérent</label>
                                    <select class="form-select" id="modalReservationAdherentId" name="id_adherent"
                                        required>
                                        <option value="">-- Sélectionnez un adhérent --</option>
                                        <c:forEach items="${adherents}" var="adherent">
                                            <option value="${adherent.id_adherent}">
                                                ${adherent.nom} (ID: ${adherent.id_adherent})
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label for="modalReservationDate" class="form-label">Date de réservation</label>
                                    <input type="datetime-local" class="form-control" id="modalReservationDate"
                                        name="date_reservation" required>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                                <button type="submit" class="btn btn-primary">Confirmer la réservation</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Modal for loan -->
            <div class="modal fade" id="pretModal" tabindex="-1" aria-labelledby="pretModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="pretModalLabel">Prêter un exemplaire</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="<c:url value='/livre/pret'/>" method="post">
                            <div class="modal-body">
                                <input type="hidden" name="id_exemplaire" id="modalPretExemplaireId">
                                <div class="mb-3">
                                    <label for="modalPretAdherentId" class="form-label">Adhérent</label>
                                    <select class="form-select" id="modalPretAdherentId" name="id_adherent" required>
                                        <option value="">-- Sélectionnez un adhérent --</option>
                                        <c:forEach items="${adherents}" var="adherent">
                                            <option value="${adherent.id_adherent}">
                                                ${adherent.nom} (ID: ${adherent.id_adherent})
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label for="modalPretDate" class="form-label">Date de prêt</label>
                                    <input type="date" class="form-control" id="modalPretDate" name="date_pret"
                                        required>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                                <button type="submit" class="btn btn-primary">Confirmer le prêt</button>
                            </div>
                        </form>
                   
            <script>
                document.addEventListener('DOMContentLoaded', function () {
                    const reservationModal = document.getElementById('reservationModal');
                    if (reservationModal) {
                        reservationModal.addEventListener('show.bs.modal', function (event) {
                            const button = event.relatedTarget;
                            const exemplaireId = button.getAttribute('data-exemplaire-id');
                            const modal = this;
                            modal.querySelector('#modalReservationExemplaireId').value = exemplaireId;
                        });
                    }

                    const pretModal = document.getElementById('pretModal');
                    if (pretModal) {
                        pretModal.addEventListener('show.bs.modal', function (event) {
                            const button = event.relatedTarget;
                            const exemplaireId = button.getAttribute('data-exemplaire-id');
                            const modal = this;
                            modal.querySelector('#modalPretExemplaireId').value = exemplaireId;
                        });
                    }
                });
            </script>
        </body>

        </html>