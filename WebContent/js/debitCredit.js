$(document).ready(
		function() {
			// set current date to debit credit using datepicker
			$(function() {
				$("#creditDebitDate").datepicker({
					// dateFormat: 'dd/mm/yy',
					showAnim : 'drop',
					showButtonPanel : true,
				}).datepicker('setDate', 'today');
			});

			$.getJSON('/server/jsonfiles/customer.json', function(data) {
				var jsonData = data['data'];
				$.each(jsonData, function(key, val) {
					$('#customer').append(
							'<option value="' + val.fname + " " + val.lname
									+ '">' + val.fname + " " + val.lname
									+ '</option>');
				});
			});

			$.getJSON('/server/jsonfiles/company.json', function(data) {
				var jsonData = data['data'];
				$.each(jsonData, function(key, val) {
					$('#company').append(
							'<option value="' + val.name + '">' + val.name
									+ '</option>');
				});
			});

			function checkradiobox() {
				var radio = $("input[name='CreditDebit']:checked").val();
				$('#customerChecked').attr('checked', true);
				$('#companyChecked').attr('checked', true);

				if (radio == "credit") {
					// $('#companyChecked').attr('checked',false);

					$('#customerChecked').attr('disabled', false);
					$('#companyChecked').attr('disabled', true);
					$('#customer').attr('disabled', false);
					$('#company').attr('disabled', true);

				} else if (radio == "debit") {
					// $('#customerChecked').attr('checked',false);

					$('#customerChecked').attr('disabled', true);
					$('#companyChecked').attr('disabled', false);
					$('#customer').attr('disabled', true);
					$('#company').attr('disabled', false);

				}
			}

			$("#optionsRadios1, #optionsRadios2").change(function() {
				checkradiobox();
			});

			checkradiobox();
		});