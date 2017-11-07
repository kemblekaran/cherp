$(document).ready(function() {
	$("#fromDate").datepicker({
		// dateFormat: 'dd/mm/yy',
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
		// dateFormat: 'dd/mm/yy',
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

	
	$('#go').on('click', function(e) {
//		alert('hey');
		event.preventDefault();
//		var cmpName = $("#cmpName").val();
		cmpOsTable.clear().draw();
		
		var totalOpeBal = 0;
		var totalOpePaymentAmt = 0;
		var totalOpeDebitCreditAmt = 0;
		
		var totalPurchaseAmt = 0;
		var totalPaymentAmt = 0;
		var totalDebitCreditNoteAmt = 0;
				
		
				$.ajax({
					type : "POST",
					url : "/server/jsonfiles/company.json",
					dataType : "json",
					select : true,
					success : function(data) {
						var companyData = data['data'];
		
						$.each(companyData, function(key, val) {
							
							var cmpName = val.name;
//							alert(cmpName);
							var curDate = $('#fromDate').datepicker('getDate');
//							alert(curDate+" curDate");
							var preDate = curDate.setDate(curDate.getDate() - 1);
							
							
							$.ajax({
								type : "POST",
								url : "/server/jsonfiles/purchaseView.json",
								dataType : "json",
								
								success : function(data) {
									var openingBalData = data['data'];
									var amount = 0;
									var opAmount = 0;
									var purchaseAmount = 0;
									$.each(openingBalData, function(key, val) {
//										alert(cmpName == val.customer);
										if (cmpName == val.company) {
											
//											var curDate = $('#fromDate').datepicker('getDate');
////											alert(curDate+" curDate");
//											var preDate = curDate.setDate(curDate.getDate() - 1);

											var cmpDate = new Date(val.date);
											if(cmpDate.getTime() <= preDate){
												opAmount = opAmount + val.amount;
//												alert(opAmount);
											}
//									      --------------------------------------------------------------
											
											var fromTime = new Date($('#fromDate').val()).getTime();
											var toTime = new Date($('#toDate').val()).getTime();
											var date = new Date(val.date);
											
											if (date.getTime() >= fromTime && date.getTime() <= toTime) {
												purchaseAmount = purchaseAmount + val.amount;
												
//												$("#cmpName").on("change", function() {
//													cmpOsTable.clear().draw();
//												});
												
												
											}
										}
									});
									
//									
									

											
//	-------------------------------------------------------------------------------------------------------------------------------------					
											
											$.ajax({
												type : "POST",
												url : "/server/jsonfiles/payment.json",
												dataType : "json",
												
												success : function(data) {
													var paymentData = data['data'];
													var amount = 0;
													var opPaymentAmount = 0;
													var paymentAmount = 0;
													$.each(paymentData, function(key, val) {
//														alert(cmpName == val.customer);
														if (cmpName == val.company) {
															
//															var curDate = $('#fromDate').datepicker('getDate');
////															alert(curDate+" curDate");
//															var preDate = curDate.setDate(curDate.getDate() - 1);

															var cmpDate = new Date(val.paymentDate);
															if(cmpDate.getTime() <= preDate){
																opPaymentAmount = opPaymentAmount + val.payNow;
//																alert(opAmount);
															}
															
															
//															--------------------------------------------------------------------------
															var fromTime = new Date($('#fromDate').val()).getTime();
															var toTime = new Date($('#toDate').val()).getTime();
															var date = new Date(val.paymentDate);
															
															if (date.getTime() >= fromTime && date.getTime() <= toTime) {
																paymentAmount = paymentAmount + val.payNow;
																
//																$("#cmpName").on("change", function() {
//																	cmpOsTable.clear().draw();
//																});
																$('#go').on('click', function() {
																	cmpOsTable.clear().draw();
																});
																
															}
															
														}
													});
													
													
//--------------------------------------------------------------------------------------------------------------------------------------									
													
													$.ajax({
														type : "POST",
														url : "/server/jsonfiles/debitcreditnote.json",
														dataType : "json",
														
														success : function(data) {
															var debitCreditNoteData = data['data'];
															var amount = 0;
															var drCrNoteAmount = 0;
															var opDebitCreditAmount = 0;
															$.each(debitCreditNoteData, function(key, val) {
//																alert(cmpName == val.customer);
																if (cmpName == val.selectCustCmp) {
																	
																	
//																	var curDate = $('#fromDate').datepicker('getDate');
////																	alert(curDate+" curDate");
//																	var preDate = curDate.setDate(curDate.getDate() - 1);

																	var cmpDate = new Date(val.debitCreditDate);
																	if(cmpDate.getTime() <= preDate){
																		opDebitCreditAmount = opDebitCreditAmount + val.amount;
//																		alert(opAmount);
																	}
																	
//																	--------------------------------------------------------------------------
																	var fromTime = new Date($('#fromDate').val()).getTime();
																	var toTime = new Date($('#toDate').val()).getTime();
																	var date = new Date(val.debitCreditDate);
																	
																	if (date.getTime() >= fromTime && date.getTime() <= toTime) {
																		drCrNoteAmount = drCrNoteAmount + val.amount;
																		
//																		$("#cmpName").on("change", function() {
//																			cmpOsTable.clear().draw();
//																		});
																		$('#go').on('click', function() {
																			cmpOsTable.clear().draw();
																		});
																		
																	}
																	
																}
															});
													
															//for calculate total debitCreditNoteAmt
															totalDebitCreditNoteAmt = drCrNoteAmount;
															drCrNoteAmount = 0;
//															alert(totalDebitCreditNoteAmt + " debitcredit amt");
																	
																
															//for calculate total collection
															totalPaymentAmt = paymentAmount;
															paymentAmount = 0;
//															alert(totalPaymentAmt + " collection amt");
															
															
															//for calculate total sale
															totalPurchaseAmt = purchaseAmount;
															purchaseAmount = 0; 
															
//															---------------------------------------------------------
															//for calculate opening balance
															totalOpePaymentAmt = opPaymentAmount;
															opPaymentAmount = 0;
//															alert(totalOpePaymentAmt + "totalOpePaymentAmt ");
															
															totalOpeDebitCreditAmt = opDebitCreditAmount;
															opDebitCreditAmount = 0;
															
															totalOpeBal = opAmount;
															opAmount = 0; 
															
															var openingBalance = (totalOpeBal - (totalOpePaymentAmt + totalOpeDebitCreditAmt));
				//											alert(totalPurchaseAmt + " total sale");
//															alert(openingBalance + " openingBalance ");
															var closingBalance = ((openingBalance + totalPurchaseAmt) - (totalPaymentAmt + totalDebitCreditNoteAmt));
															
															//value added to the table
															cmpOsTable.row.add([ val.id, val.name, val.city,
																openingBalance.toFixed(2), totalPurchaseAmt.toFixed(2),
																totalPaymentAmt.toFixed(2), totalDebitCreditNoteAmt.toFixed(2), 
																closingBalance.toFixed(2) ]).draw();
											
															
											
														}
													});//fourth ajax method close
											
												}
											});//third ajax method close
						
							
								}
							});//second ajax method close
							
							
		
						});
						
					}
				});//first ajax method close
			
	});
	
	
});