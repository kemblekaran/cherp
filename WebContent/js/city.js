//loads state data into dropdown
		$.getJSON('/server/jsonfiles/state.json', function(data) {
			var jsonData = data['data'];
			$.each(jsonData, function(key, val) {
				$('#stateName').append(
						'<option value="' + val.stateName + '">'
								+ val.stateName + '</option>');
			});
		});