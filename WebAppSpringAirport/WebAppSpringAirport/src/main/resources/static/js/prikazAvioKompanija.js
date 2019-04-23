/**
 * 
 */
uloga="";

$(document).ready(function(){
	uloga=localStorage.getItem("uloga");
	if(uloga=="ROLE_ADMIN"){
		//$("#nav-bar").append('<li><a href="dodajAvioKompaniju.html">New Airline</a></li>');
		$("#nav-bar").append('<li><button class="dodajAvioBtn">New Airline</button></li>');
	}
});

findAll();
function findAll(){
	$.ajax({
		type:'GET',
		url:'api/avioKompanije',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:renderAvioKompanije
	})
}

function renderAvioKompanije(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");
	//sta ako ne postoji uloga u local storage?
	if(uloga=="ROLE_ADMIN"){
		var th_nbsp=$('<th colspan="2">&nbsp;</th>');
		$('#prikazAvioKompanijaTabela').find('tr:eq(0)').append(th_nbsp);
	}
	$("#prikazAvioKompanijaTabela tbody").empty();
	$.each(list, function(index, avioKompanija){
		var tr=$('<tr id="avio_' + avioKompanija.id + '"></tr>');
		if (avioKompanija.slika == null){
			avioKompanija.slika = "../slike/aereo2.jpg";
		}
		tr.append('<td align="center" width=100px height=100px>'+ '<div id="divAviokompanija">' +
				'<img src=" ' + avioKompanija.slika +' " id="imgProfilnaAviokompanija"> ' + '</div>' +
				'</td>'+'<td>' + avioKompanija.naziv + '</td>' + '<td>'
				+ avioKompanija.adresa + '</td>' + '<td>'
				+ avioKompanija.opis + '</td>'  );
		
		if (uloga == "ROLE_ADMIN") {
			var formaObrisi = $('<form id="formaObrisi"></form>');
			formaObrisi.append('<input type="hidden" value="' + avioKompanija.id + '">');
			formaObrisi.append('<input id="hiddenNazivAdresa" type="hidden" value="' + avioKompanija.naziv+", "+avioKompanija.adresa + '">');
			formaObrisi.append('<input type="submit" value="Delete">');
			var td = $('<td></td>');
			td.append(formaObrisi);
			tr.append(td);
		
			var formaUpdate = $('<form id="formaUpdate"></form>');
			formaUpdate.append('<input type="hidden" value="' + avioKompanija.id + '">');
			formaUpdate.append('<input type="submit" value="Update">');
			var td1 = $('<td></td>');
			td1.append(formaUpdate);
			tr.append(td1);
		}
		$('#prikazAvioKompanijaTabela').append(tr);
		
	})
}

$(document).on('click', '.dodajAvioBtn', function(e){
	e.preventDefault();
	console.log("dodaj avio btn");
	$("#id03").css("display", "block");
	$("body").addClass("modal-open");
})

$(document).on('submit', '#formaObrisi', function(e) {
	e.preventDefault();
	var id = $(this).find('input[type=hidden]').val();
	var naziv_adresa=$(this).find("#hiddenNazivAdresa").val();
	console.log(id);
	$("#id02").css("display", "block");
	$("body").addClass("modal-open");
	$("#identifikator").val(id);
	$("#nazivAdresaAvio").text(naziv_adresa+"?");
	

})

$(document).on('submit', '#formaUpdate', function(e) {
	e.preventDefault();
	$("#id01").css("display", "block");
	$("body").addClass("modal-open");
	var id = $(this).find('input[type=hidden]').val();
	console.log(id);
	$.ajax({
		type:"GET",
		url:"api/avioKompanije/"+id,
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	$("#h1NazivAvio").text(data.naziv+", "+data.adresa);
        	$("#nazivAvio").val(data.naziv);
        	$("#adresaAvio").val(data.adresa);
        	$("#opisAvio").val(data.opis);
        	
        	$("#identifikator").val(data.id);
        }
		
	})
})

$(document).on('submit', ".modal-content1", function(e){
	e.preventDefault();
	var naziv=$("#nazivAvio").val();
	var adresa=$("#adresaAvio").val();
	var opis=$("#opisAvio").val();
	//var ocena=$("#ocenaHotela").val();
	var id=$("#identifikator").val();
	$.ajax({
		type:"PUT",
		url:"api/avioKompanije/"+id,
		contentType:'application/json',
		dataType:'text',
		data:avioToJSON(id, naziv, adresa, opis),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	window.location.replace("prikazAvioKompanija.html");
        }
	})
	
})
$(document).on('submit', ".modal-content2", function(e){
	e.preventDefault();
	var id=$("#identifikator").val();
	$.ajax({
		type : 'DELETE',
		url : '/api/avioKompanije/' + id,
		/*
		 * Milan: drugi nacin da token posaljete u zahtevu kroz ajax poziv kroz
		 * header
		 */
		headers : {
			Authorization : "Bearer " + localStorage.getItem("accessToken")
		},
		success : function() {
			console.log('blaa');
			$('#avio_' + id).remove();
			$("#id02").css("display", "none");
			$("body").removeClass("modal-open");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("okdosdkaasdd");
			alert(errorThrown);
		}
	})
	
})

$(document).on('submit', ".modal-content3", function(e){
	e.preventDefault();
	console.log("dodavanje avio kompanije");
	var naziv=$("#nazivAvio1").val();
	var adresa=$("#adresaAvio1").val();
	var opis=$("#opisAvio1").val();
	console.log("aaa"+opis);
	console.log("sss"+naziv);
	console.log("cccc"+adresa);
	$.ajax({
		type:'POST',
		url:"api/avioKompanije",
		contentType:'application/json',
		dataType:'json',
		data:avioToJSONadd(naziv, adresa, opis),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			//$("#id03").css("display", "none");
			//$("body").removeClass("modal-open");
			window.location.replace("prikazAvioKompanija.html");
		}
	});
})
function avioToJSONadd(naziv, adresa, opis){
	return JSON.stringify({
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis
	});
}
function avioToJSON(id, naziv, adresa, opis){
	return JSON.stringify({
		"id":id,
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis
	});
}
$(window).click(function(e){
	
	if(e.target==document.getElementById("id01")){
		$("#id01").css("display", "none");
		$("body").removeClass("modal-open");
	}else if(e.target==document.getElementById("id02")){
		$("#id02").css("display", "none");
		$("body").removeClass("modal-open");
	}else if(e.target==document.getElementById("id03")){
		$("#id03").css("display", "none");
		$("body").removeClass("modal-open");
	}
	
})

$(document).on('click', '.close', function(e){
	$("#id01").css("display", "none");
	$("#id02").css("display", "none");
	$("#id03").css("display", "none");
	$("body").removeClass("modal-open");
})

$('document').ready(function() {
	$('#btnSearch').bind('click', searchAvioByName);
});

function searchAvioByName() {
	var naziv = $('#searchName').val();
	console.log(naziv);

	if (naziv) {
		$.ajax({
			type : 'GET',
			url : 'api/avioKompanije/search/' + naziv,
			dataType : 'json',
			success : renderAvioKompanije
		});
	} else{
		findAll();
	}
}