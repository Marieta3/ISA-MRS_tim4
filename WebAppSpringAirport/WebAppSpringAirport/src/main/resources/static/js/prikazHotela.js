/**
 * 
 */
uloga="";

$(document).ready(function(){
	uloga=localStorage.getItem("uloga");
	if(uloga=="ROLE_ADMIN"){
		//$("#nav-bar").append('<li><a href="dodajHotel.html">New Hotel</a></li>');
		$("#nav-bar").append('<li><button class="dodajHotelBtn">New Hotel</button></li>');
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
	//sta ako ne postoji uloga u local storage?
	if(uloga=="ROLE_ADMIN"){
		var th_nbsp=$('<th colspan="2">&nbsp;</th>');
		$('#prikazHotelaTabela').find('tr:eq(0)').append(th_nbsp);
	}
	$.each(list, function(index, hotel) {
		var tr = $('<tr id="hotel_' + hotel.id + '"></tr>');
		if (hotel.slika == null) {
			hotel.slika = "../slike/hotel.jpg";
		}
		tr.append('<td align="center" width=100px height=100px>'+ '<div id="divHotel">' +
				'<img src=" ' + hotel.slika +' " id="imgProfilnaHotel"> ' + '</div>' +
				'</td>' + '<td>' + hotel.naziv + '</td>'
				+ '<td>' + hotel.adresa + '</td>' + '<td>' + hotel.opis
				+ '</td>' + '<td>' + hotel.ocena + '</td>');
		console.log(uloga);
		if (uloga == "ROLE_ADMIN") {
			var formaObrisi = $('<form id="formaObrisi"></form>');
			formaObrisi.append('<input type="hidden" value="' + hotel.id + '">');
			formaObrisi.append('<input id="hiddenNazivAdresa" type="hidden" value="' + hotel.naziv+", "+hotel.adresa + '">');
			formaObrisi.append('<input type="submit" value="Delete">');
			var td = $('<td></td>');
			td.append(formaObrisi);
			tr.append(td);
		
			var formaUpdate = $('<form id="formaUpdate"></form>');
			formaUpdate.append('<input type="hidden" value="' + hotel.id + '">');
			formaUpdate.append('<input type="submit" value="Update">');
			var td1 = $('<td></td>');
			td1.append(formaUpdate);
			tr.append(td1);
		}
		$('#prikazHotelaTabela').append(tr);
	})
	
}

$(document).on('click', '.dodajHotelBtn', function(e){
	e.preventDefault();
	console.log("dodaj hotel btn");
	$("#id03").css("display", "block");
	$("body").addClass("modal-open");
})

$(document).on('submit', '#formaObrisi', function(e) {
	e.preventDefault();
	var id_hotela = $(this).find('input[type=hidden]').val();
	var naziv_adresa=$(this).find("#hiddenNazivAdresa").val();
	console.log(id_hotela);
	$("#id02").css("display", "block");
	$("body").addClass("modal-open");
	$("#identifikator").val(id_hotela);
	$("#nazivAdresaHotela").text(naziv_adresa+"?");
	

})

$(document).on('submit', '#formaUpdate', function(e) {
	e.preventDefault();
	$("#id01").css("display", "block");
	$("body").addClass("modal-open");
	var id_hotela = $(this).find('input[type=hidden]').val();
	console.log(id_hotela);
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
})

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
        	window.location.replace("prikazHotela.html");
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
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("okdosdkaasdd");
			alert(errorThrown);
		}
	})
	
})

$(document).on('submit', ".modal-content3", function(e){
	e.preventDefault();
	console.log("dodavanje hotela");
	var naziv=$("#nazivHotela1").val();
	var adresa=$("#adresaHotela1").val();
	var opis=$("#opisHotela1").val();
	console.log("aaa"+opis);
	console.log("sss"+naziv);
	console.log("cccc"+adresa);
	$.ajax({
		type:'POST',
		url:"api/hotels",
		contentType:'application/json',
		dataType:'json',
		data:hotelToJSONadd(naziv, adresa, opis),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			//$("#id03").css("display", "none");
			//$("body").removeClass("modal-open");
			window.location.replace("prikazHotela.html");
		}
	});
})
function hotelToJSONadd(naziv, adresa, opis){
	return JSON.stringify({
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis
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

function previewFile(){
      var preview = document.querySelector('img'); //selects the query named img
      var file    = document.querySelector('input[type=file]').files[0]; //sames as here
      var reader  = new FileReader();
 
      reader.onloadend = function () {
           preview.src = reader.result;
      }

      if (file) {
           reader.readAsDataURL(file); //reads the data as a URL
      } else {
           preview.src = "";
      }
      //$('.id03').find('.imgcontainer').find('.imagePicker1').find('img').attr('src', preview.src);
      //console.log($('.id03').find('.imagePicker1').find('img'));
       //previewFile();  //calls the function named previewFile()
}
