/**
 * 
 */
findAllRentACars();
function findAllRentACars(){
	$.ajax({
		type : 'GET',
		url : 'api/rentACars',
		dataType : 'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success : renderRentACars
	})
}

function renderRentACars(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");
	if(list.length == 0){
		console.log("Not found data");
		notify("No rent-a-car agencies found!", 'info');
		//return;
	}
	$('#prikazRentACarTabela').DataTable().clear().destroy();
	if(list.length == 0){
		console.log("Not found data");
		//alert("No rent-a-car found!")
		$.bootstrapGrowl("No rent-a-car found!", {
			  ele: 'body', // which element to append to
			  type: 'info', // (null, 'info', 'danger', 'success')
			  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
			  align: 'right', // ('left', 'right', or 'center')
			  width: 'auto', // (integer, or 'auto')
			  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
			  allow_dismiss: false, // If true then will display a cross to close the popup.
			  stackup_spacing: 10 // spacing between consecutively stacked growls.
			});
		return;
	}

	$("#prikazRentACarTabela").find("tr:gt(0)").remove();
	$("#prikazRentACarTabela").find("th:gt(5)").remove();
	$.each(list, function(index, rentACar) {
		var trow=get_row(rentACar, "rent", localStorage.getItem('uloga'), 'nema', 'nema');
		trow.append('<td><a href="#prikaz-vozila"><button  onclick="selektovanRent(this)"><input type="hidden" id="'+rentACar.id+'">Select</button></a></td>');
		$('#prikazRentACarTabela').append(trow);
	})
	$('#prikazRentACarTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      order: [[1, 'asc']],
	      "columnDefs": [
	                     { "orderable": false, "targets": 0 },
	                     { "orderable": false, "targets": 5 }
	                   ]
	  });

}

function selektovanRent(btn){
	var rent_id=$(btn).find('input[type=hidden]').attr('id');
	$('#selected-cars').empty();
	$('#counter-cars').text('0');
	$('#total-cars').text('0');
	$('#selektovan-rent-id').val(rent_id);
	$.ajax({
		type:'GET',
		url:'api/cars/rent/'+rent_id,
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderCars,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
}

function renderCars(data){
var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$('#prikazVoziloTabela').DataTable().clear().destroy();
	uloga=localStorage.getItem("uloga");
	$('#rent-naziv-adresa').text(data[0].filijala.rentACar.naziv+', '+data[0].filijala.rentACar.adresa);
	$("#prikazVoziloTabela").find("tr:gt(0)").remove();
	$("#prikazVoziloTabela").find("th:gt(9)").remove();
	$.each(list, function(index, car){
		console.log("render vozila.....")
		console.log(car)
		var trow=get_row(car, "car", localStorage.getItem('uloga'), 'nema', 'nema');
		trow.append('<td><input type="checkbox" class="cart-item-car" id="car_id'+car.id+'" name="'+car.proizvodjac+', '+car.tablica+', '+car.cena+'" value="'+car.id+'" onclick="selektovanoVozilo(this)"></td>');
		$('#prikazVoziloTabela').append(trow);
	})
	$('#prikazVoziloTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      order: [[1, 'desc']],
	      "columnDefs": [
	                     { "orderable": false, "targets": 9 },
	                     { "orderable": false, "targets": 0 },
	                   ]
	  });
	
}

$(document).on('submit', '#pretragaVozila', function(e){
	e.preventDefault();
	var dat1=$('#datepicker7').val();
	var dat2=$('#datepicker8').val();
	var rent_id=$('#selektovan-rent-id').val();
	$.ajax({
		type:'POST',
		url:'api/voziloRent/pretraga/'+rent_id,
		contentType: 'application/json',
		dataType : 'json',
		data : voziloToJSONsearch(dat1, dat2),
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success : renderCars
	})
})

function voziloToJSONsearch(dat1, dat2) {
	return JSON.stringify({
		"vreme1" : dat1,
		"vreme2" : dat2
	});
}
function selektovanoVozilo(checkbox){
	//ako je cekirano dodati u listu, ako nije onda izbaciti iz liste
	var id_vozila=checkbox.value;
	//var opis=checkbox.name;
	var tokens=checkbox.name.split(', ');
	var opis= [tokens[0], tokens[1]].join(', ');
	var cena=tokens[2];
	console.log(cena);
	if($(checkbox).prop('checked')==true){
		$('#counter-cars').text(parseInt($('#counter-cars').text(), 10)+1);
		$('#selected-cars').append('<li id="cars_lista'+id_vozila+'">Vehicle: '+opis+'</li>');
		$('#total-cars').text(parseInt($('#total-cars').text(), 10)+parseInt(cena, 10));
		
	}else{
		$('#counter-cars').text(parseInt($('#counter-cars').text(), 10)-1);
		$('#total-cars').text(parseInt($('#total-cars').text(), 10)-cena);
		$('#cars_lista'+id_vozila).remove()
	}
	
}
function pokupiRezervisanaVozila(){
	var checkedVals = $('input[type=checkbox]:checked').map(function() {
		return this.value;
	}).get();
	console.log(checkedVals);
	notify("Successful reservation!", 'info');
}