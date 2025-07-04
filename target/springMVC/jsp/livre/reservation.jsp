<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Réservation</title>
</head>
<body>
    <h2>Réservation d'un exemplaire</h2>
    
    <c:if test="${not empty message}">
        <div class="alert alert-info">${message}</div>
    </c:if>
    
    <form action="/livre/reserver" method="post">
        <input type="hidden" name="id_exemplaire" value="${idExemplaire}">
        
        <c:if test="${not empty sessionScope.bibliothecaire}">
            <label>ID Adhérent:</label>
            <input type="number" name="id_adherent" required>
        </c:if>
        
        <button type="submit" class="btn btn-primary">Confirmer la réservation</button>
    </form>
</body>
</html>