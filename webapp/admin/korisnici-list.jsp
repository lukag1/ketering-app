<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Upravljanje Korisnicima</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <%-- Pretpostavka je da imate admin-nav.jsp ili sličan fajl za navigaciju --%>
    <%-- Ako nemate, možete ubaciti HTML za navigaciju direktno ovde --%>
    <jsp:include page="admin-nav.jsp" />

    <div class="container mt-4">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        <h4>Lista Korisnika</h4>
                    </div>
                    <div class="card-body">
                        <a href="${pageContext.request.contextPath}/admin/korisnici?action=new" class="btn btn-success mb-3">Dodaj Novog Korisnika</a>
                        <table class="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Ime</th>
                                    <th>Prezime</th>
                                    <th>Email</th>
                                    <th>Uloga</th>
                                    <th>Akcije</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="korisnik" items="${listaKorisnika}">
                                    <tr>
                                        <td><c:out value="${korisnik.id}" /></td>
                                        <td><c:out value="${korisnik.ime}" /></td>
                                        <td><c:out value="${korisnik.prezime}" /></td>
                                        <td><c:out value="${korisnik.email}" /></td>
                                        <td><c:out value="${korisnik.uloga.naziv}" /></td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/korisnici?action=edit&id=${korisnik.id}" class="btn btn-primary btn-sm">Izmeni</a>
                                            &nbsp;&nbsp;&nbsp;
                                            <a href="${pageContext.request.contextPath}/admin/korisnici?action=delete&id=${korisnik.id}" class="btn btn-danger btn-sm" onclick="return confirm('Da li ste sigurni da želite da obrišete ovog korisnika?')">Obriši</a>
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