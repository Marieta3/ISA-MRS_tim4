/**
 * 
 */

var update_hotela_url="api/hotels";

$(document).on('click', '#cancel_hotel', function(e){
	e.preventDefault();
	dobaviPodatkeHotela();
})
dobaviPodatkeHotela();
function dobaviPodatkeHotela(){
	$.ajax({
		type:'GET',
		url:'api/myhotel',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	console.log(data);
        	localStorage.setItem("hotel_id", data.id);
        	console.log("myhotel, hotel id: "+localStorage.getItem('hotel_id'));
        	$("#hotelNazivAdmin").text(data.naziv);
        	if(data.slika!=null && data.slika!=""){
        		$("#hotel_img").attr("src", data.slika);
        	}else{
        		$("#hotel_img").attr("src", '../slike/hotel.jpg');
        	}
        	//$("#rating_hotel").text("Rating: "+data.ocena);
        	var rating=data.ocena;
        	var stars=$('.fa');
        	$.each(stars, function(index, star){
        		$('#star'+(index+1)).removeClass('checked');
        	})
        	if(rating>=1){
        		$('#star1').addClass('checked');
        	}
        	if(rating>=2){
        		$('#star2').addClass('checked');
        	}
        	if(rating>=3){
        		$('#star3').addClass('checked');
        	}
        	if(rating>=4){
        		$('#star4').addClass('checked');
        	}
        	if(rating>=5){
        		$('#star5').addClass('checked');
        	}
        	$('.cornerimage').css("width", (rating/5)*100+"%");
        	$('#rating_hotel').text(rating);
        	$("#naziv").val(data.naziv);
        	$("#adresa").val(data.adresa);
        	$("#opis").val(data.opis);
        	$('#identifikator_hotel').val(data.id);
        }
	})
}
$(document).on('submit', "#updateHotelaForma", function(e){
	e.preventDefault();
	console.log("update hotel");
	var id = $('#identifikator_hotel').val();
	var naziv=$('#naziv').val();
	var adresa=$('#adresa').val();
	var opis=$('#opis').val();
	var slika = $('#slika_hotel').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	console.log("update hotel  slika  "+slika);
	if(slika=="" || slika==null){
		console.log("update hotel  slika  null"+slika);
		slika=$("#hotel_img").attr("src");
		console.log("update hotel  slika  nije null"+slika);
	}
	$.ajax({
		type:'PUT',
		url:update_hotela_url+"/"+id,
		contentType:'application/json',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		data:hotelToJSON(id,naziv, adresa, opis, slika),
		success:function(data){
			alert("Successfully saved changes!");
		}
			, error: function(XMLHttpRequest,textStatus, errorThrown) 
				{alert("No such hotel!"+errorThrown);
			}
	});
});

function hotelToJSON(id,naziv, adresa, opis, slika){
	return JSON.stringify({
		"id":id,
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis,
		"slika":slika
	});
}