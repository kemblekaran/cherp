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
	
	//variable for calculate total Kg in purchase
	var purchaseTotalKgs = 0;
	var purchaseTotalAmount = 0;
	
	//variable for calculate total Kg in purchase
	var salesTotalPieces = 0;
	var salesTotalKgs = 0;
	var salesTotalAmount = 0;
	
	var exp1 = $("#exp1");
	var exp2 = $("#exp2");
	var exp3 = $("#exp3");
	var exp4 = $("#exp4");
	var exp5 = $("#exp5");
	var exp6 = $("#exp6");
	var totalExp = $("#totalExp");
	
	var selectedDate;
	var selectedVan;
	
	var totalPurchaseAmt = 0;
	var totalSalesAmt = 0;
	
	$.getJSON('/server/jsonfiles/purchaseLoader.json', function(data) {

		var jsonData = data['van'];
		$.each(jsonData, function(key, val) {
			$('#vanNo').append(
					'<option value="' + val.name + '">' + val.name
							+ '</option>');
		});
	});

	//expenses loaded from master expenses
	$.getJSON('/server/jsonfiles/expenses.json', function(data) {

		var jsonData = data['data'];
		$.each(jsonData, function(key, val) {
			$('#expenses1, #expenses2, #expenses3, #expenses4, #expenses5, #expenses6').append(
					'<option value="' + val.description + '">' + val.description
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

	//on change clear all values
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
			
			//sales van table
			var totalPieces = 0;
			var totalKgs = 0;
			var totalAmount = 0;
			$.getJSON('/server/jsonfiles/salesView.json', function(data) {
				var vanWiseData = data['data'];
				
				
				$.each(vanWiseData, function(key, val) {
					if(selectedDate == val.purchaseDate && selectedVan == val.van){
						
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
				
				salesTotalPieces = totalPieces;
				salesTotalKgs = totalKgs;
				salesTotalAmount = totalAmount;
				
				
				totalPieces = 0 ;
				totalKgs = 0;
				totalAmount = 0;
				
				$('#totalPcs').val(salesTotalPieces);
				$('#totalKgs').val(salesTotalKgs);
				$('#totalAmt').val(salesTotalAmount);
				
				
				
			});
			
//			purchase van table
			$.getJSON('/server/jsonfiles/purchaseView.json', function(data) {
				var vanWiseData = data['data'];
				var purchaseNoArray = [];
				var purKgs = 0;
				var purAmt = 0;
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
									[ val.date, val.company, val.product, val.pieces, val.kg, val.rate, val.amount, val.avgWeight,val.balancePieces,val.balanceKG, val.avgWeight  ]).draw();
							
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
							
							//calculate total purchase kg and amount for the day
							purKgs = purKgs + val.kg;
							purAmt = purAmt + val.amount;
					}
					
				});
				
				
				purchaseTotalKgs = purKgs;
				purchaseTotalAmount = purAmt;
				purKgs = 0;
				purAmt = 0;
//				alert(purchaseTotalKgs + " purchase ");
				
				var weightLoss = purchaseTotalKgs - parseFloat($('#totalKgs').val());
//				alert(weightLoss + " weightLoss");
				var netProfit = parseFloat($('#totalAmt').val()) - purchaseTotalAmount;
				
				$('#wtLoss').val(weightLoss.toFixed(2));
				$('#netProfit').val(netProfit.toFixed(2));
				
				var shrinkage = ( ( weightLoss / purchaseTotalKgs ) * 100 );
				
//				shrinkage = $.trim(shrinkage);
				$("#shrinkage").val((parseFloat(shrinkage) || 0).toFixed(2) + "%");
				//purchase no. array
				
				$("#purchaseNo").val(purchaseNoArray);
				
	//			$("#rent").on('input', function() {
					totalPurchase = parseFloat(purchaseAmt.val()) + parseFloat($("#rent").val());
					if (totalPurchase >= 0) {
	
						parseFloat($("#totalPurchase").val(totalPurchase));
					} else {
						$("#totalPurchase").val(purchaseAmt.val());
						$("#rent").val(null);
						
					}
	//			});
				
			});
			
			
			
			
			
		});//end of show button

		
		
		//enabling textbox on dropdown menu select
		$("#exp1, #exp2, #exp3, #exp4, #exp5, #exp6").attr("disabled",true);
		$("#expenses1").on('change',function(){
			$("#exp1").attr("disabled",false);
		});
		$("#expenses2").on('change',function(){
			$("#exp2").attr("disabled",false);
		});
		$("#expenses3").on('change',function(){
			$("#exp3").attr("disabled",false);
		});
		$("#expenses4").on('change',function(){
			$("#exp4").attr("disabled",false);
		});
		$("#expenses5").on('change',function(){
			$("#exp5").attr("disabled",false);
		});
		$("#expenses6").on('change',function(){
			$("#exp6").attr("disabled",false);
		});
		
		//calculate total expenses
		$("#calcNetProfit").on("click", function(){
			var total = 0;
			total = (parseFloat(exp1.val()) + parseFloat(exp2.val()) + parseFloat(exp3.val()) +
					parseFloat(exp4.val()) + parseFloat(exp5.val()) + parseFloat(exp6.val()));
			totalExp.val(total);
			
			var totalGrossProfit = parseFloat($('#netProfit').val()) - total;
			
			$("#grossProfit").val(totalGrossProfit);
		});

});