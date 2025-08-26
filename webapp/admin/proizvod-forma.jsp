<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Forma za Proizvod</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="admin.jsp" />
    <div class="container mt-4">
        <h3>
            <c:if test="${proizvod != null}">Izmena Proizvoda</c:if>
            <c:if test="${proizvod == null}">Dodavanje Novog Proizvoda</c:if>
        </h3>
        <form action="${pageContext.request.contextPath}/admin/proizvodi?action=${proizvod != null ? 'update' : 'insert'}" method="post">
            <c:if test="${proizvod != null}"><input type="hidden" name="id" value="${proizvod.id}" /></c:if>
            
            <div class="form-group">
                <label>Naziv</label>
                <input type="text" name="naziv" class="form-control" value="<c:out value='${proizvod.naziv}'/>" required>
            </div>
            <div class="form-group">
                <label>Opis</label>
                <textarea name="opis" class="form-control" required><c:out value='${proizvod.opis}'/></textarea>
            </div>
            <div class="form-group">
                <label>Cena (RSD)</label>
                <input type="number" step="0.01" name="cena" class="form-control" value="<c:out value='${proizvod.cena}'/>" required>
            </div>
            <div class="form-group">
                <label>Tip</label>
                <select name="tip" class="form-control">
                    <option value="slatko" ${proizvod.tip == 'slatko' ? 'selected' : ''}>Slatko</option>
                    <option value="slano" ${proizvod.tip == 'slano' ? 'selected' : ''}>Slano</option>
                </select>
            </div>
            

            <button type="submit" class="btn btn-primary">Sačuvaj</button>
        </form>
    </div>
</body>
</html>
