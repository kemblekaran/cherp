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

	var cellData = 0;
	var cell = 0;
	var cellKg = 0;
	$('#salesTable tbody').on( 'click', 'td', function (e) {
//	    alert( selectItemTable.cell( this ).data() );
//	    var cell = selectItemTable.cell( this );
//	    cell.data( cell.data() + 1 ).draw();
//	    alert(selectItemTable.row( this ).data() );
//	    var row = selectItemTable.row( this ).data();
////	    alert(row[11]);
//	    alert(row[11](row[11] + 1).draw());
//		alert(selectItemTable.cell(this, 11, { order: 'original' }).data());
		cellData = selectItemTable.cell(this, 12, { order: 'original' });
		cell = cellData.data();
//		alert(cell);
		$(salesKg).each(function() {
		$("#salesKg").on('keyup', function(e) {
			cellKg = parseFloat(salesKg.val());
			
			var minus = 0;			
//			$(salesKg).each(function() {
//			alert(cell);
				minus = cell.toFixed(2) - (parseFloat($(salesKg).val()) || 0);
//			});
//			alert(cell + "cell");
//			var minus = $.trim(cell - cellKg);	
//			alert(minus);
			if(minus >= 0  && e.keyCode != 46 && e.keyCode != 8){
				var newCell = cellData.data( minus.toFixed(2) ).draw();	
				
				if (e.keyCode === 13) {
					// alert(newCell.data());
					cellData.data(newCell.data());
					cell = newCell.data();
					// alert(cell + "new assign cell");
					$(salesKg).val(null);
				}
			}else{
				e.preventDefault();  
				$(salesKg).val(null);
				cellData.data(cell).draw();
			}	
//			alert(cell);
		});
		});
		
	});
	
	
	
	
	
	// balance and sales quantity
	
	var selectItemData;
	$('#salesTable tbody').on('click', 'tr', function() {
		
		
		$("#balanceQtyKg, #balanceQtyPieces, #salesQtyKg, #salesQtyPieces, #salesPieces, #salesKg, #salesRate").val(null);
		selectItemData = selectItemTable.row(this).data();
		console.log('selected data ' + selectItemData);
		console.log('purchase Id ' + selectItemData[0])
		$('#salesProduct').val(selectItemData[4]);
		// console.log('selected id '+JSON.stringify(selectItemData));
		$('#balanceQtyPieces').val(selectItemData[10]);
		$('#balanceQtyKg').val(selectItemData[11]);
		$('#salesQtyPieces').val(selectItemData[5] - $('#balanceQtyPieces').val());
		$('#salesQtyKg').val(selectItemData[6] - $('#balanceQtyKg').val());
		

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
		

		
		$("#salesPieces").on('input', function() {
			
			var balPieces = selectItemData[10] - parseInt(salesPieces.val());
				if(balPieces >= 0){
					$('#balanceQtyPieces').val(balPieces);
					$('#salesQtyPieces').val(selectItemData[5] - $('#balanceQtyPieces').val());
				}else{
//					$("#salesPieces,  #salesQtyPieces").val(null);
//					$('#balanceQtyPieces').val(selectItemData[10]);
					
				}
		});
		
		$(" #salesKg").on('input', function() {
			
			var balKg = parseFloat(balanceQtyKg.val()) - parseFloat(salesKg.val());
				if (balKg >= 0  ) {
					$('#balanceQtyKg').val(balKg);
					$('#salesQtyKg').val(selectItemData[6] - $('#balanceQtyKg').val());
					
				}else{
//					$(" #salesKg,  #salesQtyKg").val(null);
//					$('#balanceQtyKg').val(selectItemData[11]);
					
				}
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

	

	// Invokes the onclick listener on salesReady Table
	// $('#salesTable tbody')
	// .on(
	// 'click',
	// 'tr',
	// function() {
	//
	// // selects all the data of purchase table into salesData
	// // variable
	// var salesData = salesReady.row(this).data();
	//
	// // sets invoiceNo from database
	//
	// // sets the value to the variable from salesData
	// pid = salesData.purchaseId;
	// salesProduct = salesData.product;
	// salesPieces = salesData.pieces;
	// salesKg = salesData.kg;
	// salesRate = salesData.rate;
	// company = salesData.company;
	//
	// $('#salesRate').val(salesRate);
	// $('#companyName').val(company);
	//						
	// // on entering the value checks for the certain
	// // operations
	// $('#salesReadyTable tbody tr td')
	// .keydown(
	// function(e) {
	//
	// var salesPiecesNew = $(
	// '#salesPieces').val();
	// var salesKgNew;
	//
	// // Determines the balance KG
	// // Quantity amount and sales KG
	// // Quantity
	// console.log('above saleskg');
	// $('#salesKg')
	// .on(
	// 'input',
	// function() {
	//
	// salesKgNew = $(
	// '#salesKg')
	// .val();
	// var BalanceKg = (parseInt(salesKg) - parseInt(salesKgNew));
	// $(
	// '#balanceQtyKg')
	// .attr(
	// 'value',
	// BalanceKg);
	// $('#salesQtyKg')
	// .attr(
	// 'value',
	// salesKgNew);
	//
	// });
	//
	// // Determines the balance KG
	// // Quantity amount and
	// // sales KG Quantity
	// $('#salesPieces')
	// .on(
	// 'input',
	// function() {
	//
	// var salesPiecesNew = $(
	// '#salesPieces')
	// .val();
	//
	// var BalancePieces = (parseInt(salesPieces) - parseInt(salesPiecesNew));
	// $(
	// '#balanceQtyPieces')
	// .attr(
	// 'value',
	// BalancePieces);
	// $(
	// '#salesQtyPieces')
	// .attr(
	// 'value',
	// salesPiecesNew);
	//
	// });
	// if (e.keyCode === 13) {
	//
	// // checks against existing
	// // pieces and disables
	// // next input
	// if (salesPiecesNew > salesPieces) {
	// alert('pieces quantity exceeds');
	// $('#salesKg').attr(
	// 'disabled',
	// 'disabled');
	// }
	//
	// // checks against existing kg
	// // and disables next
	// // input
	// if (salesKgNew > salesKg) {
	// alert('Kg quantity exceeds');
	// $('#salesRate').attr(
	// 'disabled',
	// 'disabled');
	// }
	//
	// }
	//
	// // sets the amount and average
	// // weight value
	// var newAmt = parseInt(salesRate)
	// * parseInt(salesKg);
	// var newAvgWeight = parseInt(salesKg)
	// / parseInt(salesPiecesNew);
	// // console.log(newAmt);
	// if (newAmt !== null
	// || newAvgWeight !== null) {
	// salesAmount.val(newAmt);
	// salesAvgWeight
	// .val(newAvgWeight);
	//
	// }
	//
	// });
	//
	// });

		// Initialize salesReadyTable
		$('#salesReadyTable').DataTable();
	// array to store all product to be sell
	var productRowData = [];
	var salesLoadData = [];

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
						var purchaseDate = selectItemData[1];
						var balancePieces =  selectItemData[10];
						var balanceKgs =  selectItemData[11];
						
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
							"id" : id,
							"purchaseId" : purchaseId,
							"purchaseDate" : purchaseDate,
							"van" : van,
							"balancePieces" : balancePieces,
							"balanceKgs" : balanceKgs
						}

						var salesLoadRow = {
							"invoiceNo" : invoiceNo.val(),
							"customer" : customer.val(),
							"invoiceAmount" : salesAmount.val(),
							"balanceAmount" : salesAmount.val(),
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
							console.log('productJson ' + productJson);
							console.log('salesLoadJson ' + salesLoadJson);

						}
					});

	// ajaxCall to purchaseServlet
	$('#insertBtn').on('click', function() {
		
		$.ajax({
			url : 'SalesServlet',
			type : 'POST',
			data : {
				update : 'true',
				purchaseUpdateData : '{purchaseUpdateData:'+ JSON.stringify(productRowData) + '}',
			},
			success : function() {
				console.log('suceess to purchaseServlet');
			},
			error : function() {
				console.log('error to purchaseServlet');
			}
		});

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