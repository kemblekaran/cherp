$(function() {
	
	var purchaseAmt = $("#purchaseAmt");
	var rent = $("#rent");
	var totalPurchase = 0;
	
	
	$.getJSON('/server/jsonfiles/purchaseLoader.json', function(data) {

		var jsonData = data['van'];
		$.each(jsonData, function(key, val) {
			$('#vanNo').append(
					'<option value="' + val.name + '">' + val.name
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

	$("#Show").on('click', function() {
		console.log(date.val() + van.val());
		$.ajax({
			url : 'PurchaseServlet',
			data : {
				'date' : date.val(),
				'vanName' : van.val(),
				vanWiseSales : true
			},
			async : false,
			type : 'POST',
			success : function() {
				console.log('successfully sent data to the server');
			},
			error : function() {
				console.log('error occurred');
			}
		});
		
		$.ajax({
			type : "POST",
			url : "/server/jsonfiles/vanWiseSales.json",
			dataType : "json",
			select : true,
			success : function(data) {
				var vanWiseData = data['data'];

				$.each(vanWiseData, function(key, val) {
					alert(val.driver1);
					$("#driverName1").val(val.driver1);
					$("#driverName2").val(val.driver2);
					$("#purchaseNo").val(val.purchaseId);
					$("#cleanerName1").val(val.cleaner1);
					$("#cleanerName2").val(val.cleaner2);
					$("#purchaseAmt").val(val.finalAmount);
					
					$("#vanNo, #date").on('change', function() {
						$("#driverName1").val(null);
						$("#driverName2").val(null);
						$("#purchaseNo").val(null);
						$("#cleanerName1").val(null);
						$("#cleanerName2").val(null);
						$("#purchaseAmt").val(null);
						$("#rent").val(null);
						$("#totalPurchase").val(null);
					});
				});
				
				$("#rent").on('input', function() {
					console.log(typeof parseInt(purchaseAmt.val())+ " purchase amt");	
					console.log(rent.val());
					totalPurchase = parseInt(purchaseAmt.val()) + parseInt($("#rent").val());
					if (totalPurchase >= 0) {

						parseInt($("#totalPurchase").val(totalPurchase));
					} else {
						$("#totalPurchase").val(purchaseAmt.val());
						$("#rent").val(null);
						
					}
					 
				});

			}

		});
	});

	

});