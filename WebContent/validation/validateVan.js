$(function() {
	
	// Pattern for pan card number validation handler
	$.validator.addMethod("vehicle", function(value, element) {
		return this.optional(element) || /^[A-Z]{2}-\d{2}-\d{4}$/.test(value);
	}, "Invalid Vehicle Number");
	
	
	//validates van form
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "em",
		rules : {
			vanNumber : {
				required : true,
				vehicle : true
			},
			companyName : "required",
			model : "required",
			ownerName : "required",
			insuranceNo : "required",
			insStartDate : "required",
			insEndDate : "required",
			permitNo : "required",
			permitStartDate : "required",
			permitEndDate : "required"
		},
		messages:{
			vanNumber : {
				required : 'Enter Van Number',
				vehicle : 'Enter in Format XX-XX-XXXX'
			},
			companyName : {
				required : 'Enter Name of the company'
			},
			ownerName : {
				required : 'Enter Owner Name'  
			},
			insuranceNo : {
				required : 'Enter Insurance Number'
			},
			insStartDate : {
				required : 'Enter Insurance Start Date'
			},
			insEndDate : {
				required : 'Enter Insurance Ending Date'
			},
			permitNo : {
				required : 'Enter Permit Number'
			},
			permitStartDate : {
				required : 'Enter Permit Start Date'
			},
			permitEndDate : {
				required : 'Enter Permit Ending Date'
			}
		}
	});
});