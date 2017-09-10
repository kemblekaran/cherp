$(function() {
	$.getJSON('/server/jsonfiles/van.json', function(data) {

		var jsonData = data['data'];
		$.each(jsonData, function(key, val) {
			$('#vanList').append(
					'<option value="' + val.vanNumber + '">' + val.vanNumber
							+ '</option>');
		});
	});

	var date = $('#date');
	var van = $('#vanList');

	van.on('change', function() {
		console.log(date.val() + van.val());
		$.ajax({
			url : 'VanWiseSales',
			data : {
				'date' : date.val(),
				'van' : van.val()
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