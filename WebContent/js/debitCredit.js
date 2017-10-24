$(document).ready(
		function() {
			
			

			var insertData = document.getElementById('insertBtn');
			var actionURL = $('#actionURL').val();

			
			  $.ajax({
					url : 'DebitCreditNoteServlet',
//					async : false,
					data : {
						noteNoLoader : true
					},
					type : 'POST',
					success : function() {
						console.log('Note Number Loaded Successfully');
					},
					error : function() {
						console.log('Error Loading Note Number');

					}
				});
			  
			  
			  $(document).ready(function() {
			  
			  $.getJSON('/server/jsonfiles/NoteNumber.json',function(data){
					var jsonData = data['data'];
					var NoteNumber;
					$.each(jsonData,function(key,val){
						NoteNumber = val.NoteNumber;
					});
					console.log(NoteNumber);
					$("#noteNo").val(NoteNumber + 1);
				});
			});
			  
			// set current date to debit credit using datepicker
			$(function() {
				$("#debitCreditDate").datepicker({
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

			
			
			
			
			
				$('#company').attr('disabled', true);
				$('#companyChecked').attr('disabled', true);
				$('#optionsRadios1').on('click', function(){
					$('#customerChecked').prop('checked', true);
					$('#customerChecked').attr('disabled', false);
					$('#companyChecked').attr('disabled', true);
					$('#customer').attr('disabled', false);
					$('#company').attr('disabled', true);
				});
				
				$('#optionsRadios2').on('click', function(){
					$('#companyChecked').prop('checked', true);
					$('#customerChecked').attr('disabled', true);
					$('#companyChecked').attr('disabled', false);
					$('#customer').attr('disabled', true);
					$('#company').attr('disabled', false);
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
//								alert(data);
							}
						},
						error : function(xhr, ajaxOptions, thrownError) {
							alert("Insert Form Error");
						}
					});

				});

			});
		
		
			// select data from json file
			function selectAllAjax() {
				if (actionURL !== 'DebitCreditNoteServlet') {
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