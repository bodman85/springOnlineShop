<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../../fragments/header.jsp" />

<body>

<div class="container">

    <h1 align="center">We offer</h1>

    <spring:url value="/products/add/" var="addProduct" />

    <button class="btn btn-success"
            onclick="location.href='${addProduct}'">Add product</button>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>#ID</th>
            <th>Name</th>
            <th>Category</th>
            <th>Price, $</th>
            <th>Action</th>
        </tr>
        </thead>

        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.category}</td>
                <td>${product.price}</td>
                <td>
                    <spring:url value="/products/${product.id}" var="productUrl" />
                    <spring:url value="/products/${product.id}/delete" var="deleteUrl" />
                    <spring:url value="/products/${product.id}/update" var="updateUrl" />

                    <button class="btn btn-info"
                            onclick="location.href='${productUrl}'">Details</button>
                    <button class="btn btn-primary"
                            onclick="location.href='${updateUrl}'">Update</button>
                    <button class="btn btn-danger"
                            onclick="this.disabled=true;location.href='${deleteUrl}'">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>

</div>

<jsp:include page="../../fragments/footer.jsp" />

</body>
</html>