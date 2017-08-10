$(function() {
	
	// drivers validation form
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
			mobile : "required",
			phone : "required",
			drLiscense : "required",
			panNo : "required",
			adhaarNo : "required",
			photo : "required"
		}
	});
});