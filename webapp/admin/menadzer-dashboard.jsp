<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Menadžer Panel</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

    <%-- Uključujemo istu navigaciju koju koristi i admin.
         Ona će automatski sakriti link za korisnike jer rola nije "admin". --%>
    <jsp:include page="admin-nav.jsp" />

    <div class="container mt-4">
        <div class="jumbotron">
            <h1 class="display-4">Dobrodošli u menadžerski panel!</h1>
            <p class="lead">U navigaciji iznad se nalazi meni za upravljanje sajtom.</p>
            <hr class="my-4">
            <p>Ovde možete upravljati proizvodima i pregledati narudžbine.</p>
        </div>
    </div>

</body>
</html>