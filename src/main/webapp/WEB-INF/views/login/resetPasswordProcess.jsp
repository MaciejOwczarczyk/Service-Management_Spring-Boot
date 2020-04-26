<!doctype html>
<html lang="pl">

<head>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Service Planner</title>

    <link href="<c:url value="../../../../resources/Front/vendor/fontawesome-free/css/all.min.css"/>" rel="stylesheet"
          type="text/css">
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"/>"
          rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="<c:url value="../../../../resources/Front/css/sb-admin-2.min.css"/>" rel="stylesheet">

</head>

<body class="bg-gradient-primary">

<div class="container">
        <div class="card o-hidden border-0 shadow-lg my-5">
            <div class="card-body p-0">
                <!-- Nested Row within Card Body -->
                <div class="row">

                    <div class="col-lg-7">
                        <div class="p-5">
                            <div class="text-center">
                                <h1 class="h4 text-gray-900 mb-4">Reset Password!</h1>
                            </div>
                            <form:form method="post">
                            <form class="user">
                                <div class="form-group row">
<%--                                    <input type="hidden" name="confirmationTokenId" value="${confirmationTokenId}">--%>
                                    <div class="col-sm-6 mb-3 mb-sm-0">
                                        Password
                                        <label>
                                            <input name="password" type="password" class="form-control form-control-user"
                                                   placeholder="repeat password">
                                        </label>
                                    </div>
                                    <div class="col-sm-6">
                                        Repeat Password
                                        <label>
                                            <input name="rePassword" type="password" class="form-control form-control-user"
                                                   placeholder="repeat password">
                                        </label>
                                    </div>
                                    <c:if test="${passwordFail == true}">
                                        <tr class="error">Password does not match</tr>
                                    </c:if>
                                </div>
                                <hr>
                                <div class="text-center">
                                    <input type="submit" class="btn btn-primary btn-user btn-block" value="Reset password">
                                </div>
                            </form>
                            </form:form>
                            <div class="text-center">
                                <a class="small" href="/login">Login!</a>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

        </div>
</div>

<!-- Bootstrap core JavaScript-->
<script src="<c:url value='../../../resources/Front/vendor/bootstrap/js/bootstrap.bundle.min.js' />"
        type="text/javascript"></script>
<script src="<c:url value='../../../resources/Front/vendor/jquery/jquery.min.js' />" type="text/javascript"></script>


<!-- Core plugin JavaScript-->
<script src="<c:url value='../../../resources/Front/vendor/jquery-easing/jquery.easing.min.js' />"
        type="text/javascript"></script>

<!-- Custom scripts for all pages-->
<script src="<c:url value='../../../resources/Front/js/sb-admin-2.min.js'/>" type="text/javascript"></script>

</body>

</html>
