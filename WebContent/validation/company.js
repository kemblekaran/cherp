$(function(){
	
	var company = document.getElementByName('CompanyForm');
	alert(company);
	$(company).validate({
        errorClass: "red-error",
        errorElement: "em",
        rules: {
            fname: "required",
            pri_add: "required",
            sec_add: "required",
            mobile: "required",
            phone: "required",
            state: "required",
            city: "required",
            pin_code: "required",
            own_name: "required",
            pan_no: "required",
            op_bal: "required"
        }
    });
});