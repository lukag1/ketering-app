<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Moj Profil</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    
    <!-- ZAMENA: Ubačena kompletna navigacija umesto include direktive -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/meni">Ketering Servis</a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/meni">Meni</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/korpa">Korpa</a>
                </li>
            </ul>
            <ul class="navbar-nav ml-auto">
                <c:if test="${sessionScope.korisnik != null}">
                    <li class="nav-item">
                        <a class="nav-link">Dobrodošli, <c:out value="${sessionScope.korisnik.ime}"/></a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/profil">Moj Profil</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">Odjavi se</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.korisnik == null}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp">Prijavi se</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </nav>

    <div class="container mt-4">
        <h2>Moj Profil</h2>
        <hr>
        <h4>Moje Narudžbine</h4>

        <c:if test="${empty narudzbine}">
            <div class="alert alert-info" role="alert">
                Još uvek nemate nijednu narudžbinu.
            </div>
        </c:if>

        <c:if test="${not empty narudzbine}">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>ID Narudžbine</th>
                        <th>Datum</th>
                        <th>Adresa Dostave</th>
                        <th>Ukupna Cena</th>
                        <th>Status</th>
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
                            <td>
                                <fmt:formatNumber value="${narudzbina.ukupnaCena}" type="currency" currencySymbol="RSD " />
                            </td>
                            <td><span class="badge badge-info"><c:out value="${narudzbina.status}" /></span></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>

</body>
</html>