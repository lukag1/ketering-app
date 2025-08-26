<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Potvrda Narudžbine</title>
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
            </ul>
        </div>
    </nav>

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card text-center">
                    <div class="card-header">
                        Potvrda
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Uspešna Narudžbina!</h5>
                        <p class="card-text">
                            <c:if test="${not empty sessionScope.poruka}">
                                <div class="alert alert-success" role="alert">
                                    <c:out value="${sessionScope.poruka}"/>
                                </div>
                                <%-- Uklanjamo poruku iz sesije da se ne bi prikazivala ponovo --%>
                                <c:remove var="poruka" scope="session"/>
                                <c:remove var="poslednjaNarudzbinaId" scope="session"/>
                                <c:remove var="poslednjaAdresaDostave" scope="session"/>
                            </c:if>

                            <a href="${pageContext.request.contextPath}/meni" class="btn btn-primary mt-3">Vrati se na Meni</a>
                            <a href="${pageContext.request.contextPath}/istorija-narudzbina" class="btn btn-secondary mt-3">Pregledaj moje narudžbine</a>
                        </p>
                    </div>
                    <div class="card-footer text-muted">
                        Vaš Ketering Servis
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
