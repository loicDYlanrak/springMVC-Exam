<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Exemplaires - ${livre.titre}</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <style>
                .navbar-brand {
                    font-weight: bold;
                }

                .text-title {
                    color: white;
                    text-align: center;
                    padding: 7px;
                }

                .table-container {
                    margin-top: 20px;
                }

                .reserve-btn {
                    margin-bottom: 10px;
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
                                    <a class="nav-link" href="<c:url value='/penalites/statistiques'/>">Statistiques des pénalités</a>
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
            <div class="container mt-4">
                <h2>Exemplaires du livre : ${livre.titre}</h2>
                <div class="table-container">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID Exemplaire</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${exemplaires}" var="exemplaire">
                                <tr>
                                    <td>${exemplaire.id_exemplaire}</td>
                                    <td>
                                        <c:if test="${not empty sessionScope.adherent}">
                                            <c:if test="${exemplaire.currentStatus.etat.libelle == 'disponible'}">
                                                <button type="button" class="btn btn-primary reserve-btn"
                                                    data-bs-toggle="modal" data-bs-target="#reservationModal"
                                                    data-exemplaire-id="${exemplaire.id_exemplaire}">
                                                    Réserver
                                                </button>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${not empty sessionScope.bibliothecaire}">
                                            <a href="<c:url value='/livre/exemplaire/details/${exemplaire.id_exemplaire}'/>"
                                                class="btn btn-info btn-sm">Voir détails</a>
                                            <button type="button" class="btn btn-success btn-sm" data-bs-toggle="modal"
                                                data-bs-target="#pretModal"
                                                data-exemplaire-id="${exemplaire.id_exemplaire}">
                                                Prêter
                                            </button>
                                            <c:choose>
                                                <c:when test="${exemplaire.currentStatus.etat.libelle == 'prete'}">
                                                    <a href="<c:url value='/livre/exemplaire/loans/${exemplaire.id_exemplaire}'/>"
                                                        class="btn btn-warning btn-sm">
                                                        Gérer Prêts
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal"
                                                data-bs-target="#bibliothecaireReservationModal"
                                                data-exemplaire-id="${exemplaire.id_exemplaire}">
                                                Réserver
                                            </button>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

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
                                <input type="hidden" name="id_exemplaire" id="modalExemplaireId">
                                <input type="hidden" name="id_adherent" value="${sessionScope.adherent.id_adherent}">
                                <div class="mb-3">
                                    <label for="modalDateReservation" class="form-label">Date de réservation</label>
                                    <input type="datetime-local" class="form-control" id="modalDateReservation"
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

            <!-- Modal pour le retour -->
            <div class="modal fade" id="retourModal" tabindex="-1" aria-labelledby="retourModalLabel"
                aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="retourModalLabel">Retourner un exemplaire</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="<c:url value='/livre/retour'/>" method="post">
                            <input type="hidden" name="id_pret" id="modalRetourPretId">
                            <div class="modal-body">
                                <input type="hidden" name="id_exemplaire" id="modalRetourExemplaireId">
                                <div class="mb-3">
                                    <label for="modalRetourDate" class="form-label">Date de retour</label>
                                    <input type="date" class="form-control" id="modalRetourDate" name="date_retour"
                                        required>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                                <button type="submit" class="btn btn-primary">Confirmer le retour</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Modal pour la réservation bibliothécaire -->
            <div class="modal fade" id="bibliothecaireReservationModal" tabindex="-1"
                aria-labelledby="bibliothecaireReservationModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="bibliothecaireReservationModalLabel">Réserver un exemplaire</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="<c:url value='/livre/reserver'/>" method="post">
                            <div class="modal-body">
                                <input type="hidden" name="id_exemplaire" id="modalBiblioExemplaireId">
                                <div class="mb-3">
                                    <label for="modalBiblioAdherentId" class="form-label">Adhérent</label>
                                    <select class="form-select" id="modalBiblioAdherentId" name="id_adherent" required>
                                        <option value="">-- Sélectionnez un adhérent --</option>
                                        <c:forEach items="${adherents}" var="adherent">
                                            <option value="${adherent.id_adherent}">
                                                ${adherent.nom} (ID: ${adherent.id_adherent})
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label for="modalBiblioDateReservation" class="form-label">Date de
                                        réservation</label>
                                    <input type="datetime-local" class="form-control" id="modalBiblioDateReservation"
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

            <!-- Modal pour le prêt -->
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
                    </div>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            <script>
                // Script pour gérer l'affichage des modals et le transfert des données
                document.addEventListener('DOMContentLoaded', function () {

                    // Modal pour adhérent
                    const reservationModal = document.getElementById('reservationModal');
                    if (reservationModal) {
                        reservationModal.addEventListener('show.bs.modal', function (event) {
                            const button = event.relatedTarget;
                            const exemplaireId = button.getAttribute('data-exemplaire-id');
                            const modal = this;
                            modal.querySelector('#modalExemplaireId').value = exemplaireId;
                        });
                    }

                    // Modal pour bibliothécaire
                    const biblioReservationModal = document.getElementById('bibliothecaireReservationModal');
                    if (biblioReservationModal) {
                        biblioReservationModal.addEventListener('show.bs.modal', function (event) {
                            const button = event.relatedTarget;
                            const exemplaireId = button.getAttribute('data-exemplaire-id');
                            const modal = this;
                            modal.querySelector('#modalBiblioExemplaireId').value = exemplaireId;
                        });
                    }
                });

                // Modal pour prêt
                const pretModal = document.getElementById('pretModal');
                if (pretModal) {
                    pretModal.addEventListener('show.bs.modal', function (event) {
                        const button = event.relatedTarget;
                        const exemplaireId = button.getAttribute('data-exemplaire-id');
                        const modal = this;
                        modal.querySelector('#modalPretExemplaireId').value = exemplaireId;
                    });
                }

                // Modal pour retour
                const retourModal = document.getElementById('retourModal');
                if (retourModal) {
                    retourModal.addEventListener('show.bs.modal', function (event) {
                        const button = event.relatedTarget;
                        const exemplaireId = button.getAttribute('data-exemplaire-id');
                        const modal = this;
                        modal.querySelector('#modalRetourExemplaireId').value = exemplaireId;
                    });
                }
            </script>
        </body>

        </html>