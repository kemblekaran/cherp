$(function() {

	console.log("ready!");
	$.ajax({
		url : 'SalesServlet',
		// async : false,
		data : {
			invoiceNoLoader : true
		},
		type : 'POST',
		success : function() {
			console.log('Invoice Number Loaded Successfully');
		},
		error : function() {
			console.log('Error Loading Invoice Number');

		}
	});

	$.getJSON('/server/jsonfiles/invoiceNo.json', function(data) {
		var jsonData = data['data'];
		var invoice;
		$.each(jsonData, function(key, val) {
			invoice = val.invoiceNo;
		});
		console.log(invoice);
		invoiceNo.val(invoice + 1);
	});

	$.getJSON('/server/jsonfiles/van.json', function(data) {
		var jsonDataProduct = data['data'];
		$.each(jsonDataProduct, function(key, val) {
			$('#van').append(
					'<option value="' + val.companyName + '">'
							+ val.companyName + '</option>');
		});
	});

	
	// Sets van,date and purchaseId from salesTable.json
	$.getJSON('/server/jsonfiles/purchaseView.json', function(data) {
	
	});

	
	// list data on van change
	$("#van").on(
			"change",
			function() {
				var companyName = $(this).val();
				$.ajax({
					type : "POST",
					url : "/server/jsonfiles/purchaseView.json",
					dataType : "json",
					select : true,
					success : function(data) {
						var jsonData = data['data'];

						$.each(jsonData, function(key, val) {
							if (companyName == val.vanName) {
								salesReady.row.add(
										[ val.purchaseId, val.date,
												val.company, val.location,
												val.product, val.pieces,
												val.kg, val.rate, val.amount,
												val.avgWeight,
												val.balancePieces,
												val.balanceKG ]).draw();

								$('#purchaseId').val(val.purchaseId);
								
								$("#van").on("change", function() {
									salesReady.clear().draw();
									$('#salesProduct').val(null);
								});

							}
						});// forEach method close

					}// success method close
				});// ajax method close
			});// onchange event close

//	var totalPcs = 0;
//	$('#getTotalPieces').on('click', function(e) {
//		e.preventDefault();
//		$.getJSON('/server/jsonfiles/salesTable.json', function(data) {
//			var jsonData = data['data'];
//			// calculate total pieces
//			$.each(jsonData, function(key, val) {
//
//				totalPcs = totalPcs + val.pieces;
//
//			});
//			var showPcs = totalPcs;
//			totalPcs = 0;
//			console.log(showPcs);
//			$('#totalPieces').val(showPcs);
//
//			// calculate total eggs
//			$.each(jsonData, function(key, val) {
//				if (val.product == "Eggs")
//					totalPcs = totalPcs + val.pieces;
//			});
//			var showPcs = totalPcs;
//			totalPcs = 0;
//			console.log(showPcs);
//			$('#eggs').val(showPcs);
//
//			// calculate total chickens
//			$.each(jsonData, function(key, val) {
//				if (val.product == "Chicken")
//					totalPcs = totalPcs + val.pieces;
//			});
//			var showPcs = totalPcs;
//			totalPcs = 0;
//			console.log(showPcs);
//			$('#chicken').val(showPcs);
//
//			// calculate total broiler
//			$.each(jsonData, function(key, val) {
//				if (val.product == "Broiler")
//					totalPcs = totalPcs + val.pieces;
//			});
//			var showPcs = totalPcs;
//			totalPcs = 0;
//			console.log(showPcs);
//			$('#broiler').val(showPcs);
//		});
//	});

	// balance and sales quantity
	var selectItemTable = $('#salesTable').DataTable();

	$('#salesTable tbody').on('click', 'tr', function() {
		var selectItemData = selectItemTable.row(this).data();
		 console.log('selected data '+selectItemData);
		 console.log('purchase Id '+ selectItemData[0])
		 $('#salesProduct').val(selectItemData[4]);
		// console.log('selected id '+JSON.stringify(selectItemData));
	});

	
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
	var company;

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
					'<option value="' + val.fname + " " + val.lname + '">'
							+ val.fname + " " + val.lname + '</option>');
		});
	});
	
	
	
	$("#salesPieces, #salesKg, #salesRate").on('input', function() {
		if($("#salesProduct").val() == ""){
			alert('Please select a row first');
			$("#salesPieces, #salesKg, #salesRate").val(null);
		}
	});

	// Invokes the onclick listener on salesReady Table
	$('#salesTable tbody')
			.on(
					'click',
					'tr',
					function() {

						// selects all the data of purchase table into salesData
						// variable
						var salesData = salesReady.row(this).data();

						// sets invoiceNo from database

						// sets the value to the variable from salesData
						pid = salesData.purchaseId;
						salesProduct = salesData.product;
						salesPieces = salesData.pieces;
						salesKg = salesData.kg;
						salesRate = salesData.rate;
						company = salesData.company;

						$('#salesRate').val(salesRate);
						$('#companyName').val(company);
						
						// on entering the value checks for the certain
						// operations
						$('#salesReadyTable tbody tr td')
								.keydown(
										function(e) {

											var salesPiecesNew = $(
													'#salesPieces').val();
											var salesKgNew;

											// Determines the balance KG
											// Quantity amount and sales KG
											// Quantity
											console.log('above saleskg');
											$('#salesKg')
													.on(
															'input',
															function() {

																salesKgNew = $(
																		'#salesKg')
																		.val();
																var BalanceKg = (parseInt(salesKg) - parseInt(salesKgNew));
																$(
																		'#balanceQtyKg')
																		.attr(
																				'value',
																				BalanceKg);
																$('#salesQtyKg')
																		.attr(
																				'value',
																				salesKgNew);

															});

											// Determines the balance KG
											// Quantity amount and
											// sales KG Quantity
											$('#salesPieces')
													.on(
															'input',
															function() {

																var salesPiecesNew = $(
																		'#salesPieces')
																		.val();

																var BalancePieces = (parseInt(salesPieces) - parseInt(salesPiecesNew));
																$(
																		'#balanceQtyPieces')
																		.attr(
																				'value',
																				BalancePieces);
																$(
																		'#salesQtyPieces')
																		.attr(
																				'value',
																				salesPiecesNew);

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
	var salesLoadData = [];

	$('#salesReadyTable tbody tr td')
			.keydown(
					function(e) {

						var invoiceNo = $('#invoiceNo');
						var customer = $("#customerSelect");
						var product = $('#salesProductSelect');
						var salesPieces = $('#salesPieces');
						var salesKg = $('#salesKg');
						var salesRate = $('#salesRate');
						var salesAmount = $('#salesAmount');
						var salesAvgWeight = $('#salesAvgWeight');
						var purchaseId = $('#salesTable').DataTable().row(this)
								.data().purchaseId;
						// alert(
						// $('#salesTable').DataTable().row(this).data().purchaseId);
						var purchaseDate = $('#salesTable').DataTable().row(
								this).data().date;
						var purchaseId = $('#salesTable').DataTable().row(this)
								.data().purchaseId;
						var van = $('#salesTable').DataTable().row(this).data().vanName;
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
							"purchaseId" : purchaseId,
							"purchaseDate" : purchaseDate,
							"van" : van

						}

						var salesLoadRow = {
							"InvoiceNo" : invoiceNo.val(),
							"customer" : customer.val(),

						}

						if (e.keyCode === 13) {

							productRowData.push(productRow);
							salesLoadData.push(salesLoadRow);
							salesReadyTable.row.add(
									[ invoiceNo.val(), customer.val(),
											product.val(), salesPieces.val(),
											salesKg.val(), salesRate.val(),
											salesAmount.val(),
											salesAvgWeight.val() ]).draw();

							var productJson = '{salesData:'
									+ JSON.stringify(productRowData) + '}';
							$('#productJson').val(productJson);

							var salesLoadJson = '{salesLoadData:'
									+ JSON.stringify(salesLoadData) + '}';
							$('#salesLoadJson').val(salesLoadJson);
							console.log('productJson' + productJson);
							console.log('salesLoadJson' + salesLoadJson);

						}
					});

	// ajaxCall to purchaseServlet
	$('#insertBtn').on('click', function() {

		$.ajax({
			url : 'PurchaseServlet',
			type : 'POST',
			data : {
				update : 'true',
				purchaseId : $('#purchaseId').val(),
				company : $('#companyName').val(),
				product : $('#salesProduct').val(),
				balanceQtyKG : $('#balanceQtyKg').val(),
				balanceQtyPieces : $('#balanceQtyPieces').val()
			},
			success : function() {
				console.log('suceess to purchaseServlet');
			},
			error : function() {
				console.log('error to purchaseServlet');
			}
		});

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