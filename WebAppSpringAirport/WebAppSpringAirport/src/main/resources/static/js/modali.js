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
function formaObrisi1(e, id_entiteta, id_modala, txt_delete){
	e.preventDefault();
	$('#'+id_modala+' input[type=hidden]').val(id_entiteta);
	$('#'+id_modala+' h1').text(txt_delete);
}

function dodajNoviEntitet(tabela, tr){
	$(tr).addClass('anim highlight')
    .hide()
    .prependTo($('#'+tabela+' tbody'))
    .fadeIn("slow")
    .addClass('normal')
    .removeClass('highlight');
}

function get_row(data, entitet, uloga, del_modal, upd_modal){
	/*
	 * entitet prosledjivati na engleskom
	 */
	console.log("get row...........");
	console.log(data);
	console.log(data.opis)
	console.log(data.ocena)
	var tr=$('<tr id="'+entitet+'_'+data.id+'"></tr>');
	if(entitet!="service"){
		var slika=data.slika;
		if(slika==null){
			if(entitet=="user"){
				slika = "../slike/avatar.png";
			}else if(entitet=="room"){
				slika = "../slike/pic1.jpg";
			}else if(entitet=="hotel"){
				slika = "../slike/hotel.jpg";
			}else if(entitet=="rent-a-car"){
				slika = "../slike/rent_car.jpg";
			}else if(entitet=="airline"){
				slika = "../slike/aereo2.jpg";
			}else{
				slika = "../slike/logo.png";
			}
		}
		tr.append('<td align="center" width=100px height=100px><div class="divEntitet"><img class="imgEntitet" src="'+slika+'"></div></td>');
	}
	$.each(data, function(attr, val){
		if(attr=="naziv" || attr=="adresa" || attr=="opis" || attr=="ocena" || attr=="cena" || attr=="brojKreveta"
			|| attr=="rezervisana" || attr=="ime" || attr=="prezime" || attr=="korisnickoIme" || attr=="main"){
			var td=$('<td>'+val+'</td>');
			console.log(td);
			tr.append(td);
				
		}
	})
	if(uloga=="ROLE_USER" || uloga==null){
		return tr;
	}else if(uloga=="ROLE_HOTEL" && (entitet!="room" && entitet!="service")){
		console.log("blaa "+uloga+", "+entitet);
		return tr;
	}/*
	 * Dopuniti za ostale korisnike
	 */
	if(entitet=="user" && uloga=="ROLE_ADMIN"){
		tr.append('<td>'+data.authorities[0].authority+'</td>');
	}
	
	var txt_delete="Are you sure you want to delete "+entitet+": ";
	var text="";
	if(entitet=="room"||entitet=="service"){
		text=data.opis;
	}else if(entitet=="user"){
		text=data.ime+" "+data.prezime+", "+data.korisnickoIme;
	}
	else{
		text=data.naziv;
	}/*
	 * Dodati za ostale entitete
	 */
	txt_delete=txt_delete+text+"?";
	var formaObrisi = $('<form id="formaObrisi" onsubmit="formaObrisi1(event, '+data.id+', \''+del_modal+'\', \''+txt_delete+'\')"></form>');

	formaObrisi.append('<input type="hidden" value="' + data.id + '">');
	
	formaObrisi.append('<input type="submit" value="Delete" onclick="otvoriModal(\''+del_modal+'\')">');
	var td = $('<td></td>');
	td.append(formaObrisi);
	tr.append(td);

	var formaUpdate = $('<form id="formaUpdateUsluga"></form>');
	formaUpdate.append('<input type="hidden" value="' + data.id + '">');
	formaUpdate.append('<input type="submit" value="Update" onclick="otvoriModal(\''+upd_modal+'\')">');
	var td1 = $('<td></td>');
	td1.append(formaUpdate);
	tr.append(td1);
	return tr;
}

