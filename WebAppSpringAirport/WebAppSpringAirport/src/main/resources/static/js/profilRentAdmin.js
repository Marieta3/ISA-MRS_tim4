/**
 * 
 */

$(document).on('click', '#btnBranchList', findAllBranchesByRent)

function findAllBranchesByRent(){
	$.ajax({
		type:'GET',
		url:'/api/filijala/rent/' + localStorage.getItem("rent_id"),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderFilijale
	});
}

function renderFilijale(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$("#prikazBranchTabela").find("tr:gt(0)").remove();
	$.each(list, function(index, filijala){
		var tr=$('<tr id="filijala_'+filijala.id+'"></tr>');
		var slika=filijala.slika;
		if(slika==null){
			slika = "../slike/rent_car.jpg";
		}
		tr.append('<td align="center" width=100px height=100px>'+ '<div id="divFilijala" class="divEntitet">' +
				'<img src=" ' + slika +' " id="imgProfilnaBranch" class="imgEntitet"> ' + '</div>' +
				'</td>' + '<td>' + filijala.adresa + '</td>'
				+ '<td>' + filijala.telefon + '</td>' + '<td>' + filijala.opis + '</td>');
		$('#prikazBranchTabela').append(tr);
	})
}

$(document).on('submit', ".modal-content3", function(e){
	e.preventDefault();
	console.log("dodavanje filijale");
	var adresa=$("#adresaFilijale").val();
	var telefon=$("#telefonFilijale").val();
	var opis = $("#opisFilijale").val();
	var slika=$('#slika_branch').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	
	var id_rent=localStorage.getItem("rent_id");
	

	if(slika==""){
		slika = "../slike/rent_car.jpg";
	}
	console.log(adresa); 
	console.log(telefon); 
	console.log(opis); 
	console.log(slika); 
	console.log(id_rent); 
	
	$.ajax({
		type:'POST',
		url:"api/filijala",
		contentType:'application/json',
		dataType:'json',
		data:filijalaToJSONadd(opis, slika, telefon, id_rent, adresa),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			$("#id03").css("display", "none");
			$("body").removeClass("modal-open");
			//vizuelno prikazati dodatu sobu
			findAllBranchesByRent();
		}
	});
})


function filijalaToJSONadd(opis, slika, telefon, id_rent, adresa){
	return JSON.stringify({
		"opis":opis,
		"slika":slika,
		"telefon":telefon,
		"idRent":id_rent,
		"adresa":adresa
	});
}


