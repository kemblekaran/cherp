$(function() {

	var purchaseTable = $('#purchaseTable').DataTable();
	var paymentTable = $('#paymentTable').DataTable();
	var debitCreditTable = $('#debitCreditTable').DataTable();
	
	var cmpLedgerArray = [];
	var kgs = 0;
	var pieces = 0;
	var amount = 0;
	var payment = 0;
	var weekPurchase = $("#weekPurchase");
	var totalAmount = $("#totalAmount");
	var paymentGiven = $("#paymentGiven") ;
	var closingBal = $("#closingBal") ;
	var paymentCalc = 0 ;
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
		var toDate = $('#toDate').val();
		$('#opeBal').val(null);
		$("#pcs").val(null);
		$("#kgs").val(null);
		$("#payment").val(null);
		$("#weekPurchase").val(null);
		$("#totalAmount").val(null);
		$("#paymentGiven").val(null);
		$("#addLess").val(null);
		$("#closingBal").val(null);
		

	});

	
	
	$('#go').on('click', function(e) {

		event.preventDefault();
		$('#opeBal').val(null);
		$("#pcs").val(null);
		$("#kgs").val(null);
		$("#payment").val(null);
		$("#weekPurchase").val(null);
		$("#totalAmount").val(null);
		$("#paymentGiven").val(null);
		$("#addLess").val(null);
		$("#closingBal").val(null);
		
		var cmpName = $("#cmpName").val();
		
		var purchaseTotalAmt = 0;
		var paymentTotalAmt = 0;
		var debitCreditNoteAmt = 0;
	

		$.ajax({
			type : "POST",
			url : "/server/jsonfiles/purchaseView.json",
			dataType : "json",
			
			success : function(data) {
				var purchaseData = data['data'];
				var amount = 0;
				var purAmount = 0;
				$.each(purchaseData, function(key, val) {

					if (cmpName == val.company) {
						//getting from and to date from input
						var curDate = $('#fromDate').datepicker('getDate');
						var preDate = curDate.setDate(curDate.getDate() - 1);

						var cmpDate = new Date(val.date);
						if(cmpDate.getTime() <= preDate){
							purAmount = purAmount + val.amount;
						}
						
//						--------------------------------------------------------------------------
						var fromTime = new Date($('#fromDate').val()).getTime();
						var toTime = new Date($('#toDate').val()).getTime();
						var date = new Date(val.date);
						
						if (date.getTime() >= fromTime
								&& date.getTime() <= toTime) {

							purchaseTable.row.add(
									[ val.date, val.purchaseId,
											val.product, val.pieces,
											val.kg, val.rate,
											val.amount ]).draw();
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
				purchaseTotalAmt = purAmount;
				purAmount = 0; 
				
				var totalKgs = kgs;
				var totalPcs = pieces;
				var totalAmt = amount;
				kgs = 0;
				pieces = 0;
				amount = 0;
				$('#kgs').val(totalKgs.toFixed(2));
				$('#pcs').val(totalPcs);
				weekPurchase.val(totalAmt.toFixed(2));
				
				//get payment total amount
				$.ajax({
					type : "POST",
					url : "/server/jsonfiles/payment.json",
					dataType : "json",
					
					success : function(data) {
						var paymentData = data['data'];
						var amount = 0;
						$.each(paymentData, function(key, val) {

							if (cmpName == val.company) {
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
								var date = new Date(val.paymentDate);
								
								//check whether date is true from selected date
								if (date.getTime() >= fromTime && date.getTime() <= toTime) {
									
									paymentTable.row.add([ val.paymentDate, val.paymentMode,  val.payNow ]).draw();
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
						paymentTotalAmt = amount;
						amount = 0;
						var weekPayment = payment;
						$('#payment').val(weekPayment.toFixed(2));
						paymentGiven.val(weekPayment.toFixed(2));
						
						payment = 0;
						
						
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
									
									if (cmpName == val.selectCustCmp) {
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
											
											paymentTable.row.add([ val.debitCreditDate, val.remarks,  val.amount ]).draw();
												$("#cmpName").on("change", function() {
													paymentTable.clear().draw();
												});
												$('#go').on('click', function() {
													paymentTable.clear().draw();
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

								$('#opeBal').val((purchaseTotalAmt - (paymentTotalAmt + debitCreditNoteAmt)).toFixed(2));
//								alert(weekPurchase.val());
								totalAmount.val((parseFloat(weekPurchase.val()) + (purchaseTotalAmt - (paymentTotalAmt + debitCreditNoteAmt))).toFixed(2));
//								
								paymentCalc = totalAmount.val() - (weekPayment + debitCreditAmt);
//								alert(paymentCalc);
								$('#closingBal').val(paymentCalc.toFixed(2));
							}
						});//third ajax method close
						
						
					}
				});//second ajax method close
				
			}
		});//first ajax method close
		
	});//onclick go method
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//loader purchase data into table
	
//		$('#go').on('click', function() {
//			var cmpName = $("#cmpName").val();
//				$.ajax({
//					type : "POST",
//					url : "/server/jsonfiles/purchaseView.json",
//					dataType : "json",
//					select : true,
//					success : function(data) {
//						var purchaseData = data['data'];
//
//						$.each(purchaseData, function(key, val) {
//
//							if (cmpName == val.company) {
//								// getting from and to date from input
//								var fromTime = new Date($('#fromDate').val()).getTime();
//								var toTime = new Date($('#toDate').val()).getTime();
//
//								// getting date from json file
//								// var myDate = '15/07/2011';
//								// var chunks = myDate.split('/');
//								//
//								// var formattedDate =
//								// chunks[1]+'/'+chunks[0]+'/'+chunks[2];
//								// var newdate = new Date(formattedDate);
//								// alert(newdate + "formated date");
//								var date = new Date(val.date);
//								// check whether date is true from selected date
//								if (date.getTime() >= fromTime
//										&& date.getTime() <= toTime) {
//
//									purchaseTable.row.add(
//											[ val.date, val.purchaseId,
//													val.product, val.pieces,
//													val.kg, val.rate,
//													val.amount ]).draw();
//									$("#cmpName").on("change", function() {
//										purchaseTable.clear().draw();
//									});
//									$('#go').on('click', function() {
//										purchaseTable.clear().draw();
//									});
//									kgs = kgs + val.kg;
//									pieces = pieces + val.pieces;
//									amount = amount + val.amount;
//								}
//							}
//						});
//						var totalKgs = kgs;
//						var totalPcs = pieces;
//						var totalAmt = amount;
//						kgs = 0;
//						pieces = 0;
//						amount = 0;
//						$('#kgs').val(totalKgs);
//						$('#pcs').val(totalPcs);
//						weekPurchase.val(totalAmt);
////						totalAmount.val(totalAmt + parseFloat(opeBal.val()));
//						
//						
//						
//						$.ajax({
//							type : "POST",
//							url : "/server/jsonfiles/payment.json",
//							dataType : "json",
//							select : true,
//							success : function(data) {
//								var paymentData = data['data'];
//
//								$.each(paymentData, function(key, val) {
//
//									if (cmpName == val.company) {
//										//getting from and to date from input
//										var fromTime = new Date($('#fromDate').val()).getTime();
//										var toTime = new Date($('#toDate').val()).getTime();
//										
//										//getting date from json file
//										var date = new Date(val.paymentDate);
//										
//										//check whether date is true from selected date
//										if (date.getTime() >= fromTime && date.getTime() <= toTime) {
//											
//											paymentTable.row.add([ val.paymentDate, val.paymentMode,  val.payNow ]).draw();
//												$("#cmpName").on("change", function() {
//													paymentTable.clear().draw();
//												});
//												$('#go').on('click', function() {
//													paymentTable.clear().draw();
//												});
//												payment = payment + val.payNow;
//										}
//									}
//								});
//								var weekPayment = payment;
//								$('#payment').val(weekPayment);
//								paymentGiven.val(weekPayment);
//								paymentCalc = totalAmount.val() - weekPayment;
////								alert(paymentCalc);
//								$('#closingBal').val(paymentCalc);
//								payment = 0;
//							}
//
//						});
//					}
//
//				});
//	});
//	
		
		//loaded payment data into table

	
	$('#go').on('click', function(e) {
		event.preventDefault();
	var cmpName = $("#cmpName").val();

	
});
	$("#optionsRadios1, #optionsRadios2").change(function() {
		$("#addLess").val(null);
		$('#closingBal').val(paymentCalc);
	});
	
	$("#addLess").on('input', function() {
		var radio = $("input[name='addLess']:checked").val();
		
		var total = 0;
		if(radio == "add"){
			total = paymentCalc + parseFloat($("#addLess").val());
		}else if(radio == "less"){
			total = paymentCalc - parseFloat($("#addLess").val());
		}
			
		
		if (total >= 0) {

			var bal = parseFloat($('#closingBal').val(total));
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
