<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../layout/template.jsp">
    <jsp:param name="title" value="Prolongation de prêt"/>
</jsp:include>

<div class="container">
    <h1 class="my-4">Prolongation de prêt</h1>
    
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    
    <div class="card mb-4">
        <div class="card-header">
            <h4>Prolonger le prêt</h4>
        </div>
        <div class="card-body">
            <div class="mb-3">
                <p><strong>Livre :</strong> ${pret.exemplaire.livre.titre}</p>
                <p><strong>Exemplaire :</strong> #${pret.exemplaire.id_exemplaire}</p>
                <p><strong>Adhérent :</strong> ${pret.adherent.nom}</p>
                <p><strong>Date de prêt :</strong> ${pret.date_pret}</p>
                <p><strong>Date de retour prévue actuelle :</strong> ${pret.date_retour_prevue}</p>
                <p><strong>Nouvelle date de retour prévue :</strong> ${pretService.calculateNewDateRetour(pret)}</p>
            </div>
            
            <form method="post" action="${pageContext.request.contextPath}/livre/prolonger">
                <input type="hidden" name="idPret" value="${pret.id_pret}">
                
                <button type="submit" class="btn btn-primary">Confirmer la prolongation</button>
                <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Annuler</a>
            </form>
        </div>
    </div>
</div>

<%@ include file="../layout/footer.jsp" %>