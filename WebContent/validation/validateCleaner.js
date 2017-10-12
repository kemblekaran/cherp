$(function() {

	// Pattern for pan card number validation handler
	$.validator.addMethod("pan", function(value, element) {
		return this.optional(element) || /^[A-Z]{5}\d{4}[A-Z]{1}$/.test(value);
	}, "Invalid Pan Number");

	// Pattern for driving lisense validation handler
	$.validator.addMethod("license", function(value, element) {
		return this.optional(element) || /^[A-Z]{2}-\d{2}-\d{4}-\d{7}$/.test(value);
	}, "Invalid license Number");

	//Patter for Adhaar formatter
	$.validator.addMethod("adhaar", function(value, element) {
		return this.optional(element) || /^\d{4}-\d{4}-\d{4}$/.test(value);
	}, "Invalid Adhaar Format");
	
	
	
	// validates the cleaner form
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "b",
		rules : {
			fname : "required",
			lname : "required",
			curAdd : "required",
			perAdd : "required",
			state : "required",
			city : "required",
			mobile : {
				required : true,
				minlength : 10,
				
			},
			phone : {
				required : true,
				minlength : 7,
				
			},
			drLicense : {
				required : true,
				license : true,
			},
			panNo : {
				required : true,
				pan : true
			},
			adhaarNo : {
				required : true,
				adhaar: true,
			},
			photo : "required"
		},
		messages : {
			mobile : {
				minlength : 'Please enter atleast 10 digits.',
			},
			phone : {
				minlength : 'Please enter atleast 6 digits.'
			},
			photo : {
				required : 'Upload your Photo'
			},
			adhaarNo:{
				required: 'Enter UID Number',
				adhaar: 'Addhar number is invalid, Please enter 12 digits of your <big>Aadhar</big> number.',
			},
			drLicense:{
				required: 'Enter Your license Number',
				license: 'Licence is invalid, make sure that the first two letters are alphabates in capital and others are numbers.',
			},
			panNo : {
				pan : 'Pan number is invalid, Please enter first 5 alphabates character and after 4 digit number and then 1 alphabate character. '
			}
		},
		
	});
});