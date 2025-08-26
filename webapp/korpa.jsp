<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.ketering.model.Proizvod" %>
<%@ page import="com.example.ketering.model.Korisnik" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Vaša Korpa</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

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
        <h1 class="mb-4">Sadržaj vaše korpe</h1>

        <c:if test="${empty sessionScope.korpa or empty sessionScope.korpa.values()}">
            <div class="alert alert-info" role="alert">
                Vaša korpa je prazna. <a href="meni" class="alert-link">Vratite se na meni</a> da dodate proizvode.
            </div>
        </c:if>

        <c:if test="${not empty sessionScope.korpa.values()}">
            <table class="table table-hover">
                <thead class="thead-light">
                    <tr>
                        <th>Proizvod</th>
                        <th>Cena po komadu</th>
                        <th>Količina</th>
                        <th>Ukupno</th>
                        <th>Akcija</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="ukupnaSuma" value="0" />
                    <c:forEach var="stavka" items="${sessionScope.korpa.values()}">
                        <tr>
                            <td><c:out value="${stavka.proizvod.naziv}"/></td>
                            <td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${stavka.proizvod.cena}"/> RSD</td>
                            <td><c:out value="${stavka.kolicina}"/></td>
                            <td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${stavka.ukupnaCena}"/> RSD</td>
                            <td>
                                <form action="korpa" method="post" style="display: inline;">
                                    <input type="hidden" name="akcija" value="ukloni">
                                    <input type="hidden" name="proizvodId" value="${stavka.proizvod.id}">
                                    <button type="submit" class="btn btn-danger btn-sm">Ukloni</button>
                                </form>
                            </td>
                        </tr>
                        <c:set var="ukupnaSuma" value="${ukupnaSuma + stavka.ukupnaCena}" />
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr class="table-info">
                        <td colspan="3" class="text-right"><strong>Ukupno za plaćanje:</strong></td>
                        <td colspan="2"><strong><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ukupnaSuma}"/> RSD</strong></td>
                    </tr>
                </tfoot>
            </table>
            <div class="text-right">
                <form action="narudzbina" method="post">
                    <button type="submit" class="btn btn-success">Nastavi na plaćanje</button>
                </form>
            </div>
        </c:if>

        <c:if test="${not empty korpa}">
            <div class="card mt-4">
                <div class="card-body">
                    <h5 class="card-title">Završite narudžbinu</h5>
                    
                    <!-- Forma koja šalje podatke na NarudzbinaServlet -->
                    <form action="${pageContext.request.contextPath}/narudzbina" method="post">
                        <div class="form-group">
                            <label for="adresaDostave">Adresa za dostavu:</label>
                            <input type="text" class="form-control" id="adresaDostave" name="adresaDostave" placeholder="Unesite vašu ulicu i broj" required>
                        </div>
                        
                        <c:if test="${not empty greska}">
                            <div class="alert alert-danger">${greska}</div>
                        </c:if>

                        <button type="submit" class="btn btn-success btn-lg">Potvrdi i Naruči</button>
                    </form>
                </div>
            </div>
        </c:if>
    </div>

</body>
</html>
