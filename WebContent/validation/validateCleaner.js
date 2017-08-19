$(function() {

	// Pattern for pan card number validation handler
	$.validator.addMethod("pan", function(value, element) {
		return this.optional(element) || /^[A-Z]{5}\d{4}[A-Z]{1}$/.test(value);
	}, "Invalid Pan Number");

	// Pattern for driving lisense validation handler
	$.validator.addMethod("lisence", function(value, element) {
		return this.optional(element) || /^[A-Z]{2}-\d{14}$/.test(value);
	}, "Invalid Lisence Number");

	//Patter for Adhaar formatter
	$.validator.addMethod("adhaar", function(value, element) {
		return this.optional(element) || /^\d{4}-\d{4}-\d{4}$/.test(value);
	}, "Invalid Adhaar Format");
	
	
	// validates the cleaner form
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "em",
		rules : {
			fname : "required",
			lname : "required",
			curAdd : "required",
			perAdd : "required",
			state : "required",
			city : "required",
			mobile : {
				required : true,
				minlegth : 10,
				maxlength : 10
			},
			phone : {
				required : true,
				minlegth : 10,
				maxlength : 10
			},
			drLicense : {
				required : true,
				lisence : true,
				minlength: 17,
				maxlength: 17
			},
			panNo : {
				required : true,
				pan : true
			},
			adhaarNo : {
				required : true,
				adhaar: true,
				minlength : 14,
				maxlength : 14
			},
			photo : "required"
		},
		messages : {
			photo : {
				required : 'Upload your Photo'
			},
			adhaarNo:{
				required: 'Enter UID Number',
				adhaar: 'Add a hyphen between Adhaar Number',
				minlength: 'Adhaar is not Valid',
				maxlength: 'Enter Valid Adhaar Number'
			},
			drLicense:{
				required: 'Enter Your Lisence Number',
				lisence: 'Liscence is invalid or add a hyphen between letters and numbers',
				minlength: 'Liscence must be 17 characters long',
				maxlength: 'Liscence Number must not contains more than 17 characters'
			}
		}
	});
});