$(function() {
	
	var driverName1 = $("#driverName1");
	var driverName2 =$("#driverName2");
	var purchaseNo = $("#purchaseNo");
	var cleanerName1 = $("#cleanerName1");
	var cleanerName2 = $("#cleanerName2");
	var purchaseAmt = $("#purchaseAmt");
	var rent = $("#rent");
	var totalPur = $("#totalPurchase");
	
	var purchaseAmt = $("#purchaseAmt");
	var rent = $("#rent");
	var totalPurchase = 0;
	
	var selectedDate;
	var selectedVan;
	
	$.getJSON('/server/jsonfiles/purchaseLoader.json', function(data) {

		var jsonData = data['van'];
		$.each(jsonData, function(key, val) {
			$('#vanNo').append(
					'<option value="' + val.name + '">' + val.name
							+ '</option>');
		});
	});

	var date = $('#date');
	var van = $('#vanNo');

	// set current date to payment using datepicker
	$(function() {
		$("#date").datepicker({
//			dateFormat : 'dd/mm/yy',
			showAnim : 'drop',
			showButtonPanel : true,
		}).datepicker('setDate', 'today');
	});

	
	$("#vanNo, #date").on('change', function() {
		
		selectedDate = $("#date").val();
		selectedVan = $("#vanNo").val();
		
		purchaseVanTable.clear().draw();
		driverName1.val(null);
		driverName2.val(null);
		purchaseNo.val(null);
		cleanerName1.val(null);
		cleanerName2.val(null);
		purchaseAmt.val(null);
		rent.val(null);
		totalPur.val(null);
	});
	
	
//	$("#Show").on('mouseenter', function() {
//		console.log(date.val() + van.val());
//		$.ajax({
////			async : false,
//			url : 'PurchaseServlet',
//			type : 'POST',
//			data : {
//				'date' : date.val(),
//				'vanName' : van.val(),
//				vanWiseSales : true
//			},
//			
//			
//			success : function() {
//				console.log('successfully sent data to the server');
//			
//				
//			},
//			error : function() {
//				console.log('error occurred');
//			}
//		});	
//	});
	
		$("#Show").on('click', function() {
			purchaseVanTable.clear().draw();
			salesVanTable.clear().draw();
//			purchase van table
			$.getJSON('/server/jsonfiles/purchaseView.json', function(data) {
				var vanWiseData = data['data'];
				var purchaseNoArray = [];
				
				$.each(vanWiseData, function(key, val) {
					if(selectedDate == val.date && selectedVan == val.vanName){
						
							driverName1.val(val.driver1);
							driverName2.val(val.driver2);
							purchaseNoArray.push(val.purchaseId);
							cleanerName1.val(val.cleaner1);
							cleanerName2.val(val.cleaner2);
							purchaseAmt.val(val.finalAmount);
							rent.val(val.rent);
							purchaseVanTable.row.add(
									[ val.date, val.company, val.product, val.pieces, val.kg, val.rate, val.amount, val.avgWeight,val.avgWeight,val.avgWeight, val.avgWeight  ]).draw();
							
							$("#Show").on('click', function() {
								purchaseVanTable.clear().draw();
								driverName1.val(null);
								driverName2.val(null);
								purchaseNo.val(null);
								cleanerName1.val(null);
								cleanerName2.val(null);
								purchaseAmt.val(null);
								rent.val(null);
								totalPur.val(null);
							});
					}
					
				});
				//purchase no array
				$("#purchaseNo").val(purchaseNoArray);
				
	//			$("#rent").on('input', function() {
					totalPurchase = parseInt(purchaseAmt.val()) + parseInt($("#rent").val());
					if (totalPurchase >= 0) {
	
						parseInt($("#totalPurchase").val(totalPurchase));
					} else {
						$("#totalPurchase").val(purchaseAmt.val());
						$("#rent").val(null);
						
					}
	//			});
				
			});
			var totalPieces = 0;
			var totalKgs = 0;
			var totalAmount = 0;
			//sales van table
			$.getJSON('/server/jsonfiles/salesView.json', function(data) {
				var vanWiseData = data['data'];
				var purchaseNoArray = [];
				
				$.each(vanWiseData, function(key, val) {
					if(selectedDate == val.salesDate && selectedVan == val.van){
						
							salesVanTable.row.add(
									[ val.salesDate, val.invoiceNo, val.customer, val.product, val.pieces, val.kg, val.rate, val.avgWeight,
										val.amount ]).draw();
							
							$("#Show").on('click', function() {
								salesVanTable.clear().draw();
								
							});
							
							totalPieces = totalPieces + val.pieces;
							totalKgs = totalKgs + val.kg;
							totalAmount = totalAmount + val.amount;
							
					}
					
				});
				
				var pieces = totalPieces;
				var kgs = totalKgs;
				var amount = totalAmount;
				
				totalPieces = 0 ;
				totalKgs = 0;
				totalAmount = 0;
				
				$('#totalPcs').val(pieces);
				$('#totalKgs').val(kgs);
				$('#totalAmt').val(amount);
			});
		});

	

});