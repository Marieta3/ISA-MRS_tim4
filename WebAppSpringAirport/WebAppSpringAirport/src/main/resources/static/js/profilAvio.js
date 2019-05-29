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
});
 
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
		var tr=$('<tr id="flight_' + let.id + '"></tr>');
		tr.append('<td align="center" width=100px height=100px><div class="divEntitet"><img class="imgEntitet" src="'+slika+'"></div></td>');
		tr.append('<td align="center">' + let.pocetnaDestinacija + '</td>'+ '<td align="center">' + 
				let.krajnjaDestinacija + '</td>'+'<td align="center">' + let.vremePolaska + '</td>' + '<td>'
				+ let.vremeDolaska + '</td>' + '</td>'+'<td align="center">' + let.model + '</td>');
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
$("#resetBtn").on('click',function () {

	alert('resetting');
	findAllFlightsByAvio();
})

$(document).on('submit','#pretragaLetova',function () {
	alert("submit");
	var start = $("#departure").val();
	var end = $("#destination").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();

	if(startDate > endDate){
		$.bootstrapGrowl("Start must be after end date!", {
			  ele: 'body', // which element to append to
			  type: "danger", // (null, 'info', 'danger', 'success')
			  offset: {from: 'top', amount: 50}, // 'top', or 'bottom'
			  align: 'center', // ('left', 'right', or 'center')
			  width: 'auto', // (integer, or 'auto')
			  delay: 1500, // Time while the message will be displayed. It's not equivalent to the demo timeOut!
			  allow_dismiss: false, // If true then will display a cross to close the popup.
			  stackup_spacing: 10 // spacing between consecutively stacked growls.
			});
		return;
	}
	alert(start + end + startDate + endDate);
	
	$.ajax({
		type:'GET',
		url:'api/avioKompanije/searchFlights/' + localStorage.getItem("profil_avio"),
		dataType:'json',
		data:letToJSON(start, end, startDate, endDate),
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderFlights
	});
})

function letToJSON(start, end, startDate, endDate){
	return JSON.stringify({
		"mestoPolaska":start,
		"mestoDolaska":end,
		"vreme1":startDate,
		"vreme2":endDate
	});
}