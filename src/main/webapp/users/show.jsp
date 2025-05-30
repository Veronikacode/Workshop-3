<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/header.jsp" %>

<!-- Begin Page Content -->
<div class="container-fluid">

  <!-- Page Heading -->
  <div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h1 class="h3 mb-0 text-gray-800">UsersCRUD</h1>
    <a href='<c:url value="/user/list" />' class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
      <i class="fas fa-download fa-sm text-white-50"></i> Lista użytkownika
    </a>
  </div>

  <!-- Content Row -->
  <div class="row">
    <div class="col-12">
      <!-- DataTales -->
      <div class="card shadow mb-4 w-100">
        <div class="card-header py-3">
          <h6 class="m-0 font-weight-bold text-primary">Szczegóły użytkownika</h6>
        </div>
        <div class="card-body">

          <c:choose>
            <c:when test="${not empty error}">
              <div class="alert alert-danger">${error}</div>
            </c:when>
            <c:otherwise>
              <div class="table-responsive">
                <table class="table table-hover" id="dataTable" width="100%" cellspacing="0">
                  <tbody>
                  <tr>
                    <th scope="row">Id</th>
                    <td>${user.id}</td>
                  </tr>
                  <tr>
                    <th scope="row">Nazwa użytkownika</th>
                    <td>${user.userName}</td>
                  </tr>
                  <tr>
                    <th scope="row">Email</th>
                    <td>${user.email}</td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </c:otherwise>
          </c:choose>

        </div>
      </div>
    </div>
  </div>

</div>
<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->

<%@ include file="/footer.jsp" %>