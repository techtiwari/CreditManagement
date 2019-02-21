<%@page import="java.util.ArrayList"%>
<%@page import="us.techtiwari.bean.UserDetailsBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%ArrayList userrecord=(ArrayList)request.getAttribute("userdetail"); %>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>Material Design Bootstrap</title>
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
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

	<div style="pading:5px;margin:5px;">
	<table id="dtBasicExample" class="table table-striped table-bordered"
		 style="width:100%">
		<thead>
			<tr>
				<th class="th-sm">Account No</th>
				<th class="th-sm">Name</th>
				<th class="th-sm">Email</th>
				<th class="th-sm">Balance</th>
			</tr>
		</thead>

		<% for(int recCount=0;recCount < userrecord.size();recCount++){ %>
		<tr>
			<% UserDetailsBean userdetail=(UserDetailsBean)userrecord.get(recCount); %>
			<td><%=userdetail.getAccNo()%></td>
			<td><%=userdetail.getName()%></td>
			<td><%=userdetail.getEmail()%></td>
			<td><%=userdetail.getBalance()%></td>
		</tr>

		<%} %>
		<tbody>
	</table>
</div>
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