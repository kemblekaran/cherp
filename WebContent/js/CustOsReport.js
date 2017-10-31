$(document).ready(function() {
	$("#fromDate").datepicker({
		// dateFormat: 'dd/mm/yy',
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
		// dateFormat: 'dd/mm/yy',
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