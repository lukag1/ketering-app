<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Registracija</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h4>Registracija novog korisnika</h4>
                    </div>
                    <div class="card-body">
                        <form action="registracija" method="post">
                            <c:if test="${not empty greska}">
                                <div class="alert alert-danger">${greska}</div>
                            </c:if>
                            <div class="form-group">
                                <label for="ime">Ime</label>
                                <input type="text" class="form-control" id="ime" name="ime" required>
                            </div>
                            <div class="form-group">
                                <label for="prezime">Prezime</label>
                                <input type="text" class="form-control" id="prezime" name="prezime" required>
                            </div>
                            <div class="form-group">
                                <label for="email">Email adresa</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>
                            <div class="form-group">
                                <label for="lozinka">Lozinka</label>
                                <input type="password" class="form-control" id="lozinka" name="lozinka" required>
                            </div>
                            <div class="form-group">
                                <label for="potvrdaLozinke">Potvrdi lozinku</label>
                                <input type="password" class="form-control" id="potvrdaLozinke" name="potvrdaLozinke" required>
                            </div>
                            <button type="submit" class="btn btn-primary btn-block">Registruj se</button>
                        </form>
                    </div>
                    <div class="card-footer text-center">
                        <a href="login.jsp">Već imate nalog? Prijavite se</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
