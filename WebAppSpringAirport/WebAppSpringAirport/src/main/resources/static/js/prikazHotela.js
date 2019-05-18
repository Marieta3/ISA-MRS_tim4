/**
 * 
 */
uloga="";

$(document).ready(function(){
	uloga=localStorage.getItem("uloga");
	if(uloga=="ROLE_ADMIN"){
		//$("#nav-bar").append('<li><a href="dodajHotel.html">New Hotel</a></li>');
		$("#nav-bar").append('<li><button class="dodajHotelBtn" onclick="otvoriModal(\'id03\')">New Hotel</button></li>');
	}
});
findAll();
function findAll() {
	$.ajax({
		type : 'GET',
		url : 'api/hotels',
		dataType : 'json',
		/*
		 * Milan: jedan nacin da token koji ste dobili prilikom logina posaljete
		 * kroz ajax zahtev kroz header
		 */
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success : renderHoteli
	})
}

function renderHoteli(data) {
	// console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");
	
	if(uloga=="ROLE_ADMIN"){
		var th_nbsp=$('<th>&nbsp;</th>');
		var th_nbsp1=$('<th>&nbsp;</th>');
		$('#prikazHotelaTabela').find('tr:eq(0)').append(th_nbsp);
		$('#prikazHotelaTabela').find('tr:eq(0)').append(th_nbsp1);
	}
	$("#prikazHotelaTabela").find("tr:gt(0)").remove();
	$("#prikazHotelaTabela").find("th:gt(6)").remove();
	$.each(list, function(index, hotel) {
		/*
		var tr = $('<tr id="hotel_' + hotel.id + '"></tr>');
		if (hotel.slika == null) {
			hotel.slika = "../slike/hotel.jpg";
		}
		tr.append('<td align="center" width=100px height=100px>'+ '<div id="divHotel" class="divEntitet">' +
				'<img src=" ' + hotel.slika +' " id="imgProfilnaHotel" class="imgEntitet"> ' + '</div>' +
				'</td>' + '<td>' + hotel.naziv + '</td>'
				+ '<td>' + hotel.adresa + '</td>' + '<td>' + hotel.opis
				+ '</td>' + '<td>' + hotel.ocena + '</td>');

		if (uloga == "ROLE_ADMIN") {
			var formaObrisi = $('<form id="formaObrisi" onsubmit="formaObrisi(event, this, \'identifikator\', \'nazivAdresaHotela\')"></form>');
			formaObrisi.append('<input type="hidden" value="' + hotel.id + '">');
			formaObrisi.append('<input id="hiddenNazivAdresa" type="hidden" value="' + hotel.naziv+", "+hotel.adresa + '">');
			formaObrisi.append('<input type="submit" value="Delete" onclick="otvoriModal(\'id02\')">');
			var td = $('<td></td>');
			td.append(formaObrisi);
			tr.append(td);
		
			var formaUpdate = $('<form id="formaUpdate"></form>');
			formaUpdate.append('<input type="hidden" value="' + hotel.id + '">');
			formaUpdate.append('<input type="submit" value="Update" onclick="otvoriModal(\'id01\')">');
			var td1 = $('<td></td>');
			td1.append(formaUpdate);
			tr.append(td1);
		}*/
		$('#prikazHotelaTabela').append(get_row(hotel, "hotel", localStorage.getItem('uloga'), 'id02', 'id01'));
		
	})
	$('#prikazHotelaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });
	
}


function formaUpdatehotel(e, forma){
	e.preventDefault();
	var id_hotela = $(forma).find('input[type=hidden]').val();
	$.ajax({
		type:"GET",
		url:"api/hotels/"+id_hotela,
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	$("#h1NazivHotela").text(data.naziv+", "+data.adresa);
        	$("#nazivHotela").val(data.naziv);
        	$("#adresaHotela").val(data.adresa);
        	$("#opisHotela").val(data.opis);
        	$("#identifikator").val(data.id);
        }
		
	})
}

$(document).on('submit', ".modal-content1", function(e){
	e.preventDefault();
	var naziv=$("#nazivHotela").val();
	var adresa=$("#adresaHotela").val();
	var opis=$("#opisHotela").val();
	var id=$("#identifikator").val();
	$.ajax({
		type:"PUT",
		url:"api/hotels/"+id,
		contentType:'application/json',
		dataType:'text',
		data:hotelToJSON(id, naziv, adresa, opis),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	zatvoriModal('id01')
			$('#hotel_'+id).remove();
			//dodajNoviEntitet('prikazHotelaTabela', get_row($.parseJSON(data), "hotel", localStorage.getItem('uloga'), 'id02', 'id01'));
        	$('#prikazHotelaTabela').DataTable().clear().destroy();
			findAll();
        }
	})
	
})
$(document).on('submit', ".modal-content2", function(e){
	e.preventDefault();
	var id=$("#identifikator").val();
	$.ajax({
		type : 'DELETE',
		url : '/api/hotels/' + id,
		/*
		 * Milan: drugi nacin da token posaljete u zahtevu kroz ajax poziv kroz
		 * header
		 */
		headers : {
			Authorization : "Bearer " + localStorage.getItem("accessToken")
		},
		success : function() {
			console.log('blaa');
			$('#hotel_' + id).remove();
			$("#id02").css("display", "none");
			$("body").removeClass("modal-open");
			$('#prikazHotelaTabela').DataTable().clear().destroy();
			findAll();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("okdosdkaasdd");
			alert(errorThrown);
		}
	})
	
})

$(document).on('submit', "#newHotelForma", function(e){
	e.preventDefault();
	console.log("dodavanje hotela");
	var naziv=$("#nazivHotela1").val();
	var adresa=$("#adresaHotela1").val();
	var opis=$("#opisHotela1").val();
	var slika=$('#slikaEdit').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	console.log("aaa"+opis);
	console.log("sss"+naziv);
	console.log("cccc"+adresa);
	$.ajax({
		type:'POST',
		url:"api/hotels",
		contentType:'application/json',
		dataType:'json',
		data:hotelToJSONadd(naziv, adresa, opis, slika),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			$("#id03").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('newHotelForma');
			$('#prikazHotelaTabela').DataTable().clear().destroy();
			findAll();
			//get_row(data);
			//dodajNoviEntitet('prikazHotelaTabela', get_row(data, "hotel", localStorage.getItem('uloga'), 'id02', 'id01'));
		}
	});
})
function hotelToJSONadd(naziv, adresa, opis, slika){
	return JSON.stringify({
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis,
		"slika":slika
	});
}
function hotelToJSON(id, naziv, adresa, opis){
	return JSON.stringify({
		"id":id,
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis,
	});
}




$('document').ready(function() {
	$('#btnSearch').bind('click', searchHotelsByName);
});


function searchHotelsByName() {
	var naziv = $('#searchName').val();
	console.log(naziv);

	if (naziv) {
		$.ajax({
			type : 'GET',
			url : 'api/hotels/search/' + naziv,
			dataType : 'json',
			success : renderHoteli
		});
	} else{
		findAll();
	}
}
