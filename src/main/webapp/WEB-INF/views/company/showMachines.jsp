<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="pl">

<head>

    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Service Planner</title>

    <!-- Custom fonts for this template -->
    <link href='<c:url value="../../../resources/Front/vendor/fontawesome-free/css/all.min.css"/>' rel="stylesheet"
          type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href='<c:url value="../../../resources/Front/css/sb-admin-2.min.css"/>' rel="stylesheet" type="text/css">

    <!-- Custom styles for this page -->
    <link href='<c:url value="../../../resources/Front/vendor/datatables/dataTables.bootstrap4.min.css"/>' rel="stylesheet"
          type="text/css">

    <script>
        function confirmDelete(id, machine, companyId) {
            if (confirm("Are you sure you want to delete \"" + machine + "\"")) {
                window.location.href = "/company/delete/machine/" + id + "/" + companyId;
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

        <!-- Nav Item - Utilities Collapse Menu -->
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

                <!-- Topbar Search -->
                <form class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
                    <div class="input-group">
                        <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..."
                               aria-label="Search" aria-describedby="basic-addon2">
                        <div class="input-group-append">
                            <button class="btn btn-primary" type="button">
                                <i class="fas fa-search fa-sm"></i>
                            </button>
                        </div>
                    </div>
                </form>

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
                <a href="/company/addNewMachine/${companyId}" class="btn btn-warning btn-icon-split">
                            <span class="icon text-white-50">
                            <i class="fas fa-arrow-right"></i>
                            </span>
                    <span class="text">Add new machine to this client</span>
                    <div/>
                </a><br>
                <div class="card shadow mb-4">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <c:if test="${failedMachine == true}">
                                    <tr class="error">
                                        To this machine ticket has been assigned, you cannot delete it !
                                    </tr>
                                </c:if>
                                <tr>
                                    <th>Action</th>
                                    <th>Model</th>
                                    <th>Serial Number</th>
                                    <th>Warranty Start</th>
                                    <th>Warranty End</th>
                                    <th>Year of production</th>
                                    <th>Machine type</th>
                                    <th>Producer</th>
                                </tr>
                                </thead>
                                <tfoot>
                                <tr>
                                    <th>Action</th>
                                    <th>Model</th>
                                    <th>Serial Number</th>
                                    <th>Warranty Start</th>
                                    <th>Warranty End</th>
                                    <th>Year of production</th>
                                    <th>Machine type</th>
                                    <th>Producer</th>
                                </tr>
                                </tfoot>
                                <tbody>
                                <c:forEach items="${machines}" var="machine">
                                    <tr>
                                        <td>
                                            <a href="#" onclick="confirmDelete(${machine.id}, '${machine.model}', '${companyId}')"
                                               class="btn btn-danger btn-circle">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                            <a href="/company/edit/machine/${machine.id}"
                                               class="btn btn-dark btn-circle btn-circle-sm m-1">
                                                <i class="fa fa-edit"></i>
                                            </a>
                                        </td>
                                        <td>${machine.model}</td>
                                        <td>${machine.serialNumber}</td>
                                        <td>${machine.warrantyStart}</td>
                                        <td>${machine.warrantyEnd}</td>
                                        <td>${machine.yearOfProduction}</td>
                                        <td>${machine.machineType.name}</td>
                                        <td>${machine.producer.name}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

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
