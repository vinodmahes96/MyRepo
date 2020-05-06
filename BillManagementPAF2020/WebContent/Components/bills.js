$(document).ready(function() {
	
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateBillForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid------------------------
	var type = ($("#hidBillIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax({
		url : "BillsAPI",
		type : type,
		data : $("#formItem").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onBillSaveComplete(response.responseText, status);
		}
	});
	
	});	
	

	function onBillSaveComplete(response, status) {

		if (status == "success") {
			var resultSet = JSON.parse(response);
			if (resultSet.status.trim() == "success") {
				$("#alertSuccess").text("Successfully saved.");
				$("#alertSuccess").show();
				$("#divItemsGrid").html(resultSet.data);
			} else if (resultSet.status.trim() == "error") {
				$("#alertError").text(resultSet.data);
				$("#alertError").show();
			}
		} else if (status == "error") {
			$("#alertError").text("Error while saving.");
			$("#alertError").show();
		} else {
			$("#alertError").text("Unknown error while saving..");
			$("#alertError").show();
		}
		$("#hidBillIDSave").val("");
		$("#formItem")[0].reset();
	}
	
	



function onBillDeleteComplete(response, status) {
	
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}


// REMOVE=============================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "BillsAPI",
		type : "DELETE",
		//possible problem here
		data : "BillID=" + $(this).data("billid"),
		dataType : "text",
		complete : function(response, status) {
			onBillDeleteComplete(response.responseText, status);
		}
	});
});



//UPDATE==========================================
$(document).on( "click", ".btnUpdate", function(event) {
			$("#hidBillIDSave").val($(this).closest("tr").find('#hidBillIDUpdate').val());
			$("#PayMethod").val($(this).closest("tr").find('td:eq(0)').text());
			$("#Amount").val($(this).closest("tr").find('td:eq(1)').text());
			$("#pid").val($(this).closest("tr").find('td:eq(2)').text());

		});




// CLIENTMODEL=========================================================================
function validateBillForm() {

	// PayMethod
	if ($("#PayMethod").val().trim() == "") {
		return "Insert payment method.";
	}

	// Amount-------------------------------
	if ($("#Amount").val().trim() == "") {
		return "Insert payment amount.";
	}
	// to numerical value------------------
	var tmpPrice = $("#Amount").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Payment Amount.";
	}
	// convert to decimal price--------------
	$("#Amount").val(parseFloat(tmpPrice).toFixed(2));

	// PID-----------------------------------
	if ($("#pid").val().trim() == "") {
		return "Insert patient ID.";
	}
	
	/*
	var type = ($("#hidBillIDSave").val() == "") ? "POST" : "PUT";

	
	$.ajax({
		url : "BillsAPI",
		type : type,
		data : $("#formItem").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onBillSaveComplete(response.responseText, status);
		}
	});
	
	*/


	
	return true;
}









