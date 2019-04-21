/**
 * 
 */
findAll();
function findAll(){
	$.ajax({
		type:'GET',
		url:'api/hotels',
		dataType:'json',
		/*
		 * Milan: jedan nacin da token koji ste dobili prilikom logina posaljete kroz ajax zahtev kroz header
		 */
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:renderHoteli
	})
}

function renderHoteli(data){
	//console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$.each(list, function(index, hotel){
		var tr=$('<tr id="hotel_'+hotel.id+'"></tr>');
		tr.append('<td>' + hotel.id + '</td>'+'<td>' + hotel.naziv + '</td>' + '<td>'
				+ hotel.adresa + '</td>' + '<td>'
				+ hotel.opis + '</td>'  );
		var formaUpdate=$('<form id="formaUpdate"></form>');
		formaUpdate.append('<input type="hidden" value="'+hotel.id+'">');
		formaUpdate.append('<input type="submit" value="Ažuriraj">');
		
		var formaObrisi=$('<form id="formaObrisi"></form>');
		formaObrisi.append('<input type="hidden" value="'+hotel.id+'">');
		formaObrisi.append('<input type="submit" value="Obriši">');
		
		var td1=$('<td></td>');
		td1.append(formaUpdate);
		
		var td=$('<td></td>');
		td.append(formaObrisi);
		tr.append(td1);
		tr.append(td);
		
		$('#prikazHotelaTabela').append(tr);
	})
}

$(document).on('submit', '#formaObrisi', function(e){
	e.preventDefault();
	var id_hotela=$(this).find('input[type=hidden]').val();
	console.log(id_hotela);
	$.ajax({
		type:'DELETE',
		url:'/api/hotels/'+id_hotela,
		/*
		 * Milan: drugi nacin da token posaljete u zahtevu kroz ajax poziv kroz header
		 */
		headers: {
			Authorization: "Bearer " + localStorage.getItem("accessToken")
			},
		success:function(){
			console.log('blaa');
			$('#hotel_'+id_hotela).remove();
		},error:function(XMLHttpRequest,textStatus, errorThrown){
			console.log("okdosdkaasdd");
			alert(errorThrown);
		}
	})
	
})

$(document).on('submit', '#formaUpdate', function(e){
	e.preventDefault();
	var id_hotela=$(this).find('input[type=hidden]').val();
	console.log(id_hotela);
	$.ajax({
		
	})
})