$(function() {

	// Load company into dropdown list
	$.getJSON('/server/jsonfiles/company.json', function(data) {
		var jsonDataProduct = data['data'];
		$.each(jsonDataProduct, function(key, val) {
			$('#cmpName').append(
					'<option value="' + val.name + '">' + val.name
							+ '</option>');
		});
	});

	$("#cmpName").on("change", function() {
		var companyName = $(this).val();

		$.ajax({
			type : "POST",
			url : "/server/jsonfiles/payment.json",
			dataType : "json",
			select : true,
			success : function(data) {
				var jsonDataProduct = data['data'];
				var jsonPayment = data['data'];

				$.each(jsonDataProduct, function(key, val) {

					if (companyName == val.company) {
						
						
						$('#opeBal').val(val.closingBal);
					}
					else if(companyName == "selectCmp"){
						$('#opeBal').val(null);
					}
				});

			}

		});
	});

	
	// For setting Today's date
	// $(document).ready(function() {
	// alert("datepicker");
	// $(function() {
	//
	// $("#fromDate").datepicker({
	// showAnim : 'drop',
	// showButtonPanel : true,
	// }).datepicker('setDate', 'today');
	// });
	// });

	// for setting from and to date
	$(document).ready(function() {
		$("#fromDate").datepicker({
			dateFormat : "dd-M-yy",
			// minDate : 0,
			showAnim : 'drop',
			onSelect : function(date) {
				var date2 = $('#fromDate').datepicker('getDate');
				date2.setDate(date2.getDate() + 7);
				$('#toDate').datepicker('setDate', date2);
				// sets minDate to dt1 date + 1
				$('#toDate').datepicker('option', 'minDate', date2);
			}
		});
		$('#toDate').datepicker({
			dateFormat : "dd-M-yy",
			showAnim : 'drop',
			onClose : function() {
				var dt1 = $('#fromDate').datepicker('getDate');
				var dt2 = $('#toDate').datepicker('getDate');
				// check to prevent a user from entering a date below date of
				// dt1
				if (dt2 <= dt1) {
					var minDate = $('#toDate').datepicker('option', 'minDate');
					$('#toDate').datepicker('setDate', minDate);
				}
			}
		});
		
		
	});

});// end of function
