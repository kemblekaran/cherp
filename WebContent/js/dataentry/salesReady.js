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

	// Invokes the onclick listener on salesReady Table
	$('#salesReady tbody').on(
			'click',
			'tr',
			function() {

				// selects all the data of purchase table into salesData
				// variable
				var salesData = salesReady.row().data();

				// TODO
				// This invoice Number needs to be change as per the existing
				// sales done
				var invoiceNumber = 14;
				invoiceNo.val(invoiceNumber);

				// sets the value to the variable from salesData
				pid = salesData.purchaseId;
				salesProduct = salesData.product;
				salesPieces = salesData.pieces;
				salesKg = salesData.kg;
				salesRate = salesData.rate;

				// Fetches the product value from the above purchase table in
				// html
				$('#salesProductSelect').append(
						'<option value="' + salesProduct + '">' + salesProduct
								+ '</option>');

				$('#salesRate').val(salesRate);

				// on entering the value checks for the certain operations
				$('#salesReadyTable tbody tr td').keydown(
						function(e) {

							var salesKgNew = $('#salesKg').val();
							var salesPiecesNew = $('#salesPieces').val();

							if (e.keyCode === 13) {

								// checks against existing pieces and disables
								// next input
								if (salesPiecesNew > salesPieces) {
									alert('pieces quantity exceeds');
									$('#salesKg').attr('disabled', 'disabled');
								}

								// checks against existing kg and disables next
								// input
								if (salesKgNew > salesKg) {
									alert('Kg quantity exceeds');
									$('#salesRate')
											.attr('disabled', 'disabled');
								}

							}

							// sets the amount and average weight value
							var newAmt = parseInt(salesRate)
									* parseInt(salesPiecesNew);
							var newAvgWeight = parseInt(salesKg)
									/ parseInt(salesPiecesNew);
							console.log(newAmt);
							if (newAmt !== null || newAvgWeight !== null) {
								salesAmount.val(newAmt);
								salesAvgWeight.val(newAvgWeight);

							}

						});

			});

});