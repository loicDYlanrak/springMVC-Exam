<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../layout/template.jsp">
    <jsp:param name="title" value="Retour d'exemplaire"/>
</jsp:include>

<div class="container">
    <h1 class="my-4">Retour d'exemplaire</h1>
    
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>
    
    <div class="card mb-4">
        <div class="card-header">
            <h4>Exemplaire #${exemplaire.id_exemplaire}</h4>
        </div>
        <div class="card-body">
            <div class="mb-3">
                <p><strong>Adhérent :</strong> ${pret.adherent.nom}</p>
                <p><strong>Date de prêt :</strong> ${pret.date_pret}</p>
                <p><strong>Date de retour prévue :</strong> ${pret.date_retour_prevue}</p>
            </div>
            
            <form method="post" action="${pageContext.request.contextPath}/livre/retourner">
                <input type="hidden" name="idPret" value="${pret.id_pret}">
                
                <button type="submit" class="btn btn-primary">Confirmer le retour</button>
                <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Annuler</a>
            </form>
        </div>
    </div>
</div>

<%@ include file="../layout/footer.jsp" %>