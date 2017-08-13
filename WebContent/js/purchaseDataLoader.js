$(function() {

	$.ajax({
		url : 'PurchaseServlet',
		data : {
			dataLoader : true
		},
		type : 'POST',
		success : function() {
			alert('Data Loaded Successfully from Database');
		},
		error : function() {
			alert('Error Loading Data from Database');

		}
	});

	$.getJSON('/server/jsonfiles/purchaseLoader.json', function(data) {

		var jsonData = data['van'];
		$.each(jsonData, function(key, val) {
			$('#vanList').append(
					'<option value="' + val.name + '">' + val.name
							+ '</option>');
		});
	});

	$.getJSON('/server/jsonfiles/purchaseLoader.json', function(data) {
		var jsonData = data['driver'];
		$.each(jsonData, function(key, val) {
			$('#driverList1').append(
					'<option value="' + val.name + '">' + val.name
							+ '</option>');
			$('#driverList2').append(
					'<option value="' + val.name + '">' + val.name
							+ '</option>');
		});
	});

	$.getJSON('/server/jsonfiles/purchaseLoader.json', function(data) {
		var jsonData = data['cleaner'];
		$.each(jsonData, function(key, val) {
			$('#cleanerList1').append(
					'<option value="' + val.name + '">' + val.name
							+ '</option>');
			$('#cleanerList2').append(
					'<option value="' + val.name + '">' + val.name
							+ '</option>');
		});
	});
	
	$.getJSON('/server/jsonfiles/purchaseLoader.json', function(data) {
		var jsonDataProduct = data['product'];
		$.each(jsonDataProduct, function(key, val) {
			$('#productSelect').append(
					'<option value="' + val.name + '">' + val.name
							+ '</option>');
		});
		
		//***********Create the selects in table (In case we need it in future)**********
//		var jsonDataPieces = data['pieces'];
//		$.each(jsonDataPieces,function(key,val){
//		$('#pieces').append('<option value="'+ val.name+'">'+val.name+'</option>');
//		});
		
//		var jsonDataPieces = data['kg'];
//		$.each(jsonDataPieces,function(key,val){
//		$('#kg').append('<option value="'+ val.name+'">'+val.name+'</option>');
//		});
		
//		var jsonDataPieces = data['rate'];
//		$.each(jsonDataPieces,function(key,val){
//		$('#rate').append('<option value="'+ val.name+'">'+val.name+'</option>');
//		});
		
//		var jsonDataPieces = data['amount'];
//		$.each(jsonDataPieces,function(key,val){
//		$('#amount').append('<option value="'+ val.name+'">'+val.name+'</option>');
//		});
		
//		var jsonDataPieces = data['weight'];
//		$.each(jsonDataPieces,function(key,val){
//		$('#weight').append('<option value="'+ val.name+'">'+val.name+'</option>');
//		});
	});

});