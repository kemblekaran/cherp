$(function() {

	$.getJSON('/server/jsonfiles/indianStates.json', function(data) {
		$.each(data, function(key, val) {
			$('#stateName').append(
					'<option value="' + val.name + '">' + val.name + '</option>');
		});
	});

});