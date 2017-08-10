$(function() {
	
	// drivers validation form
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "em",
		rules : {
			description : "required"
		},
		messages : {
			description : {
				required : 'Describe About Expenses!'
			}
		}
	});
});