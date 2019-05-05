/**
 * 
 */


function findAllByHotel(){
	$.ajax({
		type:'GET',
		url:'api/sobe/'+localStorage.getItem('hotel_id'),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderSobe
	});
}

function renderSobe(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$("#prikazSobaTabela").find("tr:gt(0)").remove();
	$.each(list, function(index, soba){
		var tr=$('<tr id="soba_"'+soba.id+'></tr>');
		var slika=soba.slika;
		if(slika==null){
			slika = "../slike/hotel.jpg";
		}
		tr.append('<td align="center" width=100px height=100px>'+ '<div id="divSoba" class="divEntitet">' +
				'<img src=" ' + slika +' " id="imgProfilnaSoba" class="imgEntitet"> ' + '</div>' +
				'</td>' + '<td>' + soba.brojKreveta + '</td>'
				+ '<td>' + soba.opis + '</td>' + '<td>' + soba.ocena + '</td>');
		$('#prikazSobaTabela').append(tr);
	})
}
$(document).on('submit', ".modal-content3", function(e){
	e.preventDefault();
	console.log("dodavanje dest");
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
			findAll();
			//window.location.replace("prikazHotela.html");
		}
	});
})

function sobaToJSONadd(opis, slika, broj_kreveta, id_hotela){
	return JSON.stringify({
		"opis":opis,
		"slika":slika,
		"brojKreveta":broj_kreveta,
		"idHotela":id_hotela
	});
}



