$(function() {
	
	// validating the userForm
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "em",
		rules : {
			username : {
				required : true
			},
			password : {
				required : true
			}
		},
		messages : {
			username : {
				required : "Enter Your Username"
			},
			password : {
				required : "Enter Your Password"
			}
		}
	});
});