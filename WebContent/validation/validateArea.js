$(function() {
	
	// validating the areaForm
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "em",
		rules : {
			name : {
				required : true,
				lettersonly : true
			},
			state : {
				required : true
			},
			city : "required"
		},
		messages : {
			name : {
				required : 'Area Name is Required!',
				lettersonly : 'Numbers not allowed!'
			},
			state : {
				required : 'State is Required!'
			},
			city : {
				required : 'City is Required!'
			}
		}
	});
});