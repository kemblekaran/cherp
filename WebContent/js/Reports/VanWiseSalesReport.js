$(function() {
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

	van.on('change', function() {
		console.log(date.val() + van.val());
		$.ajax({
			url : 'PurchaseServlet',
			data : {
				'date' : date.val(),
				'vanName' : van.val()
			},
			type : 'POST',
			success : function() {
				console.log('successfully sent data to the server');
			},
			error : function() {
				console.log('error occurred');
			}
		});
	});

});