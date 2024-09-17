<%@ include file="/init.jsp" %>

<form method="get">
    <input type="text" name="name" placeholder="Nombre">
    <input type="text" name="surname" placeholder="Apellidos">
    <input type="text" name="email" placeholder="Correo electrónico">
    <input type="submit" value="Buscar">
</form>

<c:forEach var="user" items="${users}">
    <div>
        <strong>${user.name}</strong> ${user.surname1} ${user.surname2} - ${user.email}
    </div>
</c:forEach>

<div>
    <c:if test="${currentPage > 1}">
        <a href="?page=${currentPage - 1}">Anterior</a>
    </c:if>
    <c:if test="${currentPage < totalPages}">
        <a href="?page=${currentPage + 1}">Siguiente</a>
    </c:if>
</div>