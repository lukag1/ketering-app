<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.ketering.model.Proizvod" %>
<%@ page import="com.example.ketering.model.Korisnik" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Meni</title>
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
                <li class="nav-item">
                    <a class="nav-link" href="korpa">Korpa</a>
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
        <h1 class="mb-4">Naš Meni</h1>

        <div class="row">
            <c:forEach var="proizvod" items="${proizvodi}">
                <div class="col-md-4 mb-4">
                    <div class="card h-100">
                        <!-- 
                            UKLONITI ILI KOMENTARISATI OVAJ IMG TAG.
                            On pokušava da koristi 'proizvod.slikaUrl' koje više ne postoji.
                        -->
                        <%-- <img src="${proizvod.slikaUrl}" class="card-img-top" alt="${proizvod.naziv}"> --%>
                        
                        <div class="card-body">
                            <h5 class="card-title">${proizvod.naziv}</h5>
                            <p class="card-text">${proizvod.opis}</p>
                        </div>
                        <div class="card-footer">
                            <p class="card-text font-weight-bold">Cena: <c:out value="${proizvod.cena}"/> RSD</p>
                            <form action="korpa" method="post">
                                <input type="hidden" name="akcija" value="dodaj">
                                <input type="hidden" name="proizvodId" value="${proizvod.id}">
                                <button type="submit" class="btn btn-primary">Dodaj u korpu</button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

</body>
</html>
