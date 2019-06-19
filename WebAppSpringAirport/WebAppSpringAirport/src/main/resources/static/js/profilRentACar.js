let searchOn = false;

$(document).ready(function () {
    var today = new Date();
    var day=today.getDate()>9?today.getDate():"0"+today.getDate(); // format should be "DD" not "D" e.g 09
    var month=(today.getMonth()+1)>9?(today.getMonth()+1):"0"+(today.getMonth()+1);
    var year=today.getFullYear();

    $("#startDate").attr('min', year + "-" + month + "-" + day);
    $("#startDate").attr('max', "2025-01-01");
    
    
    var rent_id = localStorage.getItem("profil_rent")
    if(rent_id == null){
        window.location.replace("prikazRentACar.html")
    }
    
    findProfile();
    findAllBranchesByRent();
    findAllCarsByRent();
	ymaps.ready(init);
});
function init(){
	//pokupiti koordinate iz hidden polja
	var coords= $('#rent_coords').val();
	console.log("aaa"+coords);
	var coord_list=coords.split(',');
	var map = new ymaps.Map('map', {
        center: [coord_list[0], coord_list[1]],
        zoom: 12,
        controls: ['zoomControl'],
        behaviors: ['drag']
    });
	var placemark=new ymaps.Placemark([coord_list[0], coord_list[1]], {
		
	});
	map.geoObjects.add(placemark);
}

$("#pretragaVozilo button").click(function(ev){
    ev.preventDefault()// cancel form submission
    if($(this).attr("name")=="find"){
    	if (($("#startDate").val() == "") || ($("#broj_dana").val() == ""))
		{
    		notify("Do not leave any fields blank!", "danger", "top")
		}
    	else{
	    	pretraga();
		}
    }
    else if($(this).attr("name")=="reset"){
    	
    	$("#startDate").val("");
    	$("#broj_dana").val("");
    	
    	if(searchOn){
    		findAllCarsByRent();
	        searchOn = false;
    	}
    }
});

function findProfile(){
	$.ajax({
		type:'GET',
		url:'/api/rentACars/' + localStorage.getItem("profil_rent"),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderProfil
	});
}

function findAllBranchesByRent(){
	$.ajax({
		type:'GET',
		url:'/api/filijala/rent/' + localStorage.getItem("profil_rent"),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderFilijale
	});
}

function findAllCarsByRent(){
	$.ajax({
		type:'GET',
		url:'/api/cars/rent/' + localStorage.getItem("profil_rent"),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderVozila
	});
}

function renderProfil(data){
	console.log(data);
	var rating=data.ocena;
	
	$('.cornerimage').css("width", (rating/5)*100+"%");
	$('#rating_rent').text(rating);
	$("#naziv").val(data.naziv);
	$("#adresa").val(data.adresa);
	$("#opis").val(data.opis);

	$('#rent_coords').val(data.coord1+','+data.coord2);
}

function renderFilijale(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$("#prikazBranchTabela").find("tr:gt(0)").remove();
	$("#prikazBranchTabela").find("th:gt(3)").remove();
	
	$.each(list, function(index, filijala){
		$('#prikazBranchTabela').append(get_row(filijala, "branch", localStorage.getItem('uloga'), null, null));
	})
	if ( ! $.fn.DataTable.isDataTable( '#prikazBranchTabela' ) ) {
		$('#prikazBranchTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "order":[[1,'asc']],
	      "columnDefs": [
	                     { "orderable": false, "targets": 0 }
	                   ]
		});
	}
}

function renderVozila(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	console.log(data);
	uloga=localStorage.getItem("uloga");
	
	$("#prikazVoziloTabela").find("tr:gt(0)").remove();
	$("#prikazVoziloTabela").find("th:gt(8)").remove();
	

	$('#prikazVoziloTabela').DataTable().clear().destroy();
	$.each(list, function(index, car){
		$('#prikazVoziloTabela tbody').append(get_row(car, "car", localStorage.getItem('uloga'), null, null));
	})


	$('#prikazVoziloTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "order":[[1,'asc']],
	      "columnDefs": [
	                     { "orderable": false, "targets": 0 }
	                   ]
	  });
	
}


function pretraga() {
	var startDate = $("#startDate").val();
	//var broj_dana = $("#broj_dana").val();
	var endDate=$("#endDate").val();
	searchOn = true;
	//alert(sobaToJSONsearch(startDate, broj_nocenja));
	$.ajax({
		type:'POST',
		url:'/api/voziloRent/pretraga/' + localStorage.getItem("profil_rent"),
		dataType:'json',
		contentType: 'application/json',
		data:voziloToJSONsearch(startDate, endDate),
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderVozila
	});
}

function voziloToJSONsearch(dolazak, endDate) {
	return JSON.stringify({
		"vreme1" : dolazak,
		"vreme2" : endDate
	});
}
