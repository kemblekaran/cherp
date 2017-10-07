//set current date to date of account opening using datepicker
	$(function() {
		$("#dateAccOp").datepicker({
			dateFormat: 'dd/mm/yy',
			showAnim : 'drop',
			showButtonPanel : true,
		}).datepicker('setDate', 'today');
	});
	
	//<!-- TO make first letter capital -->

	function capitalize(textboxid, str) {
		// string with alteast one character
		if (str && str.length >= 1) {
			var firstChar = str.charAt(0);
			var remainingStr = str.slice(1);
			str = firstChar.toUpperCase() + remainingStr;
		}
		document.getElementById(textboxid).value = str;
	}

//loads state data into dropdown
$.getJSON('/server/jsonfiles/state.json', function(data) {
	var jsonData = data['data'];
	$.each(jsonData, function(key, val) {
		$('#stateName').append(
				'<option value="' + val.stateName + '">' + val.stateName
						+ '</option>');
	});
});

$("#stateName").on("change", function() {
	$.getJSON('/server/jsonfiles/city.json', function(data) {
		var jsonData = data['data'];
		var stateName = $('#stateName').val();
		$.each(jsonData, function(key, val) {
			if (stateName == val.stateName) {
				$('#city').append(
						'<option value="' + val.cityName + '">' + val.cityName
								+ '</option>');
				
				$("#stateName").on("change", function() {
					$('#city option[value="' + val.cityName + '"]').remove();
				});
			}
	
		});
	});
});