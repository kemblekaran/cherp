$(function() {

	var cmpLedgerArray = [];
	var kgs = 0;
	var pieces = 0;
	var amount = 0;
	var payment = 0;
	var weekPurchase = $("#weekPurchase");
	var totalAmount = $("#totalAmount");
	var paymentGiven = $("#paymentGiven") ;
	var closingBal = $("#closingBal") ;
	var paymentCalc = null ;
	var opeBal = $('#opeBal');
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
	
	$('#go').on('click', function(e) {
//		alert('hey');
		event.preventDefault();
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
						//getting from and to date from input
						var fromTime = new Date($('#fromDate').val()).getTime();
						var toTime = new Date($('#toDate').val()).getTime();
						
						//getting date from json file
//						var date = new Date('13/10/2017');
						var date = new Date(val.date);
						
						
						//check whether date is true from selected date
						if (date.getTime() >= fromTime && date.getTime() <= toTime) {
							
							purchaseTable.row.add([ val.date, val.purchaseId, val.product, val.pieces, val.kg, val.rate, val.amount ]).draw();
								$("#cmpName").on("change", function() {
									purchaseTable.clear().draw();
								});
								$('#go').on('click', function() {
									purchaseTable.clear().draw();
								});
								kgs = kgs + val.kg;
								pieces = pieces + val.pieces;
								amount = amount + val.amount;
						}
					}
				});
				var totalKgs = kgs;
				var totalPcs = pieces;
				var totalAmt = amount;
				kgs = 0;
				pieces = 0;
				amount = 0;
				$('#kgs').val(totalKgs);
				$('#pcs').val(totalPcs);
				weekPurchase.val(totalAmt);
				totalAmount.val(totalAmt + parseInt(opeBal.val()));
			}

		});
	});
	
var paymentTable = $('#paymentTable').DataTable();
	
	$('#go').on('click', function(e) {
		event.preventDefault();
	var cmpName = $("#cmpName").val();

	$.ajax({
		type : "POST",
		url : "/server/jsonfiles/payment.json",
		dataType : "json",
		select : true,
		success : function(data) {
			var paymentData = data['data'];

			$.each(paymentData, function(key, val) {

				if (cmpName == val.company) {
					//getting from and to date from input
					var fromTime = new Date($('#fromDate').val()).getTime();
					var toTime = new Date($('#toDate').val()).getTime();
					
					//getting date from json file
					var date = new Date(val.paymentDate);
					
					//check whether date is true from selected date
					if (date.getTime() >= fromTime && date.getTime() <= toTime) {
						
						paymentTable.row.add([ val.paymentDate, val.company,  val.payNow ]).draw();
							$("#cmpName").on("change", function() {
								paymentTable.clear().draw();
							});
							$('#go').on('click', function() {
								paymentTable.clear().draw();
							});
							payment = payment + val.payNow;
					}
				}
			});
			var weekPayment = payment;
			$('#payment').val(weekPayment);
			paymentGiven.val(weekPayment);
			paymentCalc = totalAmount.val() - weekPayment;
			$('#closingBal').val(paymentCalc);
			payment = 0;
		}

	});
});
	$("#optionsRadios1, #optionsRadios2").change(function() {
		$("#addLess").val(null);
		$('#closingBal').val(paymentCalc);
	});
	
	$("#addLess").on('input', function() {
		var radio = $("input[name='addLess']:checked").val();
		
		var total = 0;
		if(radio == "add"){
			total = paymentCalc + parseInt($("#addLess").val());
		}else if(radio == "less"){
			total = paymentCalc - parseInt($("#addLess").val());
		}
			
		
		if (total >= 0) {

			var bal = parseInt($('#closingBal').val(total));
		} else {
			$('#closingBal').val(paymentCalc);
			$("#addLess").val(null);
			// alert("Please enter right amount");
		}
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
//			dateFormat: 'dd/mm/yy',
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
//			dateFormat: 'dd/mm/yy',
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
	
	
	
	// ajaxCall to purchaseServlet
	$('#print').on('click', function() {
		
		var LedgerData = {
				"cmpName" : $('#cmpName').val(),
				"dateAccOp" : $('#dateAccOp').val(),
				"fromDate" : $('#fromDate').val(),
				"toDate" : $('#toDate').val(),
				"opBal" : $('#opeBal').val(),
				"totalKgs" : $('#kgs').val(),
				"totalPcs" : $('#pcs').val(),
				"weekPayment" : $('#payment').val(),
				"weekPurchase" : $('#weekPurchase').val(),
				"totalAmount" : $('#totalAmount').val(),
				"paymentGiven" : $('#paymentGiven').val(),
				"addLess" : $('#addLess').val(),
				"closingBal" : $('#closingBal').val()
				
		}
		cmpLedgerArray.push(LedgerData);

		var cmpLedgerJson = '{data:' + JSON.stringify(cmpLedgerArray) + '}';
		
		$('#cmpLedgerJson').val(cmpLedgerJson);
		
		console.log("cmpLedgerJson---" + cmpLedgerJson);
		
		$('#CmpLedgerForm').submit(function(e) {

			$.ajax({
				url : 'CmpLedgerServlet',
				type : 'post',
				data : $('#CmpLedgerForm').serialize(),

				success : function(data) {
					console.log('success');
				},
				error : function() {

				}
			})
		});
	});


});// end of function
