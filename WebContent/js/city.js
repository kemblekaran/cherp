

  function capitalize(textboxid, str) {
      // string with alteast one character
      if (str && str.length >= 1)
      {       
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
						'<option value="' + val.stateName + '">'
								+ val.stateName + '</option>');
			});
		});