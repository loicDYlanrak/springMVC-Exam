<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Paiement Abonnement</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h3 class="text-center">Paiement de l'abonnement</h3>
                    </div>
                    <div class="card-body">
                        <div class="mb-4">
                            <h5>Informations adhérent:</h5>
                            <p><strong>Nom:</strong> ${adherent.nom}</p>
                            <p><strong>Email:</strong> ${adherent.email}</p>
                            <p><strong>Type d'adhérent:</strong> ${adherent.typeAdherent.libelle}</p>
                        </div>
                        
                        <div class="mb-4">
                            <h5>Détails de l'abonnement:</h5>
                            <p>L'abonnement est valable pour une durée d'un an à partir de la date de paiement.</p>
                            <p class="fw-bold">Prix: 50€</p>
                        </div>
                        
                        <form action="/paiement" method="post">
                            <input type="hidden" name="adherentId" value="${adherent.id_adherent}">
                            
                            <div class="mb-3">
                                <label for="cardNumber" class="form-label">Numéro de carte</label>
                                <input type="text" class="form-control" id="cardNumber" placeholder="1234 5678 9012 3456" required>
                            </div>
                            
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="expiryDate" class="form-label">Date d'expiration</label>
                                    <input type="text" class="form-control" id="expiryDate" placeholder="MM/AA" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="cvv" class="form-label">CVV</label>
                                    <input type="text" class="form-control" id="cvv" placeholder="123" required>
                                </div>
                            </div>
                            
                            <div class="d-grid">
                                <button type="submit" class="btn btn-success">Payer maintenant</button>
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