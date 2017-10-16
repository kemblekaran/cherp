$(function() {

	$.ajax({
		url : 'PurchaseServlet',
		data : {
			dataLoader : true
		},
		type : 'POST',
		success : function() {
			console.log('Data Loaded Successfully!')
		},
		error : function() {
			console.log('Error Loading Data from Database');

		}
	});

	// set purchase id value
	$.getJSON('/server/jsonfiles/purchaseLoader.json', function(data) {
		var jsonData = data['purchaseId'];	
		$('#purchaseid').val(jsonData + 1);
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
		var jsonData = data['Driver'];
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
		var jsonData = data['Cleaner'];
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
		var jsonDataProduct = data['Product'];
		$.each(jsonDataProduct, function(key, val) {
			$('#productSelect').append(
					'<option value="' + val.name + '">' + val.name
							+ '</option>');
		});
	});

	$.getJSON('/server/jsonfiles/purchaseLoader.json', function(data) {
		var jsonDataProduct = data['Location'];
		$.each(jsonDataProduct, function(key, val) {
			$('#locationList').append(
					'<option value="' + val.name + '">' + val.name
							+ '</option>');
		});
	});

	$.getJSON('/server/jsonfiles/purchaseLoader.json', function(data) {
		var jsonDataProduct = data['Company'];
		$.each(jsonDataProduct, function(key, val) {
			$('#companyList').append(
					'<option value="' + val.name + '">' + val.name
							+ '</option>');
		});
	});
});