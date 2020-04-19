<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="pl">

<head>

    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Service Planner</title>

    <!-- Custom fonts for this template -->
    <link href='<c:url value="../../../resources/Front/vendor/fontawesome-free/css/all.min.css"/>' rel="stylesheet"
          type="text/css">

    <link href="<c:url value="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"/>"
          rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href='<c:url value="../../../resources/Front/css/sb-admin-2.min.css"/>' rel="stylesheet" type="text/css">


    <!-- Custom styles for this page -->
    <link href='<c:url value="../../../resources/Front/vendor/datatables/dataTables.bootstrap4.min.css"/>' rel="stylesheet"
          type="text/css">

    <script>

        function confirmDelete(activityId) {
            if (confirm("Are you sure you want to delete this activity")) {
                window.location.href = "../../activity/delete/" + activityId;
            }
        }

        function confirmClose(id, serviceTicket) {
            if (confirm("Are you sure you want to close ticket assigned to \"" + serviceTicket + "\"")) {
                window.location.href = "/serviceTicket/close/" + id;
            }
        }
        function confirmToClose(id) {
            if(confirm("Are you sure you want to make status to close ?")) {
                window.location.href = "/serviceTicket/toClose/" + id;
            }
        }
    </script>

</head>

<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center" href="/">
            <div class="sidebar-brand-icon rotate-n-15">
                <i class="fas fa-laugh-wink"></i>
            </div>
            <div class="sidebar-brand-text mx-3">Service Planner</div>
        </a>

        <!-- Divider -->
        <hr class="sidebar-divider my-0">

        <!-- Nav Item - Dashboard -->
        <li class="nav-item active">
            <a class="nav-link" href="/">
                <i class="fas fa-fw fa-tachometer-alt"></i>
                <span>Dashboard</span></a>
        </li>

        <hr class="sidebar-divider my-0">

        <li class="nav-item active">
            <a class="nav-link" href="/calendar">
                <i class="fas fa-calendar-alt"></i>
                <span>Calendar</span></a>
        </li>

        <!-- Divider -->
        <hr class="sidebar-divider my-0">

        <li class="nav-item active">
            <a class="nav-link" href="/serviceTicket/showAllOpen">
                <i class="fas fa-ticket-alt"></i>
                <span>Service tickets</span></a>
        </li>



        <!-- Nav Item - Utilities Collapse Menu -->
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_USER')">
            <hr class="sidebar-divider">
            <!-- Heading -->
            <div class="sidebar-heading">
                Resources
            </div>

            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseFive"
                   aria-expanded="true" aria-controls="collapseFive">
                    <i class="fas fa-fw fa-cog"></i>
                    <span>Manage</span>
                </a>
                <div id="collapseFive" class="collapse" aria-labelledby="headingFive" data-parent="#accordionSidebar">
                    <div class="bg-white py-4 collapse-inner rounded">
                        <h6 class="collapse-header">Custom Components:</h6>
                        <a class="collapse-item" href="/machine/showAll">Machines</a>
                        <a class="collapse-item" href="/company/showAll">Clients</a>
                        <a class="collapse-item" href="/producer/showAll">Producers</a>
                        <a class="collapse-item" href="/salesman/showAll">Salesmen</a>
                        <a class="collapse-item" href="/technician/showAll">Technicians</a>
                    </div>
                </div>
            </li>
        </sec:authorize>

        <!-- Nav Item - Utilities Collapse Menu -->
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities"
                   aria-expanded="true" aria-controls="collapseUtilities">
                    <i class="fas fa-fw fa-wrench"></i>
                    <span>Utilities</span>
                </a>
                <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities"
                     data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">Custom Utilities:</h6>
                        <a class="collapse-item" href="/admin/ticketStatus/showAll">Ticket Statuses</a>
                        <a class="collapse-item" href="/admin/ticketType/showAll">Ticket Types</a>
                        <a class="collapse-item" href="/admin/country/showAll">Countries</a>
                        <a class="collapse-item" href="/admin/province/showAll">Provinces</a>
                        <a class="collapse-item" href="/admin/user/showAll">Users</a>
                        <a class="collapse-item" href="/admin/role/showAll">Roles</a>
                        <a class="collapse-item" href="/admin/machineType/showAll">Machine types</a>
                    </div>
                </div>
            </li>
        </sec:authorize>

        <!-- Divider -->

        <!-- Heading -->

        <!-- Nav Item - Pages Collapse Menu -->

        <!-- Nav Item - Charts -->

        <!-- Divider -->
        <hr class="sidebar-divider d-none d-md-block">

        <!-- Sidebar Toggler (Sidebar) -->
        <div class="text-center d-none d-md-inline">
            <button class="rounded-circle border-0" id="sidebarToggle"></button>
        </div>

    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">

            <!-- Topbar -->
            <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                <!-- Sidebar Toggle (Topbar) -->
                <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                    <i class="fa fa-bars"></i>
                </button>

                <!-- Topbar Navbar -->
                <ul class="navbar-nav ml-auto">

                    <!-- Nav Item - Search Dropdown (Visible Only XS) -->
                    <li class="nav-item dropdown no-arrow d-sm-none">
                        <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-search fa-fw"></i>
                        </a>
                        <!-- Dropdown - Messages -->
                        <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
                             aria-labelledby="searchDropdown">
                            <form class="form-inline mr-auto w-100 navbar-search">
                                <div class="input-group">
                                    <input type="text" class="form-control bg-light border-0 small"
                                           placeholder="Search for..." aria-label="Search"
                                           aria-describedby="basic-addon2">
                                    <div class="input-group-append">
                                        <button class="btn btn-primary" type="button">
                                            <i class="fas fa-search fa-sm"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </li>

                    <div class="topbar-divider d-none d-sm-block"></div>

                    <!-- Nav Item - User Information -->
                    <li class="nav-item dropdown no-arrow">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="mr-2 d-none d-lg-inline text-gray-600 small">${userDetails.username}</span>
                        </a>
                        <!-- Dropdown - User Information -->
                        <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                             aria-labelledby="userDropdown">
                            <a class="dropdown-item" href="#">
                                <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                                Settings
                            </a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                                <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                Logout
                            </a>
                        </div>
                    </li>

                </ul>

            </nav>
            <!-- End of Topbar -->

            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Page Heading -->
                <sec:authorize access="hasAnyRole('ROLE_ENGINEER')">
<%--                    <a href="/serviceTicket/toClose/${serviceTicket.id}" class="btn btn-info btn-icon-split">--%>
<%--                            <span class="icon text-white-50">--%>
<%--                            <i class="fas fa-archive"></i>--%>
<%--                            </span>--%>
<%--                        <span class="text">To Close</span>--%>
<%--                    </a>--%>
                    <a href="#" onclick="confirmToClose(${serviceTicket.id})" class="btn btn-info btn-icon-split">
                       <span class="icon text-white-50">
                            <i class="fas fa-archive"></i>
                            </span>
                        <span class="text">To Close</span>
                    </a>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_USER')">
                <a href="/planner/add/${serviceTicket.id}" class="btn btn-dark btn-icon-split">
                    <span class="icon text-white-50">
                            <i class="fas fa-calendar-alt"></i>
                            </span>
                    <span class="text">Planner</span>
                </a>
                <a href="/serviceTicket/edit/${serviceTicket.id}" class="btn btn-success btn-icon-split">
                    <span class="icon text-white-50">
                            <i class="fas fa-edit"></i>
                            </span>
                    <span class="text">Edit ticket</span>
                </a>
                <a href="/serviceTicket/close/${serviceTicket.id}" class="btn btn-danger btn-icon-split">
                            <span class="icon text-white-50">
                            <i class="fas fa-archive"></i>
                            </span>
                    <span class="text">Close ticket</span>
                </a>
                </sec:authorize>
                <br/>
                <br>

                <div class="card shadow mb-4">
                    <div class="card-body">
                        <div class="table">
                            <table class="table" width="100%" cellspacing="4">
                                <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Notes</th>
                                    <th>Company</th>
                                    <th>Machine</th>
                                    <th>Type</th>
                                    <th>Solution</th>
                                    <th>Open date</th>
                                    <th>Close date</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>${serviceTicket.id}</td>
                                    <td>${serviceTicket.title}</td>
                                    <td>${serviceTicket.company.name}</td>
                                    <td>${serviceTicket.machine.model}</td>
                                    <td>${serviceTicket.ticketType.name}</td>
                                    <td>${serviceTicket.solution}</td>
                                    <td>${serviceTicket.openDate}</td>
                                    <td>${serviceTicket.closeDate}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="card shadow mb-4">
                    <div class="card-body">
                        <div class="table">
                            <span class="text-dark font-weight-bolder">Planner</span>
                            <p/>
                            <table class="table" width="auto" cellspacing="4">
                                <thead>
                                <tr>
                                    <th>Activity</th>
                                    <th>Id</th>
                                    <th>Client</th>
                                    <th>Machine</th>
                                    <th>Start</th>
                                    <th>End</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${planners}" var="planner">
                                    <tr>
                                        <td>
                                            <a href="/activity/add/${planner.id}"
                                               class="btn btn-primary btn-circle">
                                                <i class="fas fa-clock"></i>
                                            </a>
                                        </td>
                                        <td>${planner.id}</td>
                                        <td>${planner.serviceTicket.company.name}</td>
                                        <td>${planner.serviceTicket.machine.model}</td>
                                        <td>${planner.start.substring(0, 10)}</td>
                                        <td>${planner.end.substring(0, 10)}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="card shadow mb-4">
                    <div class="card-body">
                        <div class="table">
                            <span class="text-dark font-weight-bolder">Activities</span>
                            <span class="text-dark font-weight-bolder">| Work time: ${sumOfWorkTime} </span>
                            <span class="text-dark font-weight-bolder">| Drive time: ${sumOfDriveTime}</span>
                            <span class="text-dark font-weight-bolder">| Total: ${totalTime} </span>
                            <span class="text-dark font-weight-bolder">| Km: ${sumOfKm} </span>
                            <p/>
                            <table class="table" width="100%" cellspacing="4">
                                <thead>
                                <tr>
                                    <th>Action</th>
                                    <th>Id</th>
                                    <th>Date</th>
                                    <th>Description</th>
                                    <th>Start drive from base</th>
                                    <th>Arrive on site</th>
                                    <th>Start work on site</th>
                                    <th>Finish work on site</th>
                                    <th>Start drive from site</th>
                                    <th>Arrive to base</th>
                                    <th>Rest</th>
                                    <th>Kilometers to site</th>
                                    <th>Kilometers to base</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${activities}" var="activity">
                                    <tr>
                                        <td>
                                            <a href="/activity/edit/${activity.id}"
                                               class="btn btn-success btn-circle">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="#" onclick="confirmDelete(${activity.id})"
                                               class="btn btn-danger btn-circle">
                                                <i class="fas fa-eraser"></i>
                                            </a>
                                        </td>
                                        <td>${activity.id}</td>
                                        <td>${activity.date}</td>
                                        <td>${activity.description}</td>
                                        <td>${activity.startDriveFromBase}</td>
                                        <td>${activity.arriveOnSite}</td>
                                        <td>${activity.startWorkOnSite}</td>
                                        <td>${activity.finishWorkOnSite}</td>
                                        <td>${activity.startDriveFromSite}</td>
                                        <td>${activity.arriveToBase}</td>
                                        <td>${activity.rest}</td>
                                        <td>${activity.kilometersToSite}</td>
                                        <td>${activity.kilometersToBase}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="card shadow mb-4">
                    <div class="card-body">
                                        <div class="form-control-file">
                                            <form method="post" enctype="multipart/form-data">
                                                Select File:
                                                <input type="file" name="file"/>
                                                <input type="submit" value="Upload File"/>
                                                <c:if test="${overSize == true}">
                                                    <br/>
                                                    <tr class="error">File size must not be greater than 5 MB</tr>
                                                </c:if>
                                            </form>
                                            </div>
                    </div>
                </div>

                <c:forEach items="${files}" var="file">
                        <div class="container-fluid">
                            <tr>
                                <td>
                                    <img width="250" height="250" src="/serviceTicket/getFile/<c:out value='${file.id}'/>"/>
                                    <a href="/serviceTicket/downloadFile/${file.id}"
                                       class="btn btn-circle btn-warning">
                                        <i class="fas fa-download"></i>
                                    </a>
                                    <a href="/serviceTicket/deleteFile/${file.id}"
                                       class="btn btn-circle btn-danger">
                                        <i class="fas fa-eraser"></i>
                                    </a>
                                </td>
                                <br/>
                            </tr>
                        </div>
                </c:forEach>

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- End of Main Content -->

        <!-- Footer -->
        <footer class="sticky-footer bg-white">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright &copy; Your Website 2019</span>
                </div>
            </div>
        </footer>
        <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                <a class="btn btn-primary" href="/logout">Logout</a>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript-->
<script src="<c:url value='../../../resources/Front/vendor/jquery/jquery.min.js' />" type="text/javascript"></script>
<script src="<c:url value='../../../resources/Front/vendor/bootstrap/js/bootstrap.bundle.min.js' />"
        type="text/javascript"></script>

<!-- Core plugin JavaScript-->
<script src="<c:url value='../../../resources/Front/vendor/jquery-easing/jquery.easing.min.js' />"
        type="text/javascript"></script>

<!-- Custom scripts for all pages-->
<script src="<c:url value='../../../resources/Front/js/sb-admin-2.min.js' />" type="text/javascript"></script>

<!-- Page level plugins -->
<script src="<c:url value='../../../resources/Front/vendor/datatables/jquery.dataTables.min.js' />"
        type="text/javascript"></script>
<script src="<c:url value='../../../resources/Front/vendor/datatables/dataTables.bootstrap4.min.js' />"
        type="text/javascript"></script>

<!-- Page level custom scripts -->
<script src="<c:url value='../../../resources/Front/js/demo/datatables-demo.js' />" type="text/javascript"></script>

</body>

</html>
