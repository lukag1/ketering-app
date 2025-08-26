<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Istorija Narudžbina</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <!-- Navigacija -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">Ketering Servis</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/meni">Meni</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/korpa">Korpa</a>
                    </li>
                     <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/istorija-narudzbina">Istorija narudžbina <span class="sr-only">(current)</span></a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                     <li class="nav-item">
                        <a class="nav-link" href="logout">Odjavi se</a>
                    </li>
                </ul>
            </div>
        </nav>

        <h1 class="mt-4">Moje Narudžbine</h1>

        <c:if test="${not empty narudzbine}">
            <table class="table table-striped mt-4">
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
                            <td>${narudzbina.id}</td>
                            <td><fmt:formatDate value="${narudzbina.datum}" pattern="dd.MM.yyyy HH:mm:ss" /></td>
                            <td>${narudzbina.adresaDostave}</td>
                            <td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${narudzbina.ukupnaCena}" /> RSD</td>
                            <td>
                                <c:choose>
                                    <c:when test="${narudzbina.status == 'PENDING'}">
                                        <span class="badge badge-warning">Obrada u toku</span>
                                    </c:when>
                                    <c:when test="${narudzbina.status == 'CONFIRMED'}">
                                        <span class="badge badge-primary">Potvrđena</span>
                                    </c:when>
                                    <c:when test="${narudzbina.status == 'SHIPPED'}">
                                        <span class="badge badge-info">Poslata</span>
                                    </c:when>
                                    <c:when test="${narudzbina.status == 'DELIVERED'}">
                                        <span class="badge badge-success">Dostavljena</span>
                                    </c:when>
                                    <c:when test="${narudzbina.status == 'CANCELLED'}">
                                        <span class="badge badge-danger">Otkazana</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-secondary">${narudzbina.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty narudzbine}">
            <p class="mt-4">Nemate prethodnih narudžbina.</p>
        </c:if>
    </div>
</body>
</html>
