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
        	
        	$('.cornerimage').css("width", (rating/5)*100+"%");
        	$('#rating_hotel').text(rating);
        	$("#naziv").val(data.naziv);
        	$("#adresa").val(data.adresa);
        	$("#opis").val(data.opis);
        	$('#identifikator_hotel').val(data.id);
        	$('#hotel_coords').val(data.coord1+','+data.coord2);
        	
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
	var coords=$('#hotel_coords').val();
	var coord_list=coords.split(',');
	console.log("prva koordinata: "+coord_list[0]);
	console.log('koordinate update hotela: '+coords);
	var slika = $('#slika_hotel').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	console.log("update hotel  slika  "+slika);
	if(slika=="" || slika==null){
		slika=$("#hotel_img").attr("src");
	}
	$.ajax({
		type:'PUT',
		url:update_hotela_url+"/"+id,
		contentType:'application/json',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		data:hotelToJSON(id,naziv, adresa, opis, slika, coord_list[0], coord_list[1]),
		success:function(data){
			$.bootstrapGrowl("Succesfully saved changes!", {
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
			, error: function(XMLHttpRequest,textStatus, errorThrown) {
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

function hotelToJSON(id,naziv, adresa, opis, slika, coord1, coord2){
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