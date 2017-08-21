$(function() {

	var updateButton = document.getElementById('updateBtn');
	var deleteButton = document.getElementById('deleteBtn');
	var dbActionPerformed;
	var insertData = document.getElementById('insertBtn');
	var actionURL = $('#actionURL').val();
	// var className = $('#InsertForm').attr('class');

	// update button listener
	if (updateButton != null) {
		updateButton.addEventListener('click', function() {

			dbActionPerformed = "update";
			CherpDataTable.MakeCellsEditable({
				"onUpdate" : dataManipulator,
				"columns" : editableCols
			});
		});
	}

	// delete button listener
	if (deleteButton != null) {
		deleteButton.addEventListener('click', function() {

			var permisssion = confirm("Do you want to delete ?");

			if (permisssion == true) {
				dbActionPerformed = "delete";
				CherpDataTable.MakeCellsEditable({
					"onUpdate" : dataManipulator,
					"columns" : editableCols
				});
			} else {
				alert("No");
			}
		});
	}

	// prompts user to confirms delete or not
	function getConfirmation() {
		var retVal = confirm("Do you want to continue ?");
		if (retVal == true) {
			alert("Record Deleted successfully");
			return true;
		} else {
			alert("No");
			return false;
		}
	}

	// disable editing on esc key press
	window.onkeyup = function(e) {
		if (e.keyCode == 27)
			CherpDataTable.MakeCellsEditable("destroy");

	}

	// insertButtonListener and insert data function
	insertData.addEventListener('click', function() {

		// if ($('#InsertForm').valid()) {
		$('#InsertForm').submit(function(e) {

			if (actionURL === 'PurchaseServlet') {
				e.preventDefault();
				e.stopImmediatePropagation();
			}
			$.ajax({
				url : actionURL,
				type : "POST",
				data : $('#InsertForm').serializeArray(),
				async : false,
				success : function(data) {
					if (data != null) {
						alert(data);
					}
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert("Insert Form Error");
				}
			});

		});
		// }
	});

	// update or delete data method
	function dataManipulator(updatedCell, updatedRow, oldValue) {

		var updateValueData = {}

		if (oldValue != updatedCell.data() || dbActionPerformed == "delete") {

			$.ajax({
				url : actionURL,
				type : "post",
				data : {
					operation : dbActionPerformed,
					oldValue : oldValue,
					updatedRow : updatedRow.data()
				},
				success : function(data) {
					alert(data);
					CherpDataTable.ajax.reload();
				},
				error : function() {
					alert('Update or Delete data error');
				}

			});
		}
	}

	// select data from json file
	function selectAllAjax() {
		if (actionURL !== 'PurchaseServlet') {
			$.ajax({
				url : actionURL,
				type : "post",
				success : function(data) {

				},
				error : function() {
					alert('selectAllAjax error');
				}
			});
		}
	}
	selectAllAjax();

});
