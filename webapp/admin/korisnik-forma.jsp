<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Forma za Korisnika</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="admin-nav.jsp" />

    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <c:if test="${korisnikZaFormu != null}">
                    <h4>Izmena Korisnika</h4>
                </c:if>
                <c:if test="${korisnikZaFormu == null}">
                    <h4>Dodavanje Novog Korisnika</h4>
                </c:if>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/admin/korisnici?action=${korisnikZaFormu != null ? 'update' : 'insert'}" method="post">
                    
                    <c:if test="${korisnikZaFormu != null}">
                        <input type="hidden" name="id" value="<c:out value='${korisnikZaFormu.id}' />" />
                    </c:if>

                    <div class="form-group">
                        <label for="ime">Ime:</label>
                        <input type="text" class="form-control" id="ime" name="ime" value="<c:out value='${korisnikZaFormu.ime}' />" required>
                    </div>

                    <div class="form-group">
                        <label for="prezime">Prezime:</label>
                        <input type="text" class="form-control" id="prezime" name="prezime" value="<c:out value='${korisnikZaFormu.prezime}' />" required>
                    </div>

                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" class="form-control" id="email" name="email" value="<c:out value='${korisnikZaFormu.email}' />" required>
                    </div>

                    <div class="form-group">
                        <label for="lozinka">Lozinka:</label>
                        <input type="password" class="form-control" id="lozinka" name="lozinka" placeholder="${korisnikZaFormu != null ? 'Ostavite prazno ako ne menjate' : ''}">
                    </div>

                    <div class="form-group">
                        <label for="ulogaId">Uloga:</label>
                        <select class="form-control" id="ulogaId" name="ulogaId">
                            <c:forEach var="uloga" items="${listaUloga}">
                                <option value="${uloga.id}" ${korisnikZaFormu.uloga.id == uloga.id ? 'selected' : ''}>
                                    <c:out value="${uloga.naziv}" />
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-success">Sačuvaj</button>
                    <a href="${pageContext.request.contextPath}/admin/korisnici" class="btn btn-secondary">Odustani</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>