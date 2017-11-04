$(function() {

	var selectItemTable = $('#salesTable').DataTable();
	// store elements into variables
	var invoiceNo = $('#invoiceNo');
	var salesKg = $('#salesKg');
	var rate = $('#salesRate');
	var salesAmount = $('#salesAmount');
	var salesAvgWeight = $('#salesAvgWeight');
	
	var salesPieces = $("#salesPieces");
	var salesKg = $("#salesKg");
	var salesRate = $("#salesRate");
	var invoice = 0;
	
	var balanceQtyKg = $('#balanceQtyKg');
	var	salesQtyKg = $("#salesQtyKg");
	
	var balanceQtyPieces = $("#balanceQtyPieces");
	var salesQtyPieces = $("#salesQtyPieces");
	
	
	
	var pid;
	var salesProduct;
	var salesRate;
	var company;

	
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
		
		$.each(jsonData, function(key, val) {
			invoice = val.invoiceNo;
		});
		var newInvoice = invoice + 1;
		console.log(invoice);
		invoiceNo.val(newInvoice);
		$('#salesReadyTable thead tr th').keydown(function(e) {
			
			if (e.keyCode === 13) {
				newInvoice++;
				
			}
			invoiceNo.val(newInvoice);
		});
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
	$.getJSON('/server/jsonfiles/salesTable.json', function(data) {
		var jsonDataProduct = data['data'];
		$.each(jsonDataProduct, function(key, val) {
			$('#van').val(val.vanName);
		});
//		setTimeout($("#van option[value='"+val.vanName+"']").attr("selected","selected"),1000);
	});

	// list data on van change
	$("#van").on(
			"change",
			function() {
				$('#target').html($(this).val());
				
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
										[ val.id, val.purchaseId, val.date,
												val.company, val.location,
												val.product, val.pieces,
												val.kg, val.rate, val.amount,
												val.avgWeight,
												val.balancePieces,
												val.balanceKG ]).draw();

								$('#purchaseId').val(val.purchaseId);

								$("#van").on("change", function() {
									
									salesReady.clear().draw();
									salesReadyTable.clear().draw();
									$('#invoiceNo').val(invoice+1);
									$('#salesProduct').val(null);
									$('#salesAmount').val(null);
									$('#salesAvgWeight').val(null);
								});

							}
						});// forEach method close
						
						var totalPcs = 0, totalKgs = 0;
						//calculate total pieces and kg in van table
						$.each(jsonData, function(key, val) {
							if (companyName == val.vanName) {
								totalPcs = totalPcs + val.pieces;
								totalKgs = totalKgs + val.kg;
							}
						});
						var showPcs = totalPcs;
						var showKgs = totalKgs
						totalPcs = 0;
						totalKgs = 0;
						console.log(showPcs + " " +showKgs);
						$('#totalPieces').val(showPcs);
						$('#totalKgs').val(showKgs);
						
					}// success method close
				});// ajax method close
			});// onchange event close

	var cellPieces = 0;
	var cellKg = 0;
	var cellK = 0;
	var cellP = 0;
	
	$('#salesTable tbody').on('click', 'td', function(e) {
		
		cellPieces = selectItemTable.cell(this, 11, {
			order : 'original'
		});
		cellKg = selectItemTable.cell(this, 12, {
			order : 'original'
		});
		cellP = cellPieces.data();
		cellK = cellKg.data();

		$("#invoiceNo, #customerSelect, #salesProduct, #salesPieces, #salesKg,  #salesRate, #salesAmount, #salesAvgWeight").on('keyup', function(e) {
			
			$(salesPieces , salesKg).each(function() {

				var minus = 0;
				var minus1 = 0;
				
				minus = cellP.toFixed(2) - (parseFloat($(salesPieces).val()) || 0);
				minus1 = cellK.toFixed(2) - (parseFloat($(salesKg).val()) || 0);

				if (minus >= 0 && minus1 >= 0 && e.keyCode != 46 && e.keyCode != 8) {
					var newCellP = cellPieces.data(minus.toFixed(2)).draw();
				}else {
					e.preventDefault();
					$(salesPieces).val(null);
					cellPieces.data(cellP).draw();
					
				}
				
				if(minus1 >= 0  && e.keyCode != 46 && e.keyCode != 8){
					var newCellK = cellKg.data(minus1.toFixed(2)).draw();
				} else {
					e.preventDefault();
					
					$(salesKg).val(null);
					cellKg.data(cellK).draw();
				}
				
				
				if (e.keyCode === 13) {
					
					cellP = parseFloat(newCellP.data());
					cellK = parseFloat(newCellK.data());
					$(salesPieces).val(null);
					$(salesKg).val(null);
					$(salesRate).val(null);
					$(salesAmount).val(null);
					$(salesAvgWeight).val(null);

				}
				return cellP, cellK;
			});
			

		});

	});
	
	
	
	
	
	// balance and sales quantity
	
	var selectItemData;
	$('#salesTable tbody').on('click', 'tr', function() {
		
		
		$("#balanceQtyKg, #balanceQtyPieces, #salesQtyKg, #salesQtyPieces, #salesPieces, #salesKg, #salesRate").val(null);
		selectItemData = selectItemTable.row(this).data();
//		console.log('selected data ' + selectItemData);
//		console.log('purchase Id ' + selectItemData[0])
		$('#salesProduct').val(selectItemData[5]);
		
		
		

	});
	
	$("#salesPieces, #salesKg, #salesRate").on('click', function() {
		if ($("#salesProduct").val() == "") {
			alert('Please select a row first');
			$("#balanceQtyKg, #balanceQtyPieces, #salesQtyKg, #salesQtyPieces, #salesPieces, #salesKg, #salesRate").val(null);
		}
	});
	
	$('#van').on('change', function() {
			$("#balanceQtyKg, #balanceQtyPieces, #salesQtyKg, #salesQtyPieces, #salesPieces, #salesKg, #salesRate").val(null);
	});
		

		
	

		
		//calculation for avg weight and amount
//		$('#totalPieces').mask('##.##', {reverse: true});
		$("#salesPieces, #salesKg, #salesRate").on('input', function() {

			var newAmt = (parseFloat(salesRate.val()) || 0) * (parseFloat(salesKg.val()) || 0);
			var newAvgWeight = ((parseFloat(salesKg.val()) || 0) / (parseFloat(salesPieces.val()) || 0)) || 0;
			
			
			if (newAmt !== null || newAvgWeight !== null) {

				// toFixed() method places the decimal point after digits specified
				// as a parameter
				
				
				salesAmount.val(newAmt.toFixed(2));
				salesAvgWeight.val(newAvgWeight.toFixed(2));
			} 
			
		});
	


	// gets product value from salesTable.json
//	$.getJSON('/server/jsonfiles/salesTable.json', function(data) {
//		var jsonData = data['data'];
//		$.each(jsonData, function(key, val) {
//			$('#salesProductSelect').append(
//					'<option value="' + val.product + '">' + val.product
//							+ '</option>');
//		});
//	});
//
	$.getJSON('/server/jsonfiles/customer.json', function(data) {
		var jsonData = data['data'];
		$.each(jsonData, function(key, val) {
			$('#customerSelect').append(
					'<option value="' + val.fname + " " + val.lname + '">'
							+ val.fname + " " + val.lname + '</option>');
		});
	});

	


		// Initialize salesReadyTable
		$('#salesReadyTable').DataTable();
	// array to store all product to be sell
	var productRowData = [];
	var salesLoadData = [];
	var purchaseUpdateData = [];

	$('#salesReadyTable thead tr th')
			.keydown(
					function(e) {

						var invoiceNo = $('#invoiceNo');
						var customer = $("#customerSelect");
						var product = $('#salesProduct');
						var salesPieces = $('#salesPieces');
						var salesKg = $('#salesKg');
						var salesRate = $('#salesRate');
						var salesAmount = $('#salesAmount');
						var salesAvgWeight = $('#salesAvgWeight');
						var id = selectItemData[0];
						var purchaseId = selectItemData[1];
						// alert(
						// $('#salesTable').DataTable().row(this).data().purchaseId);
						var purchaseDate = selectItemData[2];
						var balancePieces =  selectItemData[11];
						var balanceKgs =  selectItemData[12];
						
						var van = $('#van').val();
						console.log('pid :' + purchaseId);
						var productRow = {
							"invoiceNo" : invoiceNo.val(),
							"customer" : customer.val(),
							"product" : product.val(),
							"pieces" : salesPieces.val(),
							"kg" : salesKg.val(),
							"rate" : salesRate.val(),
							"amount" : salesAmount.val(),
							"avgWeight" : salesAvgWeight.val(),
							
							"purchaseId" : purchaseId,
							"purchaseDate" : purchaseDate,
							"van" : van,
							
						}

						var salesLoadRow = {
							"invoiceNo" : invoiceNo.val(),
							"customer" : customer.val(),
							"invoiceAmount" : salesAmount.val(),
							"balanceAmount" : salesAmount.val(),
						}
						
						var purchaseUpdateRow = {
								"id" : id,
								"balancePieces" : balancePieces,
								"balanceKG" : balanceKgs
						}

						if (e.keyCode === 13) {

							
							
							productRowData.push(productRow);
							salesLoadData.push(salesLoadRow);
							purchaseUpdateData.push(purchaseUpdateRow);
							
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
							
							var purchaseUpdateJson = '{purchaseUpdateData:'
								+ JSON.stringify(purchaseUpdateData) + '}';
							$('#purchaseUpdateJson').val(purchaseUpdateJson);
							
							console.log('productJson ' + productJson);
							console.log('salesLoadJson ' + salesLoadJson);
							console.log('purchase update date ' + purchaseUpdateJson);

						}
					});

	// ajaxCall to purchaseServlet
	$('#insertBtn').on('click', function() {
		


		$('#SalesForm').submit(function(e) {
//			e.preventDefault();
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