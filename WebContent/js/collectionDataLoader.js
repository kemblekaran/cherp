$(function() {

	// Variable declaration

	var amount = 0;
	var payNow = $("#payNow");
	var closingBal = $("#closingBal");
	var balAmount = 0;
	var insertData = document.getElementById('insertBtn');
	var actionURL = $('#actionURL').val();

	$.getJSON('/server/jsonfiles/customer.json', function(data) {
		var jsonDataProduct = data['data'];
		$.each(jsonDataProduct, function(key, val) {
			$('#customerList').append(
					'<option value="' + val.fname + " " + val.lname +'">' + val.fname + " "+ val.lname
							+ '</option>');
		});
		//selecting area onchange
		$("#customerList").on("change",function() {
			var customerName = $(this).val();
			$.each(jsonDataProduct, function(key, val) {
				if(customerName == val.fname + " "+val.lname){
					$('#areaList').attr('value', val.area);
				}else if(customerName == 'selectCust') {
					$('#areaList').attr('value', null);
				}
			});
		});
	});

	
	
	// //Get the table
	var collectionTable = $('#dataTable').DataTable();

	// Hide first id column
	collectionTable.column(0).visible(false);

	// colored selected row
	$('#dataTable tbody').on('click', 'tr', function() {

		if ($(this).hasClass('selected')) {
			$(this).removeClass('selected');
		} else {
			collectionTable.$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
		}
	});

//	// To display payment details of purchase for each company.
	$("#customerList").on("change",function() {
				var customerName = $(this).val();
				console.log(customerName);
				$.ajax({
					type : "POST",
					url : "/server/jsonfiles/salesLoad.json",
					dataType : "json",
					select : true,
					success : function(data) {
						var salesData = data['data'];
						var jsonPayment = data['data'];

						// To display total amount to be paid
						$.each(salesData, function(key, val) {
							if (customerName == val.customer ) {
								amount = amount + val.balanceAmount;
							}
						});
						var balAmt = amount;
						amount = 0;
						
						$.each(salesData, function(key, val) {

							if (customerName == val.customer ) {
								collectionTable.row.add(
										[ val.id, val.date, val.invoiceNo,
												val.invoiceAmount,
												val.balanceAmount ]).draw();

								toBeReceived = $("#toBeReceived").val(balAmt);
								$("#customerList").on("change", function() {
									collectionTable.clear().draw();
									toBeReceived.val(null);
									payNow.val(null);
									closingBal.val(null);
									
								});

							}
						});// forEach method close

					}// success method close
				});// ajax method close
			});// onchange event close

	// AmountDetails
	$("#payNow").on('input', function() {
		var total = parseInt(toBeReceived.val()) - parseInt(payNow.val());

		// var balTotal = selectedBal - parseInt(payNow.val());
		if (total >= 0) {

			var bal = parseInt(closingBal.val(total));
		} else {
			closingBal.val(null);
			payNow.val(null);
			// alert("Please enter right amount");
		}
	});

	// insertButtonListener and insert data function
	insertData.addEventListener('click', function() {

		$('#InsertForm').submit(function(e) {
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

	});

	// cash bank enabled disabled
	$(document).ready(function() {

		// set current date to collection using datepicker
		$(function() {
			
			$("#collectionDate, #chDate").datepicker({
				
				showAnim : 'drop',
				showButtonPanel : true,
			}).datepicker('setDate', 'today');
		});

		function checkradiobox() {
			var radio = $("input[name='collectionMode']:checked").val();
			$('#name, #depositIn, #branch, #chDate, #chNo ').attr('disabled', true);
			$('#toBeReceived, #closingBal').attr('readonly', true);
			if (radio == "cash") {
				$('#name').attr('disabled', true);
				$('#depositIn').attr('disabled', true);
				$('#branch').attr('disabled', true);
				$('#chDate').attr('disabled', true);
				$('#chNo').attr('disabled', true);

			} else if (radio == "bank") {
				$('#name').attr('disabled', false);
				$('#depositIn').attr('disabled', false);
				$('#branch').attr('disabled', false);
				$('#chDate').attr('disabled', false);
				$('#chNo').attr('disabled', false);

			}
		}

		$("#optionsRadios1, #optionsRadios2").change(function() {
			checkradiobox();
		});

		checkradiobox();

	});

	// select data from json file
	function selectAllAjax() {
		if (actionURL !== 'SaleseServlet') {
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