<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Admin Panel</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

    <c:import url="admin-nav.jsp" />

    <div class="container mt-4">
        <div class="jumbotron">
            <h1 class="display-4">
                <c:if test="${sessionScope.korisnik.uloga.naziv == 'admin'}">
                    Dobrodošli u administratorski panel!
                </c:if>
                <c:if test="${sessionScope.korisnik.uloga.naziv == 'menadzer'}">
                    Dobrodošli u menadžerski panel!
                </c:if>
            </h1>
            <p class="lead">U navigaciji iznad se nalazi meni za upravljanje sajtom.</p>
            <hr class="my-4">
            <p>
                <c:if test="${sessionScope.korisnik.uloga.naziv == 'admin'}">
                    Ovde možete upravljati proizvodima, korisnicima i pregledati narudžbine.
                </c:if>
                 <c:if test="${sessionScope.korisnik.uloga.naziv == 'menadzer'}">
                    Ovde možete upravljati proizvodima i pregledati poslovne izveštaje.
                </c:if>
            </p>
        </div>
    </div>

</body>
</html>
