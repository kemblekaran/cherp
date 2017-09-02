$(function() {

	$('#vanList').on('change', function() {
		var date = $('#date').val();
		var van = $('#vanList').val();

		var DataSelector = {
			"date" : date,
			"van" : van
		}

		$.ajax({
			url : 'SalesDataDecider',
			async : false,
			data : {
				date : DataSelector.date,
				van : DataSelector.van
			},
			type : 'post',
			success : function(data) {
				console.log('success');
			},
			error : function() {
				console.log('error');
			}
		});

		// gets product value from saleView.json
		$.getJSON('/server/jsonfiles/salesDataSelector.json', function(data) {
			var jsonData = data['data'];
			$.each(jsonData, function(key, val) {
				$('#purchaseId').attr('value', val.purchaseId);
			});
		});

		salesReady.ajax.reload();

		// console.log('DataSelector'+DataSelector.date);
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

	// gets product value from saleView.json
	$.getJSON('/server/jsonfiles/saleView.json', function(data) {
		var jsonData = data['product'];
		$.each(jsonData, function(key, val) {
			$('#salesProductSelect').append(
					'<option value="' + val.name + '">' + val.name
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
	$('#salesReady tbody')
			.on(
					'click',
					'tr',
					function() {

						// selects all the data of purchase table into salesData
						// variable
						var salesData = salesReady.row().data();

						// TODO
						// This invoice Number needs to be change as per the
						// existing
						// sales done
						var invoiceNumber = 14;
						invoiceNo.val(invoiceNumber);

						// sets the value to the variable from salesData
						pid = salesData.purchaseId;
						salesProduct = salesData.product;
						salesPieces = salesData.pieces;
						salesKg = salesData.kg;
						salesRate = salesData.rate;

						// Fetches the product value from the above purchase
						// table in
						// html
						$('#salesProductSelect').append(
								'<option value="' + salesProduct + '">'
										+ salesProduct + '</option>');

						$('#salesRate').val(salesRate);

						// on entering the value checks for the certain
						// operations
						$('#salesReadyTable tbody tr td')
								.keydown(
										function(e) {

											var salesPiecesNew = $(
													'#salesPieces').val();

											// Determines the balance KG
											// Quantity amount and
											// sales KG Quantity
											$('#salesKg')
													.on(
															'input',
															function() {

																var salesKgNew = $(
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

				var productRow = {
					"InvoiceNo" : invoiceNo.val(),
					"customer" : customer.val(),
					"product" : product.val(),
					"salesPieces" : salesPieces.val(),
					"salesKg" : salesKg.val(),
					"salesRate" : salesRate.val(),
					"salesAmount" : salesAmount.val(),
					"salesAvgWeight" : salesAvgWeight.val()
				}

				console.log(productRow);

				if (e.keyCode === 13) {
					console.log(productRow.product);
					productRowData.push(productRow);

					salesReadyTable.row.add(
							[ invoiceNo.val(), customer.val(), product.val(),
									salesPieces.val(), salesKg.val(),
									salesRate.val(), salesAmount.val(),
									salesAvgWeight.val() ]).draw();

					// console.log('Json' + productJsonArray);
					console.log(productRowData);

				}
			});

	// ajaxCall to purchaseServlet
	$('#insertBtn').on('click', function() {

		var productJson = '{salesData:' + JSON.stringify(productRowData) + '}';
		$('#productJson').val(productJson);
		console.log(productJson);
		$('#SalesForm').submit(function(e) {

			$.ajax({
				url : 'SalesServlet',
				type : 'post',
				data : $('#SalesForm').serialize(),

				success : function(data) {
					console.log('success');
				},
				error : function() {

				}
			})
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