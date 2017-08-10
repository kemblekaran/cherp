$(function() {
	
	//validates customer form
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "em",
		rules : {
			fname : "required",
			lname : "required",
			cur_add : "required",
			per_add : "required",
			state : "required",
			city : "required",
			mobile : "required",
			phone : "required",
			dr_lisence : "required",
			pan_no : "required",
			adhaar_no : "required",
			photo : "required"
		},
		messages : {
			photo : {
				required : 'Upload your Photo'
			}
		}
	});
});