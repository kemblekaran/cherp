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
//		var custName = $("#custName").val();
		curOsTable.clear().draw();
		
		var totalOpeBal = 0;
		var totalOpeCollectionAmt = 0;
		var totalOpeDebitCreditAmt = 0;
		
		var totalSaleAmt = 0;
		var totalCollectionAmt = 0;
		var totalDebitCreditNoteAmt = 0;
				
		
				$.ajax({
					type : "POST",
					url : "/server/jsonfiles/customer.json",
					dataType : "json",
					select : true,
					success : function(data) {
						var customerData = data['data'];
		
						$.each(customerData, function(key, val) {
							
							var custName = val.fname +" "+ val.lname;
//							alert(custName);
							var curDate = $('#fromDate').datepicker('getDate');
//							alert(curDate+" curDate");
							var preDate = curDate.setDate(curDate.getDate() - 1);
							
							
							$.ajax({
								type : "POST",
								url : "/server/jsonfiles/salesView.json",
								dataType : "json",
								
								success : function(data) {
									var openingBalData = data['data'];
									var amount = 0;
									var opAmount = 0;
									var saleAmount = 0;
									$.each(openingBalData, function(key, val) {
//										alert(custName == val.customer);
										if (custName == val.customer) {
											
//											var curDate = $('#fromDate').datepicker('getDate');
////											alert(curDate+" curDate");
//											var preDate = curDate.setDate(curDate.getDate() - 1);

											var custDate = new Date(val.salesDate);
											if(custDate.getTime() <= preDate){
												opAmount = opAmount + val.amount;
//												alert(opAmount);
											}
//									      --------------------------------------------------------------
											
											var fromTime = new Date($('#fromDate').val()).getTime();
											var toTime = new Date($('#toDate').val()).getTime();
											var date = new Date(val.salesDate);
											
											if (date.getTime() >= fromTime && date.getTime() <= toTime) {
												saleAmount = saleAmount + val.amount;
												
//												$("#cmpName").on("change", function() {
//													curOsTable.clear().draw();
//												});
												
												
											}
										}
									});
									
//									
									

											
//	-------------------------------------------------------------------------------------------------------------------------------------					
											
											$.ajax({
												type : "POST",
												url : "/server/jsonfiles/collection.json",
												dataType : "json",
												
												success : function(data) {
													var collectionData = data['data'];
													var amount = 0;
													var opCollectionAmount = 0;
													var collectionAmount = 0;
													$.each(collectionData, function(key, val) {
//														alert(custName == val.customer);
														if (custName == val.customer) {
															
//															var curDate = $('#fromDate').datepicker('getDate');
////															alert(curDate+" curDate");
//															var preDate = curDate.setDate(curDate.getDate() - 1);

															var custDate = new Date(val.collectionDate);
															if(custDate.getTime() <= preDate){
																opCollectionAmount = opCollectionAmount + val.payNow;
//																alert(opAmount);
															}
															
															
//															--------------------------------------------------------------------------
															var fromTime = new Date($('#fromDate').val()).getTime();
															var toTime = new Date($('#toDate').val()).getTime();
															var date = new Date(val.collectionDate);
															
															if (date.getTime() >= fromTime && date.getTime() <= toTime) {
																collectionAmount = collectionAmount + val.payNow;
																
//																$("#cmpName").on("change", function() {
//																	curOsTable.clear().draw();
//																});
																$('#go').on('click', function() {
																	curOsTable.clear().draw();
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
//																alert(custName == val.customer);
																if (custName == val.selectCustCmp) {
																	
																	
//																	var curDate = $('#fromDate').datepicker('getDate');
////																	alert(curDate+" curDate");
//																	var preDate = curDate.setDate(curDate.getDate() - 1);

																	var custDate = new Date(val.debitCreditDate);
																	if(custDate.getTime() <= preDate){
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
//																			curOsTable.clear().draw();
//																		});
																		$('#go').on('click', function() {
																			curOsTable.clear().draw();
																		});
																		
																	}
																	
																}
															});
													
															//for calculate total debitCreditNoteAmt
															totalDebitCreditNoteAmt = drCrNoteAmount;
															drCrNoteAmount = 0;
//															alert(totalDebitCreditNoteAmt + " debitcredit amt");
																	
																
															//for calculate total collection
															totalCollectionAmt = collectionAmount;
															collectionAmount = 0;
//															alert(totalCollectionAmt + " collection amt");
															
															
															//for calculate total sale
															totalSaleAmt = saleAmount;
															saleAmount = 0; 
															
//															---------------------------------------------------------
															//for calculate opening balance
															totalOpeCollectionAmt = opCollectionAmount;
															opCollectionAmount = 0;
//															alert(totalOpeCollectionAmt + "totalOpeCollectionAmt ");
															
															totalOpeDebitCreditAmt = opDebitCreditAmount;
															opDebitCreditAmount = 0;
															
															totalOpeBal = opAmount;
															opAmount = 0; 
															
															var openingBalance = (totalOpeBal - (totalOpeCollectionAmt + totalOpeDebitCreditAmt));
				//											alert(totalSaleAmt + " total sale");
//															alert(openingBalance + " openingBalance ");
															var closingBalance = ((openingBalance + totalSaleAmt) - (totalCollectionAmt + totalDebitCreditNoteAmt));
															
															//value added to the table
															curOsTable.row.add([ val.id, val.fname +" "+ val.lname, val.area,
																openingBalance.toFixed(2), totalSaleAmt.toFixed(2),
																totalCollectionAmt.toFixed(2), totalDebitCreditNoteAmt.toFixed(2), 
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

	
	
	
	
	
	
	
	
	
//	if (cmpName == val.company) {
//	//getting from and to date from input
//	var fromTime = new Date($('#fromDate').val()).getTime();
//	var toTime = new Date($('#toDate').val()).getTime();
//	
//	//getting date from json file
////	var myDate = '15/07/2011';
////	var chunks = myDate.split('/');
////
////	var formattedDate = chunks[1]+'/'+chunks[0]+'/'+chunks[2];
////	var newdate = new Date(formattedDate);
////	alert(newdate + "formated date");
//	var date = new Date(val.date);
//	//check whether date is true from selected date
//	if (date.getTime() >= fromTime && date.getTime() <= toTime) {
//		
//		purchaseTable.row.add([ val.date, val.purchaseId, val.product, val.pieces, val.kg, val.rate, val.amount ]).draw();
//			$("#cmpName").on("change", function() {
//				purchaseTable.clear().draw();
//			});
//			$('#go').on('click', function() {
//				purchaseTable.clear().draw();
//			});
//			kgs = kgs + val.kg;
//			pieces = pieces + val.pieces;
//			amount = amount + val.amount;
//	}
//}
	
	
	
	
});