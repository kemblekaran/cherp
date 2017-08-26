$(function() {

	var productJsonArray = [];
	var product = $('#productSelect');
	var kg = $('#kg');
	var pieces = $('#pieces');
	var rate = $('#rate');
	var amt = $('#amount');
	var avgWeight = $('#avgWeight');
	var finalAmount = $('#finalAmount');
	var purchaseId = $('#purchaseid');
	var outstanding = $('#outstanding');

	var productRowData = [];
	var json;

	// calculate amount and avg weight
	rate.on('input', function() {

		var newAmt = parseInt(rate.val()) * parseInt(pieces.val());
		var newAvgWeight = parseInt(kg.val()) / parseInt(pieces.val());
		if (newAmt !== null || newAvgWeight !== null) {
			amt.val(newAmt);
			avgWeight.val(newAvgWeight);
		}
	});

	// set purchase id value
	$.getJSON('/server/jsonfiles/purchaseView.json', function(result) {
		var length = result.data.length;
		var purchaseId = result.data[length - 1].purchaseId;

		$('#purchaseid').val(purchaseId + 1);
	});

	// on double click in purchaseView send data to SalesServlet
	$('#purchaseViewTable tbody').on('dblclick', 'tr', function() {
		// By adding this to the row() it will select the current row
		var purchaseId = dataTable.row(this).data().purchaseId;
		console.log(purchaseId);
		$.ajax({
			url : 'SalesServlet',
			async : false,
			data : {
				purchaseId : purchaseId,
				purchaseView : 'true'
			},
			type : 'post',
			success : function(data) {
				console.log('Purchase successfully retrieved in sales!');
			},
			error : function() {
				console.log('error in purchase double click ajax!');
			}
		});
	});

	$('#purchaseViewTable tbody').on('dblclick', function() {
		window.location = 'Sales.html';
	});

	$('#productTable tbody tr td').keydown(
			function(e) {

				var productRow = {
					"product" : product.val(),
					"pieces" : pieces.val(),
					"kg" : kg.val(),
					"rate" : rate.val(),
					"amt" : amt.val(),
					"avgWeight" : avgWeight.val()
				}

				if (e.keyCode === 13) {
					console.log(productRow.product);
					productRowData.push(productRow);

					productTable.row.add(
							[ product.val(), pieces.val(), kg.val(),
									rate.val(), amt.val(), avgWeight.val() ])
							.draw();

//					console.log('Json' + productJsonArray);
					console.log(productRowData);

				}

				// for setting final amount
				for (var i = 0; i < productRowData.length; i++) {
					var fa = productRowData[i].amt;

				}

				// finalAmount = outstanding + value of amount from table row
				var finalAmountResult = +outstanding.val() + +fa;

				finalAmount.attr('value', finalAmountResult);

			});

	// ajaxCall to purchaseServlet
	$('#insertBtn').on('click', function() {

		

		var productJson = '{data:' + JSON.stringify(productRowData) + '}';
		$('#productJson').val(productJson);
		console.log(productJson);
		$('#PurchaseForm').submit(function(e) {
			
			$.ajax({
				url : 'PurchaseServlet',
				type : 'post',
				data : $('#PurchaseForm').serialize(),

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