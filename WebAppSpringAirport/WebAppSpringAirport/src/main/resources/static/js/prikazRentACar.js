/**
 * 
 */
uloga="";

$(document).ready(function(){
	uloga=localStorage.getItem("uloga");
	if(uloga=="ROLE_ADMIN"){
		$("#nav-bar").append('<li><button class="dodajRentBtn" onclick="otvoriModal(\'id03\')">New Rent-A-Car</button></li>');
	}
});

findAll();
function findAll() {
	$.ajax({
		type : 'GET',
		url : 'api/rentACars',
		dataType : 'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success : renderRentACars
	})
}

function renderRentACars(data) {
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");
	if(uloga=="ROLE_ADMIN"){
		var th_nbsp=$('<th colspan="2">&nbsp;</th>');
		$('#prikazRentACarTabela').find('tr:eq(0)').append(th_nbsp);
	}
	
	if(list.length == 0){
		console.log("Not found data");
		alert("No rent-a-car found!")
		return;
	}

	$("#prikazRentACarTabela").find("tr:gt(0)").remove();
	$("#prikazRentACarTabela").find("th:gt(5)").remove();
	$.each(list, function(index, rentACar) {
		/*var tr = $('<tr id="rent_' + rentACar.id + '"></tr>');
		if (rentACar.slika == null) {
			rentACar.slika = "../slike/rent_car.jpg"
		}
		tr.append('<td align="center" width=100px height=100px>'+ '<div id="divRent" class="divEntitet">' +
				'<img src=" ' + rentACar.slika +' " id="imgProfilnaRent" class="imgEntitet"> ' + '</div>' +
				'</td>' +'<td>'+ rentACar.naziv
				+ '</td>' + '<td>' + rentACar.adresa + '</td>' + '<td width=100px>'
				+ rentACar.opis + '</td>');
		if (uloga == "ROLE_ADMIN") {
			var formaObrisi = $('<form id="formaObrisi" onsubmit="formaObrisi(event, this, \'identifikator\', \'nazivAdresaRent\')"></form>');
			formaObrisi.append('<input type="hidden" value="' + rentACar.id + '">');
			formaObrisi.append('<input id="hiddenNazivAdresa" type="hidden" value="' + rentACar.naziv+", "+rentACar.adresa + '">');
			formaObrisi.append('<input type="submit" value="Delete" onclick="otvoriModal(\'id02\')">');
			var td = $('<td></td>');
			td.append(formaObrisi);
			tr.append(td);
		
			var formaUpdate = $('<form id="formaUpdate"></form>');
			formaUpdate.append('<input type="hidden" value="' + rentACar.id + '">');
			formaUpdate.append('<input type="submit" value="Update" onclick="otvoriModal(\'id01\')">');
			var td1 = $('<td></td>');
			td1.append(formaUpdate);
			tr.append(td1);
		}*/
		$('#prikazRentACarTabela tbody').append(get_row(rentACar, "rent", localStorage.getItem('uloga'), 'id02', 'id01'));

	})
}



function formaUpdaterent(e, forma) {
	e.preventDefault();
	var id = $(forma).find('input[type=hidden]').val();
	console.log(id);
	$.ajax({
		type:"GET",
		url:"api/rentACars/"+id,
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	$("#h1NazivRent").text(data.naziv+", "+data.adresa);
        	$("#nazivRent").val(data.naziv);
        	$("#adresaRent").val(data.adresa);
        	$("#opisRent").val(data.opis);
        	
        	$("#identifikator").val(data.id);
        }
		
	})
}

$(document).on('submit', ".modal-content1", function(e){
	e.preventDefault();
	var naziv=$("#nazivRent").val();
	var adresa=$("#adresaRent").val();
	var opis=$("#opisRent").val();
	//var ocena=$("#ocenaHotela").val();
	var id=$("#identifikator").val();
	$.ajax({
		type:"PUT",
		url:"api/rentACars/"+id,
		contentType:'application/json',
		dataType:'text',
		data:rentToJSON(id, naziv, adresa, opis),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	zatvoriModal('id01')
			$('#rent_'+id).remove();
			dodajNoviEntitet('prikazRentACarTabela', get_row($.parseJSON(data), "rent", localStorage.getItem('uloga'), 'id02', 'id01'));
        }
	})
	
})
$(document).on('submit', ".modal-content2", function(e){
	e.preventDefault();
	var id=$("#identifikator").val();
	$.ajax({
		type : 'DELETE',
		url : '/api/rentACars/' + id,
		/*
		 * Milan: drugi nacin da token posaljete u zahtevu kroz ajax poziv kroz
		 * header
		 */
		headers : {
			Authorization : "Bearer " + localStorage.getItem("accessToken")
		},
		success : function() {
			console.log('blaa');
			$('#rent_' + id).remove();
			$("#id02").css("display", "none");
			$("body").removeClass("modal-open");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("okdosdkaasdd");
			alert(errorThrown);
		}
	})
	
})

$(document).on('submit', "#newRentForma", function(e){
	e.preventDefault();
	console.log("dodavanje rent kompanije");
	var naziv=$("#nazivRent1").val();
	var adresa=$("#adresaRent1").val();
	var opis=$("#opisRent1").val();
	console.log("aaa"+opis);
	console.log("sss"+naziv);
	console.log("cccc"+adresa);
	$.ajax({
		type:'POST',
		url:"api/rentACars",
		contentType:'application/json',
		dataType:'json',
		data:rentToJSONadd(naziv, adresa, opis),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			$("#id03").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('newRentForma');
			//get_row(data);
			dodajNoviEntitet('prikazRentACarTabela', get_row(data, "rent", localStorage.getItem('uloga'), 'id02', 'id01'));
		}
	});
})
function rentToJSONadd(naziv, adresa, opis){
	return JSON.stringify({
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis
	});
}
function rentToJSON(id, naziv, adresa, opis){
	return JSON.stringify({
		"id":id,
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis
	});
}


$('document').ready(function() {
	$('#btnSearch').bind('click', searchCars);
});

function searchCars() {
	var naziv = $('#searchName').val();
	console.log(naziv);

	if (naziv) {
		$.ajax({
			type : 'GET',
			url : 'api/rentACars/search/' + naziv,
			dataType : 'json',
			success : renderRentACars
		});
	} else{
		findAll();
	}
}