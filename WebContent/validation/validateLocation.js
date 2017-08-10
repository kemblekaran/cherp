$(function() {
	
	// drivers validation form
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "em",
		rules : {
			location : "required"
		}
	});
});