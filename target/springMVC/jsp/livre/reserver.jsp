<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../layout/template.jsp">
    <jsp:param name="title" value="Réservation de livre"/>
</jsp:include>

<div class="container">
    <h1 class="my-4">Réservation de livre</h1>
    
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    
    <div class="card mb-4">
        <div class="card-header">
            <h4>${livre.titre} - ${livre.auteur}</h4>
        </div>
        <div class="card-body">
            <form method="post" action="${pageContext.request.contextPath}/livre/reserver">
                <c:if test="${empty adherent}">
                    <div class="mb-3">
                        <label for="idAdherent" class="form-label">Numéro d'adhérent</label>
                        <input type="number" class="form-control" id="idAdherent" name="idAdherent" required>
                    </div>
                </c:if>
                <c:if test="${not empty adherent}">
                    <input type="hidden" name="idAdherent" value="${adherent.id_adherent}">
                </c:if>
                
                <div class="mb-3">
                    <label for="idExemplaire" class="form-label">Exemplaire</label>
                    <select class="form-select" id="idExemplaire" name="idExemplaire" required>
                        <option value="">Sélectionnez un exemplaire</option>
                        <c:forEach items="${exemplaires}" var="exemplaire">
                            <option value="${exemplaire.id_exemplaire}">Exemplaire #${exemplaire.id_exemplaire}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <button type="submit" class="btn btn-primary">Confirmer la réservation</button>
                <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Annuler</a>
            </form>
        </div>
    </div>
</div>

<%@ include file="../layout/footer.jsp" %>