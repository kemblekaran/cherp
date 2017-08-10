$(function(){
	//validates compny form
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "em",
		rules : {
			name : "required",
			pre_add : "required",
			sec_add : "required",
			mobile : "required",
			phone : "required",
			state : "required",
			city : "required",
			pin_code : "required",
			own_name : "required",
			pan_no : "required",
			op_bal : "required"
		}
	});
});