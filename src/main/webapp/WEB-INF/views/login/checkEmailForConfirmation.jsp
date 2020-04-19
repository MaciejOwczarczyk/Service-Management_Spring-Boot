<!doctype html>
<html lang="pl">

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>


    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


    <link href="<c:url value="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"/>"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">


    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Service Planner</title>

    <!-- Custom fonts for this template-->
    <link href="<c:url value="../../../resources/Front/vendor/fontawesome-free/css/all.min.css"/>" rel="stylesheet"
          type="text/css">
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"/>"
          rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="<c:url value="../../../resources/Front/css/sb-admin-2.min.css"/>" rel="stylesheet">


</head>

<body class="bg-gradient-primary">

<div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">


        <div class="col-xl-10 col-lg-12 col-md-9">
            <form th:action="@{login}" method="post">
                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-0">
                        <!-- Nested Row within Card Body -->
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
                                    </div>
                                    <div>
                                        <h1 class="h4 text-gray-900 mb-4">Please check your email in order to activate account!</h1>
                                    </div>
                                    <hr>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>

    </div>

</div>

<!-- Bootstrap core JavaScript-->
<script src="<c:url value="../../../resources/Front/vendor/jquery/jquery.min.js" />" type="text/javascript"></script>
<script src="<c:url value="../../../resources/Front/vendor/bootstrap/js/bootstrap.bundle.min.js" />"
        type="text/javascript"></script>


<!-- Core plugin JavaScript-->
<script src="<c:url value="../../../resources/Front/vendor/jquery-easing/jquery.easing.min.js" />"
        type="text/javascript"></script>

<!-- Custom scripts for all pages-->
<script src="<c:url value="../../../resources/Front/js/sb-admin-2.min.js" />" type="text/javascript"></script>

</body>

</html>
