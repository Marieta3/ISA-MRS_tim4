/**
 * 
 */

var update_rentacar_url="api/rentACars";

$(document).on('click', '#cancel_rent', function(e){
	e.preventDefault();
	dobaviPodatkeRenta();
})
dobaviPodatkeRenta();
function dobaviPodatkeRenta(){
	$.ajax({
		type:'GET',
		url:'api/myrent',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	console.log(data);
        	localStorage.setItem("rent_id", data.id);
        	$("#rentNazivAdmin").text(data.naziv);
        	if(data.slika!=null && data.slika!=""){
        		$("#rent_img").attr("src", data.slika);
        	}else{
        		$("#rent_img").attr("src", '../slike/rent.jpg');
        	}
        	//$("#rating_rent").text("Rating: "+data.ocena);
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
        	$('#rating_rent').text(rating);
        	$("#naziv").val(data.naziv);
        	$("#adresa").val(data.adresa);
        	$("#opis").val(data.opis);
        	$('#identifikator_rent').val(data.id);
        }
	})
}

$(document).on('submit', "#updateRentForma", function(e){
	e.preventDefault();
	console.log("update rent-a-car");
	var id = $('#identifikator_rent').val();
	var naziv=$('#naziv').val();
	var adresa=$('#adresa').val();
	var opis=$('#opis').val();
	var slika = $('#slika_rent').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	console.log("update rent  slika  "+slika);
	if(slika=="" || slika==null){
		console.log("update rent  slika  null"+slika);
		slika=$("#rent_img").attr("src");
		console.log("update rent  slika  nije null"+slika);
	}
	$.ajax({
		type:'PUT',
		url:update_rentacar_url+"/"+id,
		contentType:'application/json',
		dataType:'text',
		data:rentacarToJSON(id,naziv, adresa, opis, slika),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			alert("Successfully saved changes!");
		}
		, error: function(XMLHttpRequest,textStatus, errorThrown){
			alert("No such rent!"+errorThrown);
		}
	});
});

function rentacarToJSON(id,naziv, adresa, opis, slika){
	return JSON.stringify({
		"id":id,
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis, 
		"slika":slika
	});
}