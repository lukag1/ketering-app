<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Upravljanje Proizvodima</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">Admin Panel</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/admin.jsp">Početna</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/proizvodi">Upravljanje Proizvodima</a>
                </li>
                 <li class="nav-item">
                    <a class="nav-link" href="#">Upravljanje Korisnicima</a>
                </li>
            </ul>
            <ul class="navbar-nav ml-auto">
                 <c:if test="${sessionScope.korisnik != null}">
                    <li class="nav-item">
                        <a class="nav-link">Prijavljeni ste kao: <c:out value="${sessionScope.korisnik.ime}"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">Odjavi se</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">Lista Proizvoda</h3>
                    </div>
                    <div class="card-body">
                        <div class="mb-3">
                            <a href="${pageContext.request.contextPath}/admin/proizvodi?action=new" class="btn btn-success">Dodaj Novi Proizvod</a>
                        </div>
                        <table class="table table-bordered table-striped">
                            <thead class="thead-light">
                                <tr>
                                    <th>ID</th>
                                    <th>Naziv</th>
                                    <th>Tip</th>
                                    <th>Cena</th>
                                    <th>Akcije</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="proizvod" items="${listProizvod}">
                                    <tr>
                                        <td><c:out value="${proizvod.id}" /></td>
                                        <td><c:out value="${proizvod.naziv}" /></td>
                                        <td><c:out value="${proizvod.tip}" /></td>
                                        <td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${proizvod.cena}"/> RSD</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/proizvodi?action=edit&id=<c:out value='${proizvod.id}'/>" class="btn btn-primary btn-sm">Izmeni</a>
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <a href="${pageContext.request.contextPath}/admin/proizvodi?action=delete&id=<c:out value='${proizvod.id}'/>" class="btn btn-danger btn-sm" onclick="return confirm('Da li ste sigurni da želite da obrišete ovaj proizvod?')">Obriši</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
