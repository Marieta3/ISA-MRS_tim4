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
        	$('#rent_coords').val(data.coord1+','+data.coord2);
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
	var coords=$('#rent_coords').val();
	var coord_list=coords.split(',');
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
		data:rentacarToJSON(id,naziv, adresa, opis, slika, coord_list[0], coord_list[1]),
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
		}
		, error: function(XMLHttpRequest,textStatus, errorThrown){
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
});

function rentacarToJSON(id,naziv, adresa, opis, slika, coord1, coord2){
	return JSON.stringify({
		"id":id,
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis, 
		"slika":slika,
		"coord1":coord1,
		"coord2":coord2
	});
}