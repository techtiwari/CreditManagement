<%@page import="java.util.ArrayList"%>
<%@page import="us.techtiwari.transactionbean.TransactionBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%ArrayList txrecord=(ArrayList)request.getAttribute("txdetail"); %>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta http-equiv="x-ua-compatible" content="ie=edge">
  <title>Material Design Bootstrap</title>
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
  <!-- Bootstrap core CSS -->
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <!-- Material Design Bootstrap -->
  <link href="css/mdb.min.css" rel="stylesheet">
  <!-- Your custom styles (optional) -->
  <link href="css/style.css" rel="stylesheet">
  <!-- MDBootstrap Datatables  -->
<link href="css/addons/datatables.min.css" rel="stylesheet">
</head>
<body>

  <table id="dtBasicExample" class="table table-striped table-bordered" cellspacing="0" width=90% >
    <thead>
      <tr>
        <th class="th-sm">Account no
        </th>
        <th class="th-sm">Name
        </th>
        <th class="th-sm">Email
        </th>
        <th class="th-sm">Balance
        </th>
      </tr>
    </thead>
    
    <% for(int recCount=0;recCount < txrecord.size();recCount++){ %>
     <tr>
     <% TransactionBean txdetail=(TransactionBean)txrecord.get(recCount); %>
     <td>
     <%=txdetail.getAccountno()%>
     </td>
     <td>
     <%=txdetail.getName()%>
     </td>
     <td>
     <%=txdetail.getBal()%>
     </td>
     <td>
     <%=txdetail.getStatus()%>
     </td>
     </tr>
    
    <%} %>
    <tbody>
           
  </table>

  <!-- SCRIPTS -->
  <!-- JQuery -->
  <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
  <!-- Bootstrap tooltips -->
  <script type="text/javascript" src="js/popper.min.js"></script>
  <!-- Bootstrap core JavaScript -->
  <script type="text/javascript" src="js/bootstrap.min.js"></script>
  <!-- MDB core JavaScript -->
  <script type="text/javascript" src="js/mdb.js"></script>
  <!-- MDBootstrap Datatables  -->
<script type="text/javascript" src="js/addons/datatables.min.js"></script>
<script>
  $(document).ready(function () {
$('#dtBasicExample').DataTable();
$('.dataTables_length').addClass('bs-select');
});
</script>
</body>
</html>