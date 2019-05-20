/**
 * 
 */
var update_avio_kompaniju_url="api/avioKompanije";

$(document).on('click', '#cancel_avio', function(e){
	e.preventDefault();
	dobaviPodatkeAvio();
})
dobaviPodatkeAvio();
function dobaviPodatkeAvio(){
	$.ajax({
		type:'GET',
		url:'api/myavio',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	console.log(data);
        	localStorage.setItem("avio_id", data.id);
        	$("#avioNaziv").text(data.naziv);
        	if(data.slika!=null && data.slika!=""){
        		$("#avio_img").attr("src", data.slika);
        	}else{
        		$("#avio_img").attr("src", '../slike/hotel.jpg');
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
        	$('#rating_avio').text(rating);
        	$("#naziv").val(data.naziv);
        	$("#adresa").val(data.adresa);
        	$("#opis").val(data.opis);
        	$('#identifikator_avio').val(data.id);
        }
	})
}

$(document).on('submit', "#updateAvioKompanijuForma", function(e){
	e.preventDefault();
	console.log("dodavanje avio");
	var id=$('#identifikator_avio').val();
	var naziv=$('#naziv').val();
	var adresa=$('#adresa').val();
	var opis=$('#opis').val();
	console.log(naziv);
	var slika = $('#slika_avio').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	console.log("update avio  slika  "+slika);
	if(slika=="" || slika==null){
		slika=$("#avio_img").attr("src");
	}
	$.ajax({
		type:'PUT',
		url:update_avio_kompaniju_url+'/'+id,
		contentType:'application/json',
		dataType:'text',
		data:avioToJSON(id, naziv, adresa, opis, slika),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			$.bootstrapGrowl("Successfully saved changes!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		}, 
		error: function(XMLHttpRequest,textStatus, errorThrown) {
			$.bootstrapGrowl("An error occured!", {
				  ele: 'body', // which element to append to
				  type: 'danger', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 2000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		}
	});
})


function avioToJSON(id, naziv, adresa, opis, slika){
	return JSON.stringify({
		"id":id,
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis,
		"slika":slika
	});
}