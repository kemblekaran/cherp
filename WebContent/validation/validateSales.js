
//validate sales form
$('#InsertForm').validate({
	errorClass : "red-error",
	errorElement : "em",
	rules : {
		date : {
			required : true
		},
		van : {
			required : true
		}
	},
	messages : {
		date : {
			required : 'Select date'
		},
		van : {
			required : 'select van'
		}
	}
});