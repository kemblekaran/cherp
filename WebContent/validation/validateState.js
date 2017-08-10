$(function() {
	
	//validates state Form
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "em",
		rules : {
			stateName : "required"
		}
	});
});