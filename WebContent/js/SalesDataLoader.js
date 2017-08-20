$(function() {
	
	$.ajax({
		url : 'SalesServlet',
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

	// Loads van data
	$.getJSON('/server/jsonfiles/purchaseLoader.json', function(data) {

		var jsonData = data['van'];
		$.each(jsonData, function(key, val) {
			$('#vanList').append(
					'<option value="' + val.name + '">' + val.name
							+ '</option>');
		});
	});
});