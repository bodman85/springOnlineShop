<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../../fragments/header.jsp" />

<div class="container">

  <h1>Product Details</h1>
  <br />

  <div class="row">
    <label class="col-sm-2">ID</label>
    <div class="col-sm-10">${product.id}</div>
  </div>

  <div class="row">
    <label class="col-sm-2">Name</label>
    <div class="col-sm-10">${product.name}</div>
  </div>

  <div class="row">
    <label class="col-sm-2">Category</label>
    <div class="col-sm-10">${product.category}</div>
  </div>

  <div class="row">
    <label class="col-sm-2">Description</label>
    <div class="col-sm-10">${product.description}</div>
  </div>

  <div class="row">
    <label class="col-sm-2">Price, $</label>
    <div class="col-sm-10">${product.price}</div>
  </div>

</div>

<jsp:include page="../../fragments/footer.jsp" />

</body>
</html>