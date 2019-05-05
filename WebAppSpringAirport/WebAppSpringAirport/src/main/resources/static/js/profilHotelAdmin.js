/**
 * 
 */

function findAllUslugeByHotel(){
	$.ajax({
		type:'GET',
		url:'api/uslugeHotela/'+localStorage.getItem('hotel_id'),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderUsluge
	});
}
function findAllByHotel(){
	$.ajax({
		type:'GET',
		url:'api/sobeHotela/'+localStorage.getItem('hotel_id'),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderSobe,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("okdosdkaasdd");
			alert(errorThrown);
		}
	});
}

function renderUsluge(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");
	
	if(uloga=="ROLE_HOTEL"){
		var th_nbsp=$('<th colspan="2">&nbsp;</th>');
		$('#prikazUslugaTabela').find('tr:eq(0)').append(th_nbsp);
	}
	$("#prikazUslugaTabela").find("tr:gt(0)").remove();
	$("#prikazUslugaTabela").find("th:gt(2)").remove();
	$.each(list, function(index, usluga){
		var tr=$('<tr id="usluga_'+usluga.id+'"></tr>');
		tr.append('<td>' + usluga.opis + '</td>' + '<td>' + usluga.cena + '</td>');
		if (uloga == "ROLE_HOTEL") {
			var formaObrisi = $('<form id="formaObrisiUslugu" onsubmit="formaObrisi(event, this, \'identifikatorUsluga\', \'usluga\')"></form>');
			formaObrisi.append('<input type="hidden" value="' + usluga.id + '">');
			formaObrisi.append('<input id="hiddenUsluga" type="hidden" value="' + usluga.opis+ '">');
			formaObrisi.append('<input type="submit" value="Delete" onclick="otvoriModal(\'id05\')">');
			var td = $('<td></td>');
			td.append(formaObrisi);
			tr.append(td);
		
			var formaUpdate = $('<form id="formaUpdateUsluga"></form>');
			formaUpdate.append('<input type="hidden" value="' + usluga.id + '">');
			formaUpdate.append('<input type="submit" value="Update" onclick="otvoriModal(\'id06\')">');
			var td1 = $('<td></td>');
			td1.append(formaUpdate);
			tr.append(td1);
		}
		$('#prikazUslugaTabela tbody').append(tr);
	})
}
function renderSobe(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	console.log(data);
	uloga=localStorage.getItem("uloga");
	
	if(uloga=="ROLE_HOTEL"){
		var th_nbsp=$('<th colspan="2">&nbsp;</th>');
		$('#prikazSobaTabela').find('tr:eq(0)').append(th_nbsp);
	}
	$("#prikazSobaTabela").find("tr:gt(0)").remove();
	$("#prikazSobaTabela").find("th:gt(4)").remove();
	$.each(list, function(index, soba){
		var tr=$('<tr id="soba_'+soba.id+'"></tr>');
		var slika=soba.slika;
		if(slika==null){
			slika = "../slike/hotel.jpg";
		}
		tr.append('<td align="center" width=100px height=100px>'+ '<div id="divSoba" class="divEntitet">' +
				'<img src=" ' + slika +' " id="imgProfilnaSoba" class="imgEntitet"> ' + '</div>' +
				'</td>' + '<td>' + soba.brojKreveta + '</td>'
				+ '<td class="opisEntitet">' + soba.opis + '</td>' + '<td>' + soba.ocena + '</td>');
		if (uloga == "ROLE_HOTEL") {
			var formaObrisi = $('<form id="formaObrisiSobu" onsubmit="formaObrisi(event, this, \'identifikatorSoba\', \'soba\')"></form>');
			formaObrisi.append('<input type="hidden" value="' + soba.id + '">');
			formaObrisi.append('<input id="hiddenSoba" type="hidden" value="' + soba.opis+ '">');
			formaObrisi.append('<input type="submit" value="Delete" onclick="otvoriModal(\'id01\')">');
			var td = $('<td></td>');
			td.append(formaObrisi);
			tr.append(td);
		
			var formaUpdate = $('<form id="formaUpdateSoba"></form>');
			formaUpdate.append('<input type="hidden" value="' + soba.id + '">');
			formaUpdate.append('<input type="submit" value="Update" onclick="otvoriModal(\'id04\')">');
			var td1 = $('<td></td>');
			td1.append(formaUpdate);
			tr.append(td1);
		}
		$('#prikazSobaTabela').append(tr);
	})
}
$(document).on('submit', ".modal-content3", function(e){
	e.preventDefault();
	console.log("dodavanje sobe");
	var opis=$("#opisSobe").val();
	var broj_kreveta=$("#brojKreveta").val();
	var slika=$('#slika_room').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	console.log("aaa"+opis);
	console.log("sss"+naziv);
	console.log("cccc"+adresa);
	
	var id_hotela=localStorage.getItem("hotel_id");
	$.ajax({
		type:'POST',
		url:"api/sobe",
		contentType:'application/json',
		dataType:'json',
		data:sobaToJSONadd(opis, slika, broj_kreveta, id_hotela),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			$("#id03").css("display", "none");
			$("body").removeClass("modal-open");
			//vizuelno prikazati dodatu sobu
			ponistavanje('newRoomForma');
			findAllByHotel();
			//window.location.replace("profilROLE_HOTEL.html");
			
		}
	});
})

$(document).on('submit', ".modal-content2", function(e){
	e.preventDefault();
	console.log("dodavanje usluge");
	var opis=$("#opisUsluge").val();
	var cena=$("#cenaUsluge").val();
	console.log(cena);
	var id_hotela=localStorage.getItem("hotel_id");
	$.ajax({
		type:'POST',
		url:"api/usluge",
		contentType:'application/json',
		dataType:'json',
		data:uslugaToJSONadd(opis, cena, id_hotela),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			$("#id02").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('newUslugaForma');
			
			dodajNoviEntitet('prikazUslugaTabela', get_trow(data));
			//findAllUslugeByHotel();
			
		}
	});
})
function get_trow(data){
	var tr=$('<tr class="anim highlight"></tr>');
	tr.append('<td>' + data.opis + '</td>' + '<td>' + data.cena + '</td>');
	var formaObrisi = $('<form id="formaObrisiUslugu" onsubmit="formaObrisi(event, this, \'identifikatorUsluga\', \'usluga\')"></form>');
	formaObrisi.append('<input type="hidden" value="' + usluga.id + '">');
	formaObrisi.append('<input id="hiddenUsluga" type="hidden" value="' + usluga.opis+ '">');
	formaObrisi.append('<input type="submit" value="Delete" onclick="otvoriModal(\'id05\')">');
	var td = $('<td></td>');
	td.append(formaObrisi);
	tr.append(td);

	var formaUpdate = $('<form id="formaUpdateUsluga"></form>');
	formaUpdate.append('<input type="hidden" value="' + usluga.id + '">');
	formaUpdate.append('<input type="submit" value="Update" onclick="otvoriModal(\'id06\')">');
	var td1 = $('<td></td>');
	td1.append(formaUpdate);
	tr.append(td1);
	return tr;
}
$(document).on('submit', "#deleteSobaForma", function(e){
	e.preventDefault();
	var id=$("#identifikatorSoba").val();
	$.ajax({
		type : 'DELETE',
		url : '/api/sobe/' + id,
		/*
		 * Milan: drugi nacin da token posaljete u zahtevu kroz ajax poziv kroz
		 * header
		 */
		headers : {
			Authorization : "Bearer " + localStorage.getItem("accessToken")
		},
		success : function() {
			console.log('blaa');
			$('#soba_' + id).remove();
			$("#id01").css("display", "none");
			$("body").removeClass("modal-open");
			//findAllByHotel();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("okdosdkaasdd");
			alert(errorThrown);
		}
	})
	
})

$(document).on('submit', "#deleteUslugaForma", function(e){
	e.preventDefault();
	var id=$("#identifikatorUsluga").val();
	$.ajax({
		type : 'DELETE',
		url : '/api/usluge/' + id,
		/*
		 * Milan: drugi nacin da token posaljete u zahtevu kroz ajax poziv kroz
		 * header
		 */
		headers : {
			Authorization : "Bearer " + localStorage.getItem("accessToken")
		},
		success : function() {
			console.log('blaa');
			$('#usluga_' + id).remove();
			$("#id05").css("display", "none");
			$("body").removeClass("modal-open");
			//findAllByHotel();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("okdosdkaasdd");
			alert(errorThrown);
		}
	})
	
})

function sobaToJSONadd(opis, slika, broj_kreveta, id_hotela){
	return JSON.stringify({
		"opis":opis,
		"slika":slika,
		"brojKreveta":broj_kreveta,
		"idHotela":id_hotela
	});
}
function uslugaToJSONadd(opis, cena, id_hotela){
	return JSON.stringify({
		"opis":opis,
		"cena":cena,
		"hotel_id":id_hotela
	});
}


