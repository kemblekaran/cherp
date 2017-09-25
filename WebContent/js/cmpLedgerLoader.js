$(function() {

	//variable declaration
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	
	var fromTime = new Date($('#fromDate').val()).getTime();
	var toTime = new Date($('#toDate').val()).getTime();
	
	// Load company into dropdown list
	$.getJSON('/server/jsonfiles/company.json', function(data) {
		var jsonDataProduct = data['data'];
		$.each(jsonDataProduct, function(key, val) {
			$('#cmpName').append(
					'<option value="' + val.name + '">' + val.name
							+ '</option>');
		});
	});

	//getting dateOfOpening from company
	$("#cmpName").on("change", function() {
		var companyName = $(this).val();
		$.ajax({
			type : "POST",
			url : "/server/jsonfiles/company.json",
			dataType : "json",
			select : true,
			success : function(data) {
				var jsonDataProduct = data['data'];
				var jsonPayment = data['data'];

				$.each(jsonDataProduct, function(key, val) {

					if (companyName == val.name) {
						$('#dateAccOp').val(val.dateAccOp);
						
					} else if (companyName == "selectCmp") {
						$('#dateAccOp').val(null);
					}
				});

			}

		});
	});

	//closing bal as opening bal from payment
	$("#cmpName").on("change", function() {
		var companyName = $(this).val();

		$.ajax({
			type : "POST",
			url : "/server/jsonfiles/payment.json",
			dataType : "json",
			select : true,
			success : function(data) {
				var jsonDataProduct = data['data'];
				var jsonPayment = data['data'];

				$.each(jsonDataProduct, function(key, val) {

					if (companyName == val.company) {
						
						$('#opeBal').val(val.closingBal);
					} else if (companyName == "selectCmp") {
						$('#opeBal').val(null);
					}
					else{
						$("#cmpName").on("change", function() {
							$('#opeBal').val(0);
						});
					}
				});

			}

		});
	});

	var purchaseTable = $('#purchaseTable').DataTable();
	
	$('#go').on('click', function() {
//		alert('hey');
		var cmpName = $("#cmpName").val();
//		alert('hey ' + cmpName);
		$.ajax({
			type : "POST",
			url : "/server/jsonfiles/purchaseView.json",
			dataType : "json",
			select : true,
			success : function(data) {
				var purchaseData = data['data'];

				$.each(purchaseData, function(key, val) {

					if (cmpName == val.company) {
						
						var date = new Date(val.date);
						alert(date);
						alert(date.getTime() >= fromTime && date.getTime() <= toTime);
						if (date.getTime() >= fromTime && date.getTime() <= toTime) {
						
							purchaseTable.row.add([ val.date, val.purchaseId, val.product, val.pieces, val.kg, val.rate, val.amount ]).draw();
							$("#cmpName").on("change", function() {
								purchaseTable.clear().draw();
							});
							$('#go').on('click', function() {
								purchaseTable.clear().draw();
							});
						}
					}
						
				});

			}

		});
	});
	
var paymentTable = $('#paymentTable').DataTable();
	
	$('#go').on('click', function() {
	var cmpName = $("#cmpName").val();
//	alert(parseInt(fromDate) + toDate);
	
//	alert(fromTime);
//	alert(toTime);
	$.ajax({
		type : "POST",
		url : "/server/jsonfiles/payment.json",
		dataType : "json",
		select : true,
		success : function(data) {
			var paymentData = data['data'];

			$.each(paymentData, function(key, val) {

				if (cmpName == val.company) {
					
					var date = new Date(val.paymentDate);
//					alert(date.getTime() >= fromTime && date.getTime() <= toTime);
					if (date.getTime() >= fromTime && date.getTime() <= toTime) {
						
					
						
					paymentTable.row.add([ val.paymentDate, val.company,  val.payNow ]).draw();
					$("#cmpName").on("change", function() {
						paymentTable.clear().draw();
					});
					$('#go').on('click', function() {
						paymentTable.clear().draw();
					});
					}
				
				}
			});

		}

	});
});
	
	
	
	
	
	
	// For setting Today's date
	// $(document).ready(function() {
	// alert("datepicker");
	// $(function() {
	//
	// $("#fromDate").datepicker({
	// showAnim : 'drop',
	// showButtonPanel : true,
	// }).datepicker('setDate', 'today');
	// });
	// });

	// for setting from and to date
	$(document).ready(function() {
		$("#fromDate").datepicker({
//			dateFormat : "dd-M-yy",
			// minDate : 0,
			showAnim : 'drop',
			onSelect : function(date) {
				var date2 = $('#fromDate').datepicker('getDate');
				date2.setDate(date2.getDate() + 7);
				$('#toDate').datepicker('setDate', date2);
				// sets minDate to dt1 date + 1
				$('#toDate').datepicker('option', 'minDate', date2);
			}
		});
		$('#toDate').datepicker({
//			dateFormat : "dd-M-yy",
			showAnim : 'drop',
			onClose : function() {
				var dt1 = $('#fromDate').datepicker('getDate');
				var dt2 = $('#toDate').datepicker('getDate');
				// check to prevent a user from entering a date below date of
				// dt1
				if (dt2 <= dt1) {
					var minDate = $('#toDate').datepicker('option', 'minDate');
					$('#toDate').datepicker('setDate', minDate);
				}
			}
		});

	});

});// end of function
