/**
 * 
 */
function otvoriModal(modal){
	console.log('otvoren modal: '+modal);
	$("#"+modal).css("display", "block");
	$("body").addClass("modal-open");
}

function zatvoriModal(modal){
	console.log('zatvoren modal: '+modal);
	$("#"+modal).css("display", "none");
	ponistavanje($("#"+modal).find('form').attr('id'));
	$("body").removeClass("modal-open");
}

$(window).click(function(e){
	//console.log('zatvoren modallll ');
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

function formaObrisi(e, forma, id_delete, txt_delete){
	//id_delete: id input taga koji je hidden i koji sadrzi id onoga sto se brise
	//txt_delete: id polja gde stoji naziv onoga sto se brise
	e.preventDefault();
	var id, txt; 
	$(forma).find('input[type=hidden]').each(function(index){
		if(index==0) id=this.value;
		else if(index==1) txt=this.value;
	})
	$("#"+id_delete).val(id);
	$("#"+txt_delete).text(txt+"?");
}

function dodajNoviEntitet(tabela, tr){
	$(tr)
    .hide()
    .prependTo($('#'+tabela+' tbody'))
    .fadeIn("slow")
    .addClass('normal')
    .removeClass('highlight');
}