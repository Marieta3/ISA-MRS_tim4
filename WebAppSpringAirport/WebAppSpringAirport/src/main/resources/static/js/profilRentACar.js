$(document).ready(function () {
    var rent_id = localStorage.getItem("profil_rent")
    if(rent_id == null){
        window.location.replace("prikazRentACar.html")
    }
    
    findProfile();
    findAllBranchesByRent();
    findAllCarsByRent();
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
	$("#naziv").val(data.naziv);
	$("#adresa").val(data.adresa);
	$("#opis").val(data.opis);
}

function renderFilijale(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	/*
	$("#prikazBranchTabela").find("tr:gt(0)").remove();
	$("#prikazBranchTabela").find("th:gt(4)").remove();
	*/
	$.each(list, function(index, filijala){
		$('#prikazBranchTabela').append(get_row(filijala, "branch", localStorage.getItem('uloga'), null, null));
	})
	if ( ! $.fn.DataTable.isDataTable( '#prikazBranchTabela' ) ) {
		$('#prikazBranchTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "order":[[1,'desc']],
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
	/*
	$("#prikazVoziloTabela").find("tr:gt(0)").remove();
	$("#prikazVoziloTabela").find("th:gt(6)").remove();
	*/
	$.each(list, function(index, car){
		$('#prikazVoziloTabela tbody').append(get_row(car, "car", localStorage.getItem('uloga'), null, null));
	})
	if ( ! $.fn.DataTable.isDataTable( '#prikazVoziloTabela' ) ) {
		$('#prikazVoziloTabela').DataTable({
		      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
		      "iDisplayLength": 5,
		      "order":[[1,'desc']],
		      "columnDefs": [
		                     { "orderable": false, "targets": 0 }
		                   ]
		  });
	}
}