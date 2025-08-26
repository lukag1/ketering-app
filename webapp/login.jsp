<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Prijava</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h4>Prijava</h4>
                    </div>
                    <div class="card-body">
                        
                        <!-- DODATO: Blok za prikaz poruke o grešci -->
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger" role="alert">
                                <c:out value="${errorMessage}" />
                            </div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/login" method="post">
                            <div class="form-group">
                                <label for="email">Email adresa</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>
                            <div class="form-group">
                                <label for="lozinka">Lozinka</label>
                                <input type="password" class="form-control" id="lozinka" name="lozinka" required>
                            </div>
                            <button type="submit" class="btn btn-primary btn-block">Prijavi se</button>
                        </form>
                        <div class="mt-3 text-center">
                            <p>Nemate nalog? <a href="registracija.jsp">Registrujte se ovde</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
