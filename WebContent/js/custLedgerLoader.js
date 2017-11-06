$(function() {

	var salesTable = $('#salesTable').DataTable();
	var collectionTable = $('#collectionTable').DataTable();
	
	var custLedgerArray = [];
	var kgs = 0;
	var pieces = 0;
	var amount = 0;
	var collection = 0;
	var weekSales = $("#weekSales");
	var totalAmount = $("#totalAmount");
	var collectionReceived = $("#collectionReceived") ;
	var closingBal = $("#closingBal") ;
	var collectionCalc = null ;
	var opeBal = $('#opeBal');
	// Load company into dropdown list
	$.getJSON('/server/jsonfiles/customer.json', function(data) {
		var jsonDataProduct = data['data'];
		$.each(jsonDataProduct, function(key, val) {
			$('#custName').append(
					'<option value="' + val.fname + " " +val.lname + '">' + val.fname + " "+ val.lname
							+ '</option>');
		});
	});

	//getting dateOfOpening from company
	$("#custName").on("change", function() {
		var customerName = $(this).val();
		$.ajax({
			type : "POST",
			url : "/server/jsonfiles/customer.json",
			dataType : "json",
			select : true,
			success : function(data) {
				var jsonDataProduct = data['data'];
				var jsonPayment = data['data'];

				$.each(jsonDataProduct, function(key, val) {

					if (customerName ==val.fname + " " +val.lname ) {
						$('#dateAccOp').val(val.dateAccOp);
						
					} else if (customerName == "selectCust") {
						$('#dateAccOp').val(null);
					}
				});

			}

		});
	});

	//closing bal as opening bal from payment
	$("#custName").on("change", function() {
		var customerName = $(this).val();

		$('#opeBal').val(null);
		$("#pcs").val(null);
		$("#kgs").val(null);
		$("#collection").val(null);
		$("#weekSales").val(null);
		$("#totalAmount").val(null);
		$("#collectionReceived").val(null);
		$("#addLess").val(null);
		$("#closingBal").val(null);
	});

	
	
	$('#go').on('click', function(e) {

		event.preventDefault();
		$('#opeBal').val(null);
		$("#pcs").val(null);
		$("#kgs").val(null);
		$("#collection").val(null);
		$("#weekSales").val(null);
		$("#totalAmount").val(null);
		$("#collectionReceived").val(null);
		$("#addLess").val(null);
		$("#closingBal").val(null);
		
		var custName = $("#custName").val();
		
		var saleTotalAmt = 0;
		var collectionTotalAmt = 0;
		var debitCreditNoteAmt = 0;
	

		$.ajax({
			type : "POST",
			url : "/server/jsonfiles/salesView.json",
			dataType : "json",
			
			success : function(data) {
				var salesData = data['data'];
				var amount = 0;
				var saleAmount = 0;
				$.each(salesData, function(key, val) {

					if (custName == val.customer) {
						//getting from and to date from input
						var curDate = $('#fromDate').datepicker('getDate');
						var preDate = curDate.setDate(curDate.getDate() - 1);

						var custDate = new Date(val.salesDate);
						if(custDate.getTime() <= preDate){
							saleAmount = saleAmount + val.amount;
						}
						
//						--------------------------------------------------------------------------
						var fromTime = new Date($('#fromDate').val()).getTime();
						var toTime = new Date($('#toDate').val()).getTime();
						var date = new Date(val.salesDate);
						
						if (date.getTime() >= fromTime
								&& date.getTime() <= toTime) {

							salesTable.row.add([ val.salesDate, val.purchaseId, val.product, val.pieces, val.kg, val.rate, val.amount ]).draw();
							$("#custName").on("change", function() {
								salesTable.clear().draw();
							});
							$('#go').on('click', function() {
								salesTable.clear().draw();
							});
							kgs = kgs + val.kg;
							pieces = pieces + val.pieces;
							amount = amount + val.amount;
						}
						
					}
				});
				saleTotalAmt = saleAmount;
				saleAmount = 0; 
				
				var totalKgs = kgs;
				var totalPcs = pieces;
				var totalAmt = amount;
				kgs = 0;
				pieces = 0;
				amount = 0;
				$('#kgs').val(totalKgs.toFixed(2));
				$('#pcs').val(totalPcs);
				weekSales.val(totalAmt.toFixed(2));
				
				
				
				//get payment total amount
				$.ajax({
					type : "POST",
					url : "/server/jsonfiles/collection.json",
					dataType : "json",
					
					success : function(data) {
						var paymentData = data['data'];
						var amount = 0;
						$.each(paymentData, function(key, val) {

							if (custName == val.customer) {
								var curDate = $('#fromDate').datepicker('getDate');

								var preDate = curDate.setDate(curDate.getDate() - 1);
								var cmpDate = new Date(val.paymentDate);

								if(cmpDate.getTime() <= preDate){
									amount = amount + val.payNow;
								}
								
//								-----------------------------------------------------
								var fromTime = new Date($('#fromDate').val()).getTime();
								var toTime = new Date($('#toDate').val()).getTime();
								
								//getting date from json file
								var date = new Date(val.collectionDate);
								
								//check whether date is true from selected date
								if (date.getTime() >= fromTime && date.getTime() <= toTime) {
									
									collectionTable.row.add([ val.collectionDate, val.collectionMode,  val.payNow ]).draw();
									$("#custName").on("change", function() {
										collectionTable.clear().draw();
									});
									$('#go').on('click', function() {
										collectionTable.clear().draw();
									});
									collection = collection + val.payNow;
								}
							}
						});
						collectionTotalAmt = amount;
						amount = 0;
						
						
						var weekCollection = collection;
						$('#collection').val(weekCollection.toFixed(2));
						collectionReceived.val(weekCollection.toFixed(2));
						
						collection = 0;
						
						
						//get debit credit note data
						$.ajax({
							type : "POST",
							url : "/server/jsonfiles/debitcreditnote.json",
							dataType : "json",
							
							success : function(data) {
								var debitCreditData = data['data'];
								var amount = 0;
								var drcrAmt = 0;
								$.each(debitCreditData, function(key, val) {
									
									if (custName == val.selectCustCmp) {
										var curDate = $('#fromDate').datepicker('getDate');
//										alert(curDate.getTime() + 'cur date');
										var preDate = curDate.setDate(curDate.getDate() - 1);
//										alert(preDate + 'pre date');
										var cmpDate = new Date(val.debitCreditDate);
//										alert(cmpDate.getTime() <= preDate);
										if(cmpDate.getTime() <= preDate){
											amount = amount + val.amount;
										}
										
//					----------------------------------------------------------------------------------------
										var fromTime = new Date($('#fromDate').val()).getTime();
										var toTime = new Date($('#toDate').val()).getTime();
										
										//getting date from json file
										var date = new Date(val.debitCreditDate);
										
										//check whether date is true from selected date
										if (date.getTime() >= fromTime && date.getTime() <= toTime) {
											
											collectionTable.row.add([ val.debitCreditDate, val.remarks,  val.amount ]).draw();
											$("#custName").on("change", function() {
												collectionTable.clear().draw();
											});
											$('#go').on('click', function() {
												collectionTable.clear().draw();
											});
												drcrAmt = drcrAmt + val.amount;
										}
										
									}
								});
								var debitCreditAmt = drcrAmt;
								drcrAmt = 0;
//								alert(debitCreditAmt);
								$('#addLess').val(debitCreditAmt.toFixed(2));
								
								debitCreditNoteAmt= amount;
//								alert(debitCreditNoteAmt + "drcrAmt");
								amount = 0;

								$('#opeBal').val((saleTotalAmt - (collectionTotalAmt + debitCreditNoteAmt)).toFixed(2));
//								alert(weekPurchase.val());
								totalAmount.val((parseFloat(weekSales.val()) + (saleTotalAmt - (collectionTotalAmt + debitCreditNoteAmt))).toFixed(2));
//								
								collectionCalc = totalAmount.val() - (weekCollection + debitCreditAmt);
//								alert(collectionCalc);
								$('#closingBal').val(collectionCalc.toFixed(2));
							}
						});//third ajax method close
						
						
					}
				});//second ajax method close
				
			}
		});//first ajax method close
		
	});//onclick go method
	
	
	
//	$('#go').on('click', function(e) {
////		alert('hey');
//		event.preventDefault();
//		var custName = $("#custName").val();
////		alert('hey ' + cmpName);
//		$.ajax({
//			type : "POST",
//			url : "/server/jsonfiles/salesView.json",
//			dataType : "json",
//			select : true,
//			success : function(data) {
//				var salesData = data['data'];
//
//				$.each(salesData, function(key, val) {
//
//					if (custName == val.customer) {
//						//getting from and to date from input
//						var fromTime = new Date($('#fromDate').val()).getTime();
//						var toTime = new Date($('#toDate').val()).getTime();
//						
//						//getting date from json file
//						var date = new Date(val.salesDate);
////						alert(date);
//						
//						//check whether date is true from selected date
//						if (date.getTime() >= fromTime && date.getTime() <= toTime) {
//						
//							salesTable.row.add([ val.salesDate, val.purchaseId, val.product, val.pieces, val.kg, val.rate, val.amount ]).draw();
//								$("#custName").on("change", function() {
//									salesTable.clear().draw();
//								});
//								$('#go').on('click', function() {
//									salesTable.clear().draw();
//								});
//								kgs = kgs + val.kg;
//								pieces = pieces + val.pieces;
//								amount = amount + val.amount;
//						}
//					}
//				});
//				var totalKgs = kgs;
//				var totalPcs = pieces;
//				var totalAmt = amount;
//				kgs = 0;
//				pieces = 0;
//				amount = 0;
//				$('#kgs').val(totalKgs);
//				$('#pcs').val(totalPcs);
//				weekSales.val(totalAmt);
//				totalAmount.val(totalAmt + parseInt(opeBal.val()));
//			}
//
//		});
//	});
//	
//
//	
//	$('#go').on('click', function(e) {
//		event.preventDefault();
//	var custName = $("#custName").val();
//
//	$.ajax({
//		type : "POST",
//		url : "/server/jsonfiles/collection.json",
//		dataType : "json",
//		select : true,
//		success : function(data) {
//			var collectionData = data['data'];
//
//			$.each(collectionData, function(key, val) {
//
//				if (custName == val.customer) {
//					//getting from and to date from input
//					var fromTime = new Date($('#fromDate').val()).getTime();
//					var toTime = new Date($('#toDate').val()).getTime();
//					
//					//getting date from json file
//					var date = new Date(val.collectionDate);
//					
//					//check whether date is true from selected date
//					if (date.getTime() >= fromTime && date.getTime() <= toTime) {
//						
//						collectionTable.row.add([ val.collectionDate, val.collectionMode,  val.payNow ]).draw();
//							$("#custName").on("change", function() {
//								collectionTable.clear().draw();
//							});
//							$('#go').on('click', function() {
//								collectionTable.clear().draw();
//							});
//							collection = collection + val.payNow;
//					}
//				}
//			});
//			var weekCollection = collection;
//			$('#collection').val(weekCollection);
//			collectionReceived.val(weekCollection);
//			collectionCalc =  totalAmount.val() - weekCollection;
//			
//			$('#closingBal').val(collectionCalc);
//			collection = 0;
//		}
//
//	});
//});
	
	
	$("#optionsRadios1, #optionsRadios2").change(function() {
		$("#addLess").val(null);
		$('#closingBal').val(collectionCalc);
	});
	
	$("#addLess").on('input', function() {
		var radio = $("input[name='addLess']:checked").val();
		
		var total = 0;
		if(radio == "add"){
			total = collectionCalc + parseInt($("#addLess").val());
		}else if(radio == "less"){
			total = collectionCalc - parseInt($("#addLess").val());
		}
			
		
		if (total >= 0) {

			var bal = parseInt($('#closingBal').val(total));
		} else {
			$('#closingBal').val(collectionCalc);
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
				"custName" : $('#custName').val(),
				"dateAccOp" : $('#dateAccOp').val(),
				"fromDate" : $('#fromDate').val(),
				"toDate" : $('#toDate').val(),
				"opBal" : $('#opeBal').val(),
				"totalKgs" : $('#kgs').val(),
				"totalPcs" : $('#pcs').val(),
				"weekCollection" : $('#collection').val(),
				"weekSales" : $('#weekSales').val(),
				"totalAmount" : $('#totalAmount').val(),
				"collectionReceived" : $('#collectionReceived').val(),
				"addLess" : $('#addLess').val(),
				"closingBal" : $('#closingBal').val()
				
		}
		custLedgerArray.push(LedgerData);

		var custLedgerJson = '{data:' + JSON.stringify(custLedgerArray) + '}';
		
		$('#custLedgerJson').val(custLedgerJson);
		
		console.log("custLedgerJson---" + custLedgerJson);
		
		$('#CustLedgerForm').submit(function(e) {

			$.ajax({
				url : 'CustLedgerServlet',
				type : 'post',
				data : $('#CustLedgerForm').serialize(),

				success : function(data) {
					console.log('success');
				},
				error : function() {

				}
			})
		});
	});


});// end of function
