/**
 * 
 */
$(document).on('click', '#btnBranchList', findAllBranchesByRent)
$(document).on('click', '#btnCarList', findBranchAddresses)
$(document).on('click', '#btnCarList', findAllCarsByRent)

$(document).ready(function () {
        var today = new Date();
        var day=today.getDate()>9?today.getDate():"0"+today.getDate(); // format should be "DD" not "D" e.g 09
        var month=(today.getMonth()+1)>9?(today.getMonth()+1):"0"+(today.getMonth()+1);
        var year=today.getFullYear();

        $("#quick-car-start").attr('min', year + "-" + month + "-" + day);
        $("#quick-car-start").attr('max', "2025-01-01");
        $("#quick-car-end").attr('min', year + "-" + month + "-" + day);
        $("#quick-car-end").attr('max', "2025-01-01");
        
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
        controls: ['zoomControl', 'searchControl'],
        behaviors: ['drag']
    });
	var placemark=new ymaps.Placemark([coord_list[0], coord_list[1]], {
		
	});
	map.geoObjects.add(placemark);
	var searchControl=map.controls.get('searchControl');
	//var coords;
	/*searchControl.search('Moscow').then(function(){
		coords=searchControl.getResultsArray()[0].geometry._coordinates;
		var placemark=new ymaps.Placemark(coords, {
			
		});
		map.geoObjects.add(placemark);
		map.setCenter(coords);
	})*/
	searchControl.events.add('load', function(event){
		if(!event.get('skip') && searchControl.getResultsCount()){
			coords=searchControl.getResultsArray()[0].geometry._coordinates;
			console.log("pronadjeno: "+searchControl.getResultsArray()[0].properties.get('text'));
			$("#adresa").val(searchControl.getResultsArray()[0].properties.get('text'));
			$('#rent_coords').val(coords);
			map.setCenter(coords);
			var placemark=new ymaps.Placemark(coords, {
				
			});
			map.geoObjects.add(placemark);
			//searchControl.showResult(0);
		}
	})
	
}

$(document).ready(function(){
	localStorage.removeItem("profil_rent");
})

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
	if ( ! $.fn.DataTable.isDataTable( '#prikazBranchTabela' ) ) {
		$('#prikazBranchTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "order":[[1,'desc']],
	      "columnDefs": [
	                     { "orderable": false, "targets": 0 },
	                     { "orderable": false, "targets": 4 },
	                     { "orderable": false, "targets": 5 }
	                   ]
		});
	}
}

function renderVozila(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	console.log(data);
	uloga=localStorage.getItem("uloga");
	
	if(uloga=="ROLE_RENT"){
		
		$('#prikazVoziloTabela').find('tr:eq(0)').append('<th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th>');
		//$('#prikazVoziloTabela').find('tr:eq(0)').append(th_nbsp1);
	}
	$("#prikazVoziloTabela").find("tr:gt(0)").remove();
	$("#prikazVoziloTabela").find("th:gt(11)").remove();
	$.each(list, function(index, car){
		console.log("render vozila.....")
		console.log(car)
		var row=get_row(car, "car", localStorage.getItem('uloga'), 'id05', 'id06');
		row.append('<td><button name="'+car.id+'" id="quick_'+car.id+'" onclick="selektovanoVoziloBrzaRezervacija(this), otvoriModal(\'id07\')">Make Quick</button></td>');
		$('#prikazVoziloTabela tbody').append(row);
	})
	if ( ! $.fn.DataTable.isDataTable( '#prikazVoziloTabela' ) ) {
		$('#prikazVoziloTabela').DataTable({
		      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
		      "iDisplayLength": 5,
		      "order":[[1,'desc']],
		      "columnDefs": [
		                     { "orderable": false, "targets": 0 },
		                     { "orderable": false, "targets": 9 },
		                     { "orderable": false, "targets": 10 }
		                   ]
		  });
	}
}

function selektovanoVoziloBrzaRezervacija(btn){
	var car_id=$(btn).attr('name');
	$('#quick-car-id').val(car_id);
	$.ajax({
		type:'GET',
		url:'api/cars/'+car_id,
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	$('#quick-car-opis').val(data.proizvodjac+' '+data.model+' '+data.tablica);
        	if(data.slika!="" && data.slika!=null){
        		$('#quick-car-img').attr('src', data.slika);
        	}
        	$('#quick-car-old-cena').val(data.cena);
        	
        }
	});
}

function dodavanjeBrzeRezervacije(e){
	e.preventDefault();
	var car_id=$('#quick-car-id').val();
	console.log(car_id);
	var nova_cena=$('#quick-car-new-cena').val();
	var start_datum=$('#quick-car-start').val();
	var end_datum=$('#quick-car-end').val();
	
	$.ajax({
		type:'POST',
		url:'api/quick/car',
		contentType:'application/json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        data:brzoVoziloToJSON(car_id, nova_cena, start_datum, end_datum),
        success:function(data){
        	zatvoriModal('id07');
        	notify("Successfully added quick vehicle reservation!", 'info');
        }
	});
}

function brzoVoziloToJSON(car_id, nova_cena, start_datum, end_datum){
	return JSON.stringify({
		"id":car_id,
		"novaCena":nova_cena,
		"startDatum":start_datum,
		"endDatum":end_datum
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
			$.bootstrapGrowl("Branch added!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
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
			$.bootstrapGrowl("Branch updated!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
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
			$.bootstrapGrowl("Branch deleted!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#id01").css("display", "none");
			$("body").removeClass("modal-open");
			$.bootstrapGrowl("Cannot delete branch with reserved cars!", {
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
			$.bootstrapGrowl("Car added!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		},

		error : function(XMLHttpRequest, textStatus, errorThrown) {
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
        	$("#branchCar1").val(data.filijala.adresa);
        	$("#identifikatorVoziloUpd").val(data.id);
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
	
	var id = $("#identifikatorVoziloUpd").val();
	$.ajax({
		type:"PUT",
		url:"api/cars/"+id,
		contentType:'application/json',
		dataType:'json',
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
			
			$.bootstrapGrowl("Car updated!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {

        	$("#id06").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('editVoziloForma');
        	$.bootstrapGrowl("Cannot change reserved car!", {
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
			$.bootstrapGrowl("Car deleted!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

			$("#id05").css("display", "none");
			$("body").removeClass("modal-open");
			$.bootstrapGrowl("Cannot delete reserved car!", {
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
	})
	
})


