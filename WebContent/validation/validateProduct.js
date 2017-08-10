$(function() {
	
	//validates product form
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "em",
		rules : {
			prodName : "required",
			prodType : "required"
		}
	});
});