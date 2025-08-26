<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Istorija Narudžbina</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    
    <!-- Uključujemo centralnu navigaciju -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">Ketering Servis</a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="meni">Meni</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="korpa">Korpa <span class="sr-only">(current)</span></a>
                </li>
                 <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/istorija-narudzbina">Istorija narudžbina</a>
                </li>
            </ul>
            <ul class="navbar-nav ml-auto">
                <c:if test="${sessionScope.korisnik != null}">
                    <li class="nav-item">
                        <a class="nav-link">Dobrodošli, <c:out value="${sessionScope.korisnik.ime}"/></a>
                    </li>
                    <!-- DODAJTE OVAJ BLOK -->
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/profil">Moj Profil</a>
                    </li>
                    <!-- KRAJ BLOKA ZA DODAVANJE -->
                    <li class="nav-item">
                        <a class="nav-link" href="logout">Odjavi se</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.korisnik == null}">
                    <li class="nav-item">
                        <a class="nav-link" href="login.jsp">Prijavi se</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </nav>
    <div class="container mt-4">
        <h2>Istorija Narudžbina</h2>
        <hr>

        <c:if test="${empty narudzbine}">
            <div class="alert alert-info" role="alert">
                Još uvek nemate nijednu narudžbinu. <a href="${pageContext.request.contextPath}/meni" class="alert-link">Pogledajte naš meni!</a>
            </div>
        </c:if>

        <c:if test="${not empty narudzbine}">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead class="thead-light">
                        <tr>
                            <th>ID</th>
                            <th>Datum</th>
                            <th>Adresa Dostave</th>
                            <th class="text-right">Ukupna Cena</th>
                            <th class="text-center">Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="narudzbina" items="${narudzbine}">
                            <tr>
                                <td>#<c:out value="${narudzbina.id}" /></td>
                                <td>
                                    <fmt:formatDate value="${narudzbina.datum}" pattern="dd.MM.yyyy HH:mm" />
                                </td>
                                <td><c:out value="${narudzbina.adresaDostave}" /></td>
                                <td class="text-right">
                                    <fmt:formatNumber value="${narudzbina.ukupnaCena}" type="currency" currencySymbol="RSD " />
                                </td>
                                <td class="text-center">
                                    <c:set var="statusClass" value="badge-secondary" />
                                    <c:if test="${narudzbina.status == 'Dostavljena'}"><c:set var="statusClass" value="badge-success" /></c:if>
                                    <c:if test="${narudzbina.status == 'U pripremi'}"><c:set var="statusClass" value="badge-warning" /></c:if>
                                    <c:if test="${narudzbina.status == 'Otkazana'}"><c:set var="statusClass" value="badge-danger" /></c:if>
                                    <span class="badge ${statusClass}"><c:out value="${narudzbina.status}" /></span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>

    <!-- Skripte za Bootstrap -->
    <script src="https://code.jquery.com/jquery-3.sim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
