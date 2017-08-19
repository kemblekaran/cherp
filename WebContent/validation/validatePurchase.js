$('#InsertForm').validate({
	rules : {
		date : {
			required : true
		},
		product : {
			required : true
		}
	},
	messages : {
		date : {
			required : 'Select Date'
		},
		product : {
			required : 'Select Product'
		}
	}
});