$(function() {

	// Sets van,date and purchaseId from salesTable.json
	$.getJSON('/server/jsonfiles/salesTable.json', function(data) {
		var jsonData = data['data'];
		var purchaseId;
		var purchaseIdArray = [];
		$.each(jsonData, function(key, val) {
			var van = val.vanName;
			var date = val.date;
			purchaseId = val.purchaseId;

			$('#vanList').val(van);
			$('#purchaseDate').val(date);

			purchaseIdArray.push(purchaseId);
		});

		$('#purchaseId').val(purchaseIdArray);
	});

	
	
	var totalPcs = 0;
	$('#getTotalPieces').on('click', function(e) {
		e.preventDefault();
		$.getJSON('/server/jsonfiles/salesTable.json',function(data){
			var jsonData = data['data'];
			//calculate total pieces 
			$.each(jsonData, function(key, val) {
				
				totalPcs = totalPcs + val.pieces;
				
			});
			var showPcs = totalPcs;
			totalPcs = 0;
			console.log(showPcs);
			$('#totalPieces').val(showPcs);
			
			//calculate total eggs
			$.each(jsonData, function(key, val) {
				if(val.product == "eggs")
					totalPcs = totalPcs + val.pieces;
			});
			var showPcs = totalPcs;
			totalPcs = 0;
			console.log(showPcs);
			$('#eggs').val(showPcs);
			
			//calculate total chickens
			$.each(jsonData, function(key, val) {
				if(val.product == "chickens")
					totalPcs = totalPcs + val.pieces;
			});
			var showPcs = totalPcs;
			totalPcs = 0;
			console.log(showPcs);
			$('#chicken').val(showPcs);
			
			//calculate total broiler
			$.each(jsonData, function(key, val) {
				if(val.product == "Broiler")
					totalPcs = totalPcs + val.pieces;
			});
			var showPcs = totalPcs;
			totalPcs = 0;
			console.log(showPcs);
			$('#broiler').val(showPcs);
		});
	});
	
	//balance and sales quantity
	 var selectItemTable = $('#salesTable').DataTable();
	 
	 $('#salesTable tbody').on('click', function(){
		 var selectItemData = salesReady.row(this).data().purchaseId;
		 console.log(selectItemData);
	 });
	
	// Calculates the total of All pieces in the salesTable
//	$('#getTotalPieces').on('click', function(e) {
//		e.preventDefault();
//		
//		$.getJSON('/server/jsonfiles/salesTable.json',function(data){
//			var table = $('#salesTable').DataTable();
//			var salesTable = $('#salesTable').dataTable();
//			var jsonData = data['data'];
//			var product;
//			var pieces;
//			var productArray = [];
//			var piecesArray = [];
//
//			//Fetch pieces and products from JSON File
//			for(var i=0;i<jsonData.length;i++){
//				 pieces = jsonData[i].pieces;
//				 product = jsonData[i].product;
//				 piecesArray.push(pieces);
//				 productArray.push(product);
//			}
//			
//			//Calculates the total number of pieces in whole table
//			 var total = 0;
//			 for(var i=0;i<piecesArray.length;i++){
//				 total = total + piecesArray[i];
//			 }
//						
//			 $('#totalPieces').val(total);
//			console.log('Product Array :'+ productArray[1]);
//			console.log('Pieces Array :'+ piecesArray);
//			
//			for(var i=0;i<productArray.length;i++){
//				
//				var productList = {
//						'eggs' : productArray[i] === 'eggs',
//						'chickens' : productArray[i] === 'chickens',
//						'broiler' : productArray[i] === 'Broiler'
//				}
//				if(productArray[i] === 'chickens'){
//					console.log('I am CHICKEN');
//					var chickensPieces = table.row().data().pieces;
//					console.log(chickensPieces);
//				}
//				if(productArray[i] === 'eggs'){
//					console.log('I am EGG');
//					var eggsPieces = table.row().data().pieces;
//					console.log(eggsPieces);
//				}
//				
////				var productLength = {
////						'eggs' :  $.inArray('eggs',productArray),
////						'chickens' :  $.inArray('chickens',productArray),
////						'broiler' :  $.inArray('Broiler',productArray)
////				}
////				var count = table.rows().column(12).data().filter( function (value, index) {
////	                  return value='chickens' ? true : false;
////	            });
////				
////				console.log('count : '+count);
////				console.log('c length :' +count.length);
//				var rowCount = table.rows()[0].length;
////				for (var row=0;row<rowCount;row++) {
////				    if (table.cells(row, 12).data().indexOf('Broiler')) {
////				   console.log(table.cells(row, 12).data().indexOf('Broiler'));
////				    }
////				}
////				console.log('countValue :' +countValue);
//				
//				if(productList.eggs){
//					console.log('Yes,There are Eggs');
//					var eggsRowId = salesTable.fnFindCellRowIndexes('eggs', 12);
//					var eggsPieces = table.row().data().pieces;
//					var total = 0;	
//					for(var i=0;i<productArray.length;i++){
//						total = total + eggsPieces;
//						console.log('total :' +total);
//						$('#eggs').val(total);
//						console.log('eggsPieces :' + eggsPieces);
//					}
//					console.log('eggsPieces :' + eggsPieces);
//				}
//				if(productList.chickens){
//					console.log('Yes,There are chickens');
//					var chickensRowId = salesTable.fnFindCellRowIndexes('chickens', 12);
//					var chickensPieces = table.row(chickensRowId).data().pieces;			
//					var total = 0;
//					for(var i=0;i<productArray.length;i++){
//						total = total + chickensPieces;
//						console.log('total :' +total);
//						$('#chicken').val(total);
//						console.log('chickensPieces :' + chickensPieces);
//					}
//					
//				}
//				if(productList.broiler){
//					console.log('Yes,There are Broiler');
//					var broilerRowId = salesTable.fnFindCellRowIndexes('Broiler', 12);
//					var broilerPieces = table.row(broilerRowId).data().pieces;
//					$('#broiler').val(broilerPieces);
//					console.log('broilerPieces :' + broilerPieces);
//				}
//			}
//		});
//		
////		if(eggsRowId){
//
////		}
////		var prod = table.row(broilerRowId).data().product;
////		console.log('Product is :'+prod);
////		var tableData = salesTable.fnGetData();
////		var piecesArray = [];
////		var total = 0;
////		var product;
////		var productArray = [];
//
//		 $.getJSON('/server/jsonfiles/salesTable.json',function(data){
//		 var jsonData = data['data'];
//		 var pieces;
//		 var piecesArray = [];
//		 
//		 //Gets each pieces from salesTable.json
//		 $.each(jsonData,function(key,val){
//		 var pieces = val.pieces;
//		 piecesArray.push(pieces);
//						
//		 });
//		 });
//	});

	// Initialize salesReadyTable
	$('#salesReadyTable').DataTable();

	// store elements into variables
	var invoiceNo = $('#invoiceNo');
	var salesKg = $('#salesKg');
	var rate = $('#salesRate');
	var salesAmount = $('#salesAmount');
	var salesAvgWeight = $('#salesAvgWeight');
	var pid;
	var salesProduct;
	var salesRate;

	// gets product value from salesTable.json
	$.getJSON('/server/jsonfiles/salesTable.json', function(data) {
		var jsonData = data['data'];
		$.each(jsonData, function(key, val) {
			$('#salesProductSelect').append(
					'<option value="' + val.product + '">' + val.product
							+ '</option>');
		});
	});

	$.getJSON('/server/jsonfiles/customer.json', function(data) {
		var jsonData = data['data'];
		$.each(jsonData, function(key, val) {
			$('#customerSelect').append(
					'<option value="' + val.fname + '">' + val.fname
							+ '</option>');
		});
	});

	// Invokes the onclick listener on salesReady Table
	$('#salesTable tbody').on('click','tr',function() {

						// selects all the data of purchase table into salesData variable
						var salesData = salesReady.row(this).data();

						//sets invoiceNo from database
						$.getJSON('/server/jsonfiles/salesTable.json',function(data){
							var jsonData = data['data'];
							var invoice;
							$.each(jsonData,function(key,val){
								invoice = val.invoiceNo;
							});
							console.log(invoice);
							invoiceNo.val(invoice + 1);
						});

						// sets the value to the variable from salesData
						pid = salesData.purchaseId;
						salesProduct = salesData.product;
						salesPieces = salesData.pieces;
						salesKg = salesData.kg;
						salesRate = salesData.rate;
						
						$('#salesRate').val(salesRate);

						// on entering the value checks for the certain
						// operations
						$('#salesReadyTable tbody tr td').keydown(function(e) {

											var salesPiecesNew = $('#salesPieces').val();
											var salesKgNew;

											// Determines the balance KG
											// Quantity amount and sales KG
											// Quantity
											$('#salesKg').on('input',function() {

																salesKgNew = $('#salesKg').val();
																var BalanceKg = (parseInt(salesKg) - parseInt(salesKgNew));
																$('#balanceQtyKg').attr('value',BalanceKg);
																$('#salesQtyKg').attr('value',salesKgNew);

															});

											// Determines the balance KG
											// Quantity amount and
											// sales KG Quantity
											$('#salesPieces').on('input',function() {

																var salesPiecesNew = $('#salesPieces').val();

																var BalancePieces = (parseInt(salesPieces) - parseInt(salesPiecesNew));
																$('#balanceQtyPieces').attr('value',BalancePieces);
																$('#salesQtyPieces').attr('value',salesPiecesNew);

															});
											if (e.keyCode === 13) {

												// checks against existing
												// pieces and disables
												// next input
												if (salesPiecesNew > salesPieces) {
													alert('pieces quantity exceeds');
													$('#salesKg').attr(
															'disabled',
															'disabled');
												}

												// checks against existing kg
												// and disables next
												// input
												if (salesKgNew > salesKg) {
													alert('Kg quantity exceeds');
													$('#salesRate').attr(
															'disabled',
															'disabled');
												}

											}

											// sets the amount and average
											// weight value
											var newAmt = parseInt(salesRate)
													* parseInt(salesKg);
											var newAvgWeight = parseInt(salesKg)
													/ parseInt(salesPiecesNew);
											// console.log(newAmt);
											if (newAmt !== null
													|| newAvgWeight !== null) {
												salesAmount.val(newAmt);
												salesAvgWeight
														.val(newAvgWeight);

											}

										});

					});

	// array to store all product to be sell
	var productRowData = [];

	$('#salesReadyTable tbody tr td').keydown(
			function(e) {

				var invoiceNo = $('#invoiceNo');
				var customer = $("#customerSelect");
				var product = $('#salesProductSelect');
				var salesPieces = $('#salesPieces');
				var salesKg = $('#salesKg');
				var salesRate = $('#salesRate');
				var salesAmount = $('#salesAmount');
				var salesAvgWeight = $('#salesAvgWeight');
				var purchaseId = $('#salesTable').DataTable().row(this).data().purchaseId;
				console.log('pid :' + purchaseId);
				var productRow = {
					"InvoiceNo" : invoiceNo.val(),
					"customer" : customer.val(),
					"product" : product.val(),
					"salesPieces" : salesPieces.val(),
					"salesKg" : salesKg.val(),
					"salesRate" : salesRate.val(),
					"salesAmount" : salesAmount.val(),
					"salesAvgWeight" : salesAvgWeight.val(),
					"purchaseId" : purchaseId
				}

				if (e.keyCode === 13) {

					productRowData.push(productRow);

					salesReadyTable.row.add(
							[ invoiceNo.val(), customer.val(), product.val(),
									salesPieces.val(), salesKg.val(),
									salesRate.val(), salesAmount.val(),
									salesAvgWeight.val() ]).draw();

					var productJson = '{salesData:'
							+ JSON.stringify(productRowData) + '}';
					$('#productJson').val(productJson);

					console.log('productJson' + productJson);
				}
			});

	// ajaxCall to purchaseServlet
	$('#insertBtn').on('click', function() {

		$('#SalesForm').submit(function(e) {

			$.ajax({
				url : 'SalesServlet',
				type : 'POST',
				data : $('#SalesForm').serializeArray(),
				async : false,
				success : function(data) {
					if (data != null) {
						alert(data);
					}
				},
				error : function(xhr, ajaxOptions, thrownError) {
					console.log("Sales Form Error");
				}
			});
		});
	});

	// to prevent form submission on enter key press
	$(window).keydown(function(event) {
		if (event.keyCode == 13) {
			event.preventDefault();
			return false;
		}
	});

});