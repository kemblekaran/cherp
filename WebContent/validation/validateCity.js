$(function() {
	
	// validating the cityForms
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "em",
		rules : {
			stateName : "required",
			cityName : "required"
		},
		messages : {
			stateName : {
				required : 'State Name Required!'
			},
			cityName : {
				required : 'City Name Required!'
			}
		}
	});
});