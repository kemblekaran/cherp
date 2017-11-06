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
		var purchaseTotalAmt = 0;
		
		$.ajax({
			type : "POST",
			url : "/server/jsonfiles/salesView.json",
			dataType : "json",
			
			success : function(data) {
				var salesData = data['data'];
				var amount = 0;
				var saleAmount = 0;
				$.each(salesData, function(key, val) {

					if (custName == val.company) {
						//getting from and to date from input
						var curDate = $('#fromDate').datepicker('getDate');
						var preDate = curDate.setDate(curDate.getDate() - 1);

						var custDate = new Date(val.date);
						if(custDate.getTime() <= preDate){
							saleAmount = saleAmount + val.amount;
						}
						
//						--------------------------------------------------------------------------
						var fromTime = new Date($('#fromDate').val()).getTime();
						var toTime = new Date($('#toDate').val()).getTime();
						var date = new Date(val.date);
						
						if (date.getTime() >= fromTime
								&& date.getTime() <= toTime) {

							curOsTable.row.add(
									[ val.date, val.purchaseId,
											val.product, val.pieces,
											val.kg, val.rate,
											val.amount ]).draw();
							$("#cmpName").on("change", function() {
								curOsTable.clear().draw();
							});
							$('#go').on('click', function() {
								curOsTable.clear().draw();
							});
							
						}
						
					}
				});
				salesTotalAmt = saleAmount;
				purAmount = 0; 
				alert(salesTotalAmt);
				
				
				
		
				$.ajax({
					type : "POST",
					url : "/server/jsonfiles/customer.json",
					dataType : "json",
					select : true,
					success : function(data) {
						var customerData = data['data'];
		
						$.each(customerData, function(key, val) {
							curOsTable.row.add([ val.id, val.fname + val.lname, val.area, val.opBal, val.opBal, val.opBal, val.opBal, val.opBal ]).draw();
							$('#go').on('click', function() {
								curOsTable.clear().draw();
							});
		
						});
						
					}
		
				});
			}
		});
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