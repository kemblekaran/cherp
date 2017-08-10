$(function() {
	//validates van form
	$('#InsertForm').validate({
		errorClass : "red-error",
		errorElement : "em",
		rules : {
			number : "required",
			companyName : "required",
			model : "required",
			owner_name : "required",
			ownerName : "required",
			capacity : "required",
			insuranceNo : "required",
			insStartDate : "required",
			insEndDate : "required",
			permitNo : "required",
			permitStartDate : "required",
			permitEndDate : "required"
		}
	});
});