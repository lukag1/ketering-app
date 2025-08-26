<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- PROMENA: navbar-dark bg-dark -> navbar-light bg-light shadow-sm -->
<nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom shadow-sm">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/admin.jsp">
        <strong>Ketering Panel</strong>
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/admin.jsp">Početna</a>
            </li>
            
            <!-- Linkovi za Menadžera i Admina -->
            <c:if test="${sessionScope.korisnik.uloga.naziv == 'menadzer' || sessionScope.korisnik.uloga.naziv == 'menadžer' || sessionScope.korisnik.uloga.naziv == 'admin'}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/proizvodi">Proizvodi</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/narudzbine">Narudžbine</a>
                </li>
                 <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/izvestaji">Izveštaji</a>
                </li>
            </c:if>

            <!-- Linkovi samo za Admina -->
            <c:if test="${sessionScope.korisnik.uloga.naziv == 'admin'}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/korisnici">Korisnici</a>
                </li>
            </c:if>
        </ul>
        <ul class="navbar-nav ml-auto">
            <c:if test="${sessionScope.korisnik != null}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <c:out value="${sessionScope.korisnik.ime}"/> (<c:out value="${sessionScope.korisnik.uloga.naziv}"/>)
                    </a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Odjavi se</a>
                    </div>
                </li>
            </c:if>
        </ul>
    </div>
</nav>