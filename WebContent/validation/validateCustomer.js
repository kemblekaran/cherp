$(function() {

	// validates customer form
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "em",
		rules : {
			fname : "required",
			lname : "required",
			shopName : "required",
			curAdd : "required",
			perAdd : "required",
			state : "required",
			city : "required",
			area : "required",
			mobile : {
				required : true,
				minlength : 10,
				maxlength : 10
			},
			phone : {
				required : true,
				minlength : 10,
				maxlength : 10
			}
			
		},
		messages : {
			photo : {
				required : 'Upload your Photo'
			},
			mobile : {
				minlength : '',
				maxlength : 'Please Enter Valid Mobile Number'
			}
		}
	});
});