<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Poslovni Izveštaji</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Dodajemo Chart.js biblioteku -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Navigacija -->
            <c:import url="/admin/admin-nav.jsp" />

            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">Poslovni Izveštaji</h1>
                </div>

                <!-- Kartice sa ključnim metrikama -->
                <div class="row">
                    <div class="col-md-6">
                        <div class="card text-white bg-primary mb-3">
                            <div class="card-header">Ukupan Promet</div>
                            <div class="card-body">
                                <h5 class="card-title">
                                    <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ukupanPromet}" /> RSD
                                </h5>
                                <p class="card-text">Ukupan prihod od svih narudžbina.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card text-white bg-success mb-3">
                            <div class="card-header">Ukupno Narudžbina</div>
                            <div class="card-body">
                                <h5 class="card-title">${sveNarudzbine.size()}</h5>
                                <p class="card-text">Ukupan broj primljenih narudžbina.</p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Grafikon i najprodavaniji proizvodi -->
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header">
                                Najprodavaniji Proizvodi
                            </div>
                            <div class="card-body">
                                <c:if test="${not empty najprodavanijiProizvodi}">
                                    <ul class="list-group">
                                        <c:forEach var="proizvod" items="${najprodavanijiProizvodi}">
                                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                                ${proizvod.naziv}
                                                <span class="badge badge-info badge-pill">Cena: <fmt:formatNumber value="${proizvod.cena}" type="currency" currencySymbol="RSD"/></span>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                                <c:if test="${empty najprodavanijiProizvodi}">
                                    <p>Nema podataka o prodaji.</p>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Tabela sa svim narudžbinama -->
                <h2 class="mt-4">Sve Narudžbine</h2>
                <div class="table-responsive">
                    <table class="table table-striped table-sm">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Naručilac</th>
                                <th>Datum</th>
                                <th>Adresa</th>
                                <th>Status</th>
                                <th class="text-right">Ukupna Cena</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="narudzbina" items="${sveNarudzbine}">
                                <tr>
                                    <td>${narudzbina.id}</td>
                                    <td>${narudzbina.korisnik.ime} ${narudzbina.korisnik.prezime}</td>
                                    <td><fmt:formatDate value="${narudzbina.datum}" pattern="dd.MM.yyyy HH:mm" /></td>
                                    <td>${narudzbina.adresaDostave}</td>
                                    <td>${narudzbina.status}</td>
                                    <td class="text-right"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${narudzbina.ukupnaCena}" /> RSD</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    </div>
</body>
</html>
