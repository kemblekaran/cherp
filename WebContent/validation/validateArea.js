$(function() {
	
	$("#code").mask('999-999');
	
	// validating the areaForm
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "b",
		rules : {
			name : {
				required : true,
			},
			state : {
				required : true
			},
			city : "required",
			code : {
				required : true,
				minlength : 7
			}
		},
		messages : {
			name : {
				required : 'Area Name is Required!',
				
			},
			state : {
				required : 'State is Required!'
			},
			city : {
				required : 'City is Required!'
			},
			code : {
				minlength : '<big>Invalid Pin Code.</big> <em>XXX-XXX</em> number format required.',
			}
		}
	});
});