//<!-- TO make first letter capital -->

function capitalize(textboxid, str) {
	// string with alteast one character
	if (str && str.length >= 1) {
		var firstChar = str.charAt(0);
		var remainingStr = str.slice(1);
		str = firstChar.toUpperCase() + remainingStr;
	}
	document.getElementById(textboxid).value = str;
}

// start and end date

$(document).ready(function() {
//	alert("datepicker");
	$(function() {
		//insuranse start date
		$("#insStartDate").datepicker({
//			dateFormat: 'dd/mm/yy',
			showAnim : 'drop',
//			showButtonPanel : true,
			onSelect : function(date) {
				var date2 = $('#insStartDate').datepicker('getDate');
//				date2.setDate(date2.getDate() + 7);
//				$('#insEndDate').datepicker('setDate', date2);
				// sets minDate to dt1 date + 1
				$('#insEndDate').datepicker('option', 'minDate', date2);
			}
		});
//		insuranse end date
		$("#insEndDate").datepicker({
//			dateFormat: 'dd/mm/yy',
			showAnim : 'drop',
//			showButtonPanel : true,
			onClose : function() {
				var dt1 = $('#insStartDate').datepicker('getDate');
				var dt2 = $('#insEndDate').datepicker('getDate');
				// check to prevent a user from entering a date below date of
				// dt1
				if (dt2 <= dt1) {
					var minDate = $('#insEndDate').datepicker('option', 'minDate');
					$('#insEndDate').datepicker('setDate', minDate);
				}
			}
		});
		
		
		
//		insuranse start date
		$("#permitStartDate").datepicker({
//			dateFormat: 'dd/mm/yy',
			showAnim : 'drop',
//			showButtonPanel : true,
			onSelect : function(date) {
				var date2 = $('#permitStartDate').datepicker('getDate');
//				date2.setDate(date2.getDate() + 7);
//				$('#permitEndDate').datepicker('setDate', date2);
				// sets minDate to dt1 date + 1
				$('#permitEndDate').datepicker('option', 'minDate', date2);
			}
		});
//		insuranse start date
		$("#permitEndDate").datepicker({
//			dateFormat: 'dd/mm/yy',
			showAnim : 'drop',
//			showButtonPanel : true,
			onClose : function() {
				var dt1 = $('#permitStartDate').datepicker('getDate');
				var dt2 = $('#permitEndDate').datepicker('getDate');
				// check to prevent a user from entering a date below date of
				// dt1
				if (dt2 <= dt1) {
					var minDate = $('#permitEndDate').datepicker('option', 'minDate');
					$('#permitEndDate').datepicker('setDate', minDate);
				}
			}
		});
	});
});