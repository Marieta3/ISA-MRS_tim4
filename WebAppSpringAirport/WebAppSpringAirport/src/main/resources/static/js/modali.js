/**
 * 
 */
function otvoriModal(modal){
	$("#"+modal).css("display", "block");
	$("body").addClass("modal-open");
}

function zatvoriModal(modal){
	$("#"+modal).css("display", "none");
	console.log($("#"+modal).find('form').attr('id'));
	ponistavanje($("#"+modal).find('form').attr('id'));
	$("body").removeClass("modal-open");
}

$(window).click(function(e){
	
	if(e.target==document.getElementById("id01")){
		$("#id01").css("display", "none");
		ponistavanje($("#id01").find('form').attr('id'));
		$("body").removeClass("modal-open");
	}else if(e.target==document.getElementById("id02")){
		$("#id02").css("display", "none");
		ponistavanje($("#id02").find('form').attr('id'));
		$("body").removeClass("modal-open");
	}else if(e.target==document.getElementById("id03")){
		$("#id03").css("display", "none");
		ponistavanje($("#id03").find('form').attr('id'));
		$("body").removeClass("modal-open");
	}
	
})

function ponistavanje(forma){
	$("#"+forma).find('img').attr('src', '../slike/avatar.png');
	$("#"+forma).find(':input').each(function() {
	    switch(this.type) {
	        case 'password':
	        case 'text':
	        case 'textarea':
	        case 'file':
	        case 'select-one':
	        case 'select-multiple':
	        case 'date':
	        case 'number':
	        case 'tel':
	        case 'email':
	            $(this).val('');
	            break;
	        case 'checkbox':
	        case 'radio':
	            this.checked = false;
	            break;
	    }
	  });
}