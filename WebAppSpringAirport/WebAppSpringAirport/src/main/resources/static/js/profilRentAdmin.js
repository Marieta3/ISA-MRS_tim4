/**
 * 
 */

$(document).on('click', '#btnBranchList', findAllBranchesByRent)
$(document).on('click', '#btnCarList', findBranchAddresses)
$(document).on('click', '#btnCarList', findAllCarsByRent)


function findBranchAddresses()
	{
	    $("#branchCar").html(""); 
	    $("#branchCar1").html(""); 
	    $.ajax({
			type : 'GET',
			url:'/api/filijala/rent/' + localStorage.getItem("rent_id"),
			dataType : 'json',
			beforeSend : function(request) {
				request.setRequestHeader("Authorization", "Bearer "
						+ localStorage.getItem("accessToken"));
			},
			success : function (data){
				var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
				$.each(list, function(index, branch) {
					$("#branchCar").append("<option value=\"branch_"+branch.id+"\">"+branch.adresa+"</option>");
					$("#branchCar1").append("<option value=\"branch_"+branch.id+"\">"+branch.adresa+"</option>");
					console.log("branch_" + branch.id);
				})
			}
		})
	    
	}

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


function findAllCarsByRent(){
	$.ajax({
		type:'GET',
		url:'/api/cars/rent/' + localStorage.getItem("rent_id"),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderVozila
	});
}

function renderFilijale(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	console.log(data);
	uloga=localStorage.getItem("uloga");
	
	if(uloga=="ROLE_RENT"){
		var th_nbsp=$('<th>&nbsp;</th>');
		var th_nbsp1=$('<th>&nbsp;</th>');
		$('#prikazBranchTabela').find('tr:eq(0)').append(th_nbsp);
		$('#prikazBranchTabela').find('tr:eq(0)').append(th_nbsp1);
	}
	$("#prikazBranchTabela").find("tr:gt(0)").remove();
	$("#prikazBranchTabela").find("th:gt(5)").remove();
	$.each(list, function(index, filijala){
		/*
		var tr=$('<tr id="filijala_'+filijala.id+'"></tr>');
		var slika=filijala.slika;
		if(slika==null){
			slika = "../slike/rent_car.jpg";
		}
		tr.append('<td align="center" width=100px height=100px>'+ '<div id="divFilijala" class="divEntitet">' +
				'<img src=" ' + slika +' " id="imgProfilnaBranch" class="imgEntitet"> ' + '</div>' +
				'</td>' + '<td>' + filijala.adresa + '</td>'
				+ '<td>' + filijala.telefon + '</td>' + '<td>' + filijala.opis + '</td>');
		if (uloga == "ROLE_RENT") {		
			var formaUpdate = $('<form id="formaUpdateFilijala"></form>');
			formaUpdate.append('<input type="hidden" value="' + filijala.id + '">');
			formaUpdate.append('<input type="submit" value="Update" onclick="otvoriModal(\'id04\')">');
			var td1 = $('<td></td>');
			td1.append(formaUpdate);
			tr.append(td1);
			
			var formaObrisi = $('<form id=\"formaObrisiFilijalu\" onsubmit=\"formaObrisi(\'identifikatorFilijala\', ' + filijala.id +' )\"> </form>');
			formaObrisi.append('<input type="hidden" value="' + filijala.id + '">');
			formaObrisi.append('<input id="hiddenFilijala" type="hidden" value="' + filijala.adresa+ '">');
			formaObrisi.append('<input type="submit" value="Delete" onclick="otvoriModal(\'id01\')">');
			var td = $('<td></td>');
			td.append(formaObrisi);
			tr.append(td);
		}

		$('#prikazBranchTabela').append(tr);*/
		$('#prikazBranchTabela').append(get_row(filijala, "branch", localStorage.getItem('uloga'), 'id01', 'id04'));
	})
	$('#prikazBranchTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });
}

function renderVozila(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	console.log(data);
	uloga=localStorage.getItem("uloga");
	
	if(uloga=="ROLE_RENT"){
		var th_nbsp=$('<th>&nbsp;</th>');
		var th_nbsp1=$('<th>&nbsp;</th>');
		$('#prikazVoziloTabela').find('tr:eq(0)').append(th_nbsp);
		$('#prikazVoziloTabela').find('tr:eq(0)').append(th_nbsp1);
	}
	$("#prikazVoziloTabela").find("tr:gt(0)").remove();
	$("#prikazVoziloTabela").find("th:gt(8)").remove();
	$.each(list, function(index, car){
		console.log("render vozila.....")
		console.log(car)
		$('#prikazVoziloTabela tbody').append(get_row(car, "car", localStorage.getItem('uloga'), 'id05', 'id06'));
	})
	$('#prikazVoziloTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });
	
}

//dodavanje filijala
$(document).on('submit', ".modal-content3", function(e){
	e.preventDefault();
	console.log("dodavanje filijala");
	var adresa=$("#adresaFilijale").val();
	var telefon=$("#telefonFilijale").val();
	var opis = $("#opisFilijale").val();
	var slika=$('#slika_branch').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	
	var id_rent=localStorage.getItem("rent_id");
	

	if(slika==""){
		slika = "../slike/branch.jpg";
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
			//vizuelno prikazati dodatu filijalu
			$('#prikazBranchTabela').DataTable().clear().destroy();
			findAllBranchesByRent();
		}
	});
})

$(document).on('submit', "#editBranchForma", function(e){
	e.preventDefault();
	var adresa=$("#adresaFilijale1").val();
	var telefon=$("#telefonFilijale1").val();
	var opis=$("#opisFilijale1").val();
	var id = $("#identifikatorFilijalaUpd").val();
	var slika = $('#slika_branch1').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	
	if(slika=="" || slika==null){
		console.log("slika je null");
		slika=$("#branch_img1").attr("src");
		console.log("["+slika+"]");
	}
	$.ajax({
		type:"PUT",
		url:"api/filijala/"+id,
		contentType:'application/json',
		dataType:'text',
		data:filijalaToJSON(opis, slika, telefon, adresa, id),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	//window.location.replace("profilROLE_HOTEL.html");
        	$("#id04").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('editBranchForma');
			$('#branch_'+id).remove();
			$('#prikazBranchTabela').DataTable().clear().destroy();
			findAllBranchesByRent();
			//dodajNoviEntitet('prikazBranchTabela', get_row($.parseJSON(data), "branch", localStorage.getItem('uloga'), 'id01', 'id04'));
			

        }
	})
	
})
function formaUpdatebranch(e, forma){
	e.preventDefault();
	var id_branch = $(forma).find('input[type=hidden]').val();
	console.log(id_branch);
	$.ajax({
		type:"GET",
		url:"api/filijala/"+id_branch,
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	if(data.slika!=null && data.slika!=""){
        		$('#branch_img1').attr("src", data.slika);
        	}
        	console.log("["+data.slika+"]");
        	$("#adresaFilijale1").val(data.adresa);
        	$("#telefonFilijale1").val(data.telefon);
        	$("#opisFilijale1").val(data.opis);
        	$("#identifikatorFilijalaUpd").val(data.id);
        }
		
	})
}

function filijalaToJSON(opis, slika, telefon, adresa, id){
	return JSON.stringify({
		"opis":opis,
		"slika":slika,
		"telefon":telefon,
		"adresa":adresa,
		"id":id
	});
}

//brisanje filijala
$(document).on('submit', "#deleteBranchForma", function(e){
	e.preventDefault();
	var id=$("#identifikatorFilijala").val();
	$.ajax({
		type : 'DELETE',
		url : '/api/filijala/' + id,
		headers : {
			Authorization : "Bearer " + localStorage.getItem("accessToken")
		},
		success : function() {
			console.log('Deleted branch');
			$('#filijala_' + id).remove();
			$("#id01").css("display", "none");
			$("body").removeClass("modal-open");
			$('#prikazBranchTabela').DataTable().clear().destroy();
			findAllBranchesByRent();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("Branch deletion failed");
			alert(errorThrown);
		}
	})
	
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

// ADD CARS

$(document).on('submit', ".modal-content2", function(e){
	e.preventDefault();
	console.log("dodavanje vozilo");
	var manufacturer=$("#carManufacturer").val();
	var model=$("#modelCar").val();
	var year=$("#yearCar").val();
	var license=$("#licenseCar").val();
	var price=$("#priceCar").val();
	var seats=$("#seatsCar").val();
	var adresa=$("#branchCar option:selected").val();
	
	//alert(adresa);
	//format branch_1
	var selBranch = $("#branchCar option:selected").attr("value");
	var filijala_id = selBranch.split("_")[1];
	console.log(filijala_id);
	var id_rent=localStorage.getItem("rent_id");
	$.ajax({
		type:'POST',
		url:"api/cars",
		contentType:'application/json',
		dataType:'json',
		data:carsToJSONadd(manufacturer, model, year, license, price, seats, filijala_id),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			$("#id02").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('newCarForma');
			$('#prikazVoziloTabela').DataTable().clear().destroy();
			findAllCarsByRent();
			//dodajNoviEntitet('prikazVoziloTabela', get_row(data, "car", localStorage.getItem('uloga'), 'id05', 'id06'));
			
			
		}
	});
})

function carsToJSONadd(manufacturer, model, year, license, price, seats, filijala_id){
	return JSON.stringify({
		"proizvodjac":manufacturer,
		"model":model,
		"godina":year,
		"tablica":license,
		"cena":price,
		"brojMesta":seats,
		"filijala_id":filijala_id
	});
}


function formaUpdatecar(e, forma){
	e.preventDefault();
	var id_car = $(forma).find('input[type=hidden]').val();
	
	$.ajax({
		type:"GET",
		url:"api/cars/"+id_car,
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	$("#carManufacturer1").val(data.proizvodjac);
        	$("#modelCar1").val(data.model);
        	$("#yearCar1").val(data.godina);
        	$("#licenseCar1").val(data.tablica);
        	$("#priceCar1").val(data.cena);
        	$("#seatsCar1").val(data.brojMesta);
        	//fali jos select
        }
		
	})
}

$(document).on('submit', "#editVoziloForma", function(e){
	e.preventDefault();
	console.log("dodavanje vozilo");
	var manufacturer=$("#carManufacturer1").val();
	var model=$("#modelCar1").val();
	var year=$("#yearCar1").val();
	var license=$("#licenseCar1").val();
	var price=$("#priceCar1").val();
	var seats=$("#seatsCar1").val();
	var adresa=$("#branchCar1 option:selected").val();
	
	//alert(adresa);
	//format branch_1
	var selBranch = $("#branchCar1 option:selected").attr("value");
	var filijala_id = selBranch.split("_")[1];
	console.log(filijala_id);
	var id_rent=localStorage.getItem("rent_id");
	
	var id = $("identifikatorVoziloUpd").val();
	alert(id);
	$.ajax({
		type:"PUT",
		url:"api/cars/"+id,
		contentType:'application/json',
		dataType:'text',
		data:carsToJSONadd(manufacturer, model, year, license, price, seats, filijala_id),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	$("#id06").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('editVoziloForma');
			$('#car_'+id).remove();
			$('#prikazVoziloTabela').DataTable().clear().destroy();
			findAllCarsByRent();
			//dodajNoviEntitet('prikazVoziloTabela', get_row($.parseJSON(data), "car", localStorage.getItem('uloga'), 'id05', 'id06'));

        }
	})
	
})


$(document).on('submit', "#deleteVoziloForma", function(e){
	e.preventDefault();
	var id=$("#identifikatorVozilo").val();
	$.ajax({
		type : 'DELETE',
		url : '/api/cars/' + id,
		headers : {
			Authorization : "Bearer " + localStorage.getItem("accessToken")
		},
		success : function() {
			console.log('brisano vozilo');
			$('#car_' + id).remove();
			$("#id05").css("display", "none");
			$("body").removeClass("modal-open");
			$('#prikazVoziloTabela').DataTable().clear().destroy();
			findAllCarsByRent();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("error deleting car");
			alert(errorThrown);
		}
	})
	
})


