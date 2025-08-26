<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Upravljanje Narudžbinama</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="admin-nav.jsp" />
    <div class="container mt-4">
        <h3>Sve Narudžbine</h3>
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Kupac</th>
                    <th>Datum</th>
                    <th>Adresa</th>
                    <th>Ukupno</th>
                    <th>Status</th>
                    <th>Akcije</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="narudzbina" items="${listaNarudzbina}">
                    <tr>
                        <td>${narudzbina.id}</td>
                        <td>${narudzbina.korisnik.ime} ${narudzbina.korisnik.prezime}</td>
                        <td><fmt:formatDate value="${narudzbina.datum}" pattern="dd.MM.yyyy HH:mm"/></td>
                        <td>${narudzbina.adresaDostave}</td>
                        <td><fmt:formatNumber value="${narudzbina.ukupnaCena}" type="currency" currencySymbol="RSD "/></td>
                        <td>
                            <form action="${pageContext.request.contextPath}/admin/narudzbine?action=updateStatus&id=${narudzbina.id}" method="post" class="form-inline">
                                <select name="status" class="form-control form-control-sm mr-2">
                                    <option value="U obradi" ${narudzbina.status == 'U obradi' ? 'selected' : ''}>U obradi</option>
                                    <option value="Poslata" ${narudzbina.status == 'Poslata' ? 'selected' : ''}>Poslata</option>
                                    <option value="Isporučena" ${narudzbina.status == 'Isporučena' ? 'selected' : ''}>Isporučena</option>
                                    <option value="Otkazana" ${narudzbina.status == 'Otkazana' ? 'selected' : ''}>Otkazana</option>
                                </select>
                                <button type="submit" class="btn btn-primary btn-sm">Sačuvaj</button>
                            </form>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/narudzbine?action=delete&id=${narudzbina.id}" class="btn btn-danger btn-sm" onclick="return confirm('Da li ste sigurni?')">Obriši</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>