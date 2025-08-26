<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Upravljanje Proizvodima</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <%-- Uključujemo navigaciju sa glavne admin stranice --%>
    <jsp:include page="admin.jsp" />

    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h4>Lista Proizvoda</h4>
            </div>
            <div class="card-body">
                <a href="${pageContext.request.contextPath}/admin/proizvodi?action=new" class="btn btn-success mb-3">Dodaj Novi Proizvod</a>
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Naziv</th>
                            <th>Cena</th>
                            <th>Tip</th>
                            <th>Akcije</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="proizvod" items="${listaProizvoda}">
                            <tr>
                                <td>${proizvod.id}</td>
                                <td>${proizvod.naziv}</td>
                                <td><fmt:formatNumber value="${proizvod.cena}" type="currency" currencySymbol="RSD "/></td>
                                <td>${proizvod.tip}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/proizvodi?action=edit&id=${proizvod.id}" class="btn btn-primary btn-sm">Izmeni</a>
                                    <a href="${pageContext.request.contextPath}/admin/proizvodi?action=delete&id=${proizvod.id}" class="btn btn-danger btn-sm" onclick="return confirm('Da li ste sigurni da želite da obrišete ovaj proizvod?')">Obriši</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>