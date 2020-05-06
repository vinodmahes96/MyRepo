<%@page import="model.Bill"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>

<head>


<meta charset="ISO-8859-1">

<title>Payments Management</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
<script src="Components/jquery-3.5.0.min.js"></script>
<script src="Components/bills.js"></script>



</head>

<body>

<div class="container">
<div class="row">
<div class="col-6">
	
	<h1>Bills Management</h1>


	<form id="formItem" name="formItem" method="post" action="bills.jsp">
	 
	 Payment Method:
	 <input id="PayMethod" name="PayMethod" type="text"
	 class="form-control form-control-sm">
	<br> Payment Amount:
	<input id="Amount" name="Amount" type="text"
	 class="form-control form-control-sm">
	<br> Patient ID:
	<input id="pid" name="pid" type="text"
	 class="form-control form-control-sm">
	<br> 
	<input id="btnSave" name="btnSave" type="button" value="Save"
	 class="btn btn-primary">
	 
	<input type="hidden" id="hidBillIDSave" name="hidBillIDSave" value="">
	
	</form>
	
	<div id="alertSuccess" class="alert alert-success">
	</div>
	<br>
	
	<div id="alertError" class="alert alert-danger"></div>
	<br>
	
	<div id="divItemsGrid">
	
	 <%
	 Bill BillObj = new Bill();
	 out.print(BillObj.readBill());
	 %>
	 
	</div>

</div>
</div>
</div>
	

</body>

</html>