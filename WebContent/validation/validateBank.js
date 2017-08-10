$(function() {
	// validates bank form
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "em",
		rules : {
			bankName : {
				required : true
			},
			branchName : "required",
			accType : "required",
			accNo : {
				required : true,
				minlength: 13,
				maxlength: 15
			},
			address : "required",
			ifscCode : "required",
			opBal : "required"
		},
		messages : {
			bankName : {
				required : 'Bank Name Required!'
			},
			accType : {
				required : 'Select Account Type'
			},
			accNo:{
				required: 'Enter Your Account Number',
				minlength: 'Account Number is not valid',
				maxlength: 'Account Number is not valid'
			}
		}
	});
});