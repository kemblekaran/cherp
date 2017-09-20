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
	var outstanding = $('#outstanding').val();

	var productRowData = [];
	var json;

	// get target element when clicked on product table
	// assign product table id to avoid assigning null object to on-input event
	var productTableTargetEle = $('#productTable');

	$('#productTable').on('click', function(e) {
		if (e.target.id.match(/(kg|pieces|rate)/)) {

			productTableTargetEle = e.target;
			console.log("Target element:" + productTableTargetEle.id);

		}
	});

	// calculate amount and avg weight
	productTableTargetEle.on('input', function() {

		var newAmt = parseInt(rate.val()) * parseInt(kg.val());
		var newAvgWeight = parseInt(kg.val()) / parseInt(pieces.val());
		
		
		if (newAmt !== null || newAvgWeight !== null) {

			// toFixed() method places the decimal point after digits specified
			// as a parameter
			
			
			amt.val(newAmt.toFixed(2));
			avgWeight.val(newAvgWeight.toFixed(2));
		} 
		
	});

	// on double click in purchaseView send data to SalesServlet
	$('#purchaseViewTable tbody').on('dblclick', 'tr', function() {
		
		// By adding this to the row() it will select the current row
		var purchaseId = dataTable.row(this).data().purchaseId;
		var date = dataTable.row(this).data().date;
		var van = dataTable.row(this).data().vanName;
	
		$.ajax({
			url : 'SalesServlet',
			async : false,
			data : {
				purchaseId : purchaseId,
				purchaseDate : date,
				van : van,
				purchaseView : 'true'
			},
			type : 'POST',
			success : function(data) {
				console.log('Purchase successfully retrieved in sales!');
			},
			error : function() {
				console.log('error in purchase double click ajax!');
			}
		});
	});

	// redirect to sales html on double click on purchase view rows
	$('#purchaseViewTable tbody').on('dblclick', function() {
		window.location = 'Sales.html';
	});

	// add product to data table when presses enter
	$('#productTable tbody tr td').keydown(
			function(e) {

				var outstanding = $('#outstanding').val();

				var productRow = {
					"purchaseId" : $('#purchaseid').val(),
					"date" : $('#date').val(),
					"vanName" : $('#vanList').val(),
					"driver1" : $('#driverList1').val(),
					"driver2" : $('#driverList2').val(),
					"cleaner1" : $('#cleanerList1').val(),
					"cleaner2" : $('#cleanerList2').val(),
					"company" : $('#companyList').val(),
					"location" : $('#locationList').val(),
					"outstanding" : $('#outstanding').val(),
					"challanNo" : $('#challanNo').val(),
					"rent" : $('#rent').val(),
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

					// console.log('Json' + productJsonArray);
					console.log(productRowData);

				}

				// console.log(outstanding);
				var fa = 0;
				// for setting final amount
				for (var i = 0; i < productRowData.length; i++) {
					fa = fa + parseInt(productRowData[i].amt);

				}
				// for setting final amount
				fa = parseInt(fa) + parseInt(outstanding);

				finalAmount.attr('value', fa.toFixed(2));

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