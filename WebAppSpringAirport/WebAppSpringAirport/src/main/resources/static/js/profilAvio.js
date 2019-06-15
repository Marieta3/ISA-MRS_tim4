let searchOn = false; 

$(document).ready(function () {
        var today = new Date();
        var day=today.getDate()>9?today.getDate():"0"+today.getDate(); // format should be "DD" not "D" e.g 09
        var month=(today.getMonth()+1)>9?(today.getMonth()+1):"0"+(today.getMonth()+1);
        var year=today.getFullYear();

        $("#startDate").attr('min', year + "-" + month + "-" + day);
        $("#startDate").attr('max', "2025-01-01");
        $("#endDate").attr('min', year + "-" + month + "-" + day);
        $("#endDate").attr('max', "2025-01-01");
        
        var avio_id = localStorage.getItem("profil_avio")
        if(avio_id == null){
            window.location.replace("prikazAvio.html")
        }
        
        findProfile();
        findAllFlightsByAvio();
        findAllDestinations();
});
 $("#pretragaLetova button").click(function(ev){
	    ev.preventDefault()// cancel form submission
	    if($(this).attr("name")=="find"){
	    	pretraga();
	    }
	    if($(this).attr("name")=="reset"){

	    	$("#from-dest option:first").removeAttr("disabled");
	    	$("#to-dest option:first").removeAttr("disabled");
	    	
	    	$("#from-dest").val("0");
	    	$("#to-dest").val("0");

	    	$("#from-dest option:first").attr("disabled","disabled");
	    	$("#to-dest option:first").attr("disabled","disabled");
	    	$("#startDate").val("");
	    	$("#endDate").val("");
	    	
	    	if(searchOn){
		        findAllFlightsByAvio();
		        searchOn = false;
	    	}
	    }
	    // $("#my-form").submit(); if you want to submit the form
	});
 
 
 
 
 function findAllDestinations(){
		$.ajax({
			type:'GET',
		url:'api/destinacije',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success : renderDestinacije
	});
}

function renderDestinacije(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$('#from-dest').find('option:gt(0)').remove();
	$('#to-dest').find('option:gt(0)').remove();
	
	$.each(list, function(index, destinacija){
		$('#from-dest').append('<option value="'+(index+1)+'">'+destinacija.adresa+'</option>');
		$('#to-dest').append('<option value="'+(index+1)+'">'+destinacija.adresa+'</option>');
	})
}
	
 
function findProfile(){
		$.ajax({
			type:'GET',
		url:'/api/avioKompanije/' + localStorage.getItem("profil_avio"),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderProfil
	});
}

function renderProfil(data){
	console.log(data);
	var rating=data.ocena;
	
	$('.cornerimage').css("width", (rating/5)*100+"%");
	$('#rating_avio').text(rating);
	$("#naziv").val(data.naziv);
	$("#adresa").val(data.adresa);
	$("#opis").val(data.opis);
}

function findAllFlightsByAvio(){
	$.ajax({
		type:'GET',
		url:'api/avioKompanije/flights/' + localStorage.getItem("profil_avio"),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderFlights
	});
}

function renderFlights(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	//notify("RenderFlights","info");
	uloga=localStorage.getItem("uloga");
	
	$("#prikazLetovaTabela").find("tr:gt(0)").remove();
	$("#prikazLetovaTabela").find("th:gt(5)").remove();
	slika = "slike/aereo2.jpg"
	$.each(list, function(index, let){
		var pD = let.vremePolaska.split("T");
		var dD = let.vremeDolaska.split("T");
		var polazakS = pD[0] + " &nbsp; " + pD[1].split(".")[0];
		var dolazakS = dD[0] + " &nbsp; " + dD[1].split(".")[0];
		
		var tr=$('<tr id="flight_' + let.id + '"></tr>');
		tr.append('<td align="center" width=100px height=100px><div class="divEntitet"><img class="imgEntitet" src="'+slika+'"></div></td>');
		tr.append('<td align="center">' + let.pocetnaDestinacija + '</td>'+ '<td align="center">' + 
				let.krajnjaDestinacija + '</td>'+'<td align="center">' + polazakS + '</td>' + '<td align="center">'
				+ dolazakS + '</td>' + '</td>'+'<td align="center">' + let.model + '</td>');
		$('#prikazLetovaTabela').append(tr);
	})
	
	if ( ! $.fn.DataTable.isDataTable( '#prikazLetovaTabela' ) ) {
	$('#prikazLetovaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "order":[[1,'asc']],
	      "columnDefs": [
	                     { "orderable": false, "targets": 0 }
	                   ]
	  });
	}
}


function pretraga() {
	var start = $("#selected_text").val();
	var end = $("#selected_text1").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();

	if(startDate > endDate){
		notify("Start must be before return date!" , "danger");
		return;
	}
	alert(start + end + startDate + endDate);
	searchOn = true;
	$.ajax({
		type:'POST',
		url:'/api/avioKompanije/searchFlights/' + localStorage.getItem("profil_avio"),
		dataType:'json',
		data:letToJSON(start, end, startDate, endDate),
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderFlights
	});
	
}

function letToJSON(start, end, startDate, endDate){
	return JSON.stringify({
		"mestoPolaska":start,
		"mestoDolaska":end,
		"vreme1":startDate,
		"vreme2":endDate
	});
}