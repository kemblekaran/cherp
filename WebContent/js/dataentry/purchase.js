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
	var payloadRowData = [];
	var json;

	var amount = 0;
	//display outstanding of company
	$("#companyList").on("change",function() {
				var companyName = $(this).val();

				$.ajax({
					type : "POST",
					url : "/server/jsonfiles/payload.json",
					dataType : "json",
					select : true,
					success : function(data) {
						var jsonDataProduct = data['data'];
						var jsonPayment = data['data'];

						// To display total amount to be paid
						$.each(jsonPayment, function(key, val) {
							if (companyName == val.company) {
								amount = amount + val.balanceAmount;
							}
						});
						var balAmt = amount;
						amount = 0;

						$.each(jsonDataProduct, function(key, val) {

							if (companyName == val.company) {
								outstanding= $("#outstanding").val(balAmt);
								$("#companyList").on("change", function() {
									productTable.clear().draw();
									outstanding.val(null);

								});

							}
						});// forEach method close

					}// success method close
				});// ajax method close
			});// onchange event close
	
	$("#vanList").on("change", function() {
		productTable.clear().draw();

	});
	
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

		var newAmt = (parseFloat(rate.val()) || 0) * (parseFloat(kg.val()) || 0);
		var newAvgWeight = ((parseFloat(kg.val()) || 0) / (parseFloat(pieces.val()) || 0)) || 0;
		
		
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
		var purchaseDate = dataTable.row(this).data().date;
		var van = dataTable.row(this).data().vanName;
		console.log(purchaseId);
		console.log(purchaseDate);
		console.log(van);
		$.ajax({
			url : 'SalesServlet',
			async : false,
			data : {
				purchaseId : purchaseId,
				purchaseDate : purchaseDate,
				van : van,
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

	// redirect to sales html on double click on purchase view rows
	$('#purchaseViewTable tbody').on('dblclick', function() {
		window.open('Sales.html','_blank');
	});

	// add product to data table when presses enter
	$('#productTable thead tr th').keydown(
			function(e) {

//				var outstanding = $('#outstanding').val();

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
					"amount" : amt.val(),
					"avgWeight" : avgWeight.val(),
					"balancePieces" : pieces.val(),
					"balanceKG" : kg.val(),
				}

				var payloadRow = {
						
						"purchaseId" : $('#purchaseid').val(),
						"date" : $('#date').val(),
						"company" : $('#companyList').val(),
						"amount" : amt.val()
				}
				if (e.keyCode === 13) {
					console.log(productRow.product);
					productRowData.push(productRow);
					payloadRowData.push(payloadRow);
					
					
					
					productTable.row.add(
							[ product.val(), pieces.val(), kg.val(),
									rate.val(), amt.val(), avgWeight.val() ])
							.draw();

					// console.log('Json' + productJsonArray);
					console.log("productRowData "+JSON.stringify(productRowData));
					console.log("payloadRowData "+JSON.stringify(payloadRowData));
					
					product.val();
					pieces.val(null);
					kg.val(null);
					rate.val(null);
					amt.val(null);
					avgWeight.val(null);

				}
				
				// console.log(outstanding);
				var fa = 0;
				// for setting final amount
				for (var i = 0; i < productRowData.length; i++) {
					fa = fa + parseFloat(productRowData[i].amount);

				}
				// for setting final amount
				fa = parseFloat(fa); //+ parseInt(outstanding);

				finalAmount.attr('value', fa.toFixed(2));

			});

//	$('#insertBtn').on('click', function(){
//		
//		
//		
//	});
	
	// ajaxCall to purchaseServlet
	$('#insertBtn').on('click', function() {

		var productJson = '{data:' + JSON.stringify(productRowData) + '}';
		var payloadJson = '{payLoadData:' + JSON.stringify(payloadRowData) + '}';
		$('#productJson').val(productJson);
		console.log("produJson*****"+productJson);
		
		$('#payloadJson').val(payloadJson);
		
		console.log("payloadJson---" + payloadJson);
		
		$('#PurchaseForm').submit(function(e) {
			e.preventDefault();
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
		productTable.clear().draw();
	});

	// to prevent form submission on enter key press
	$(window).keydown(function(event) {
		if (event.keyCode == 13) {
			event.preventDefault();
			return false;
		}
	});

});