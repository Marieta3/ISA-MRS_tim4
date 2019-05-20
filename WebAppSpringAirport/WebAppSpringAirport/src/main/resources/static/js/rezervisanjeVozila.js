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
		trow.append('<td><button  onclick="selektovanRent(this)"><input type="hidden" id="'+rentACar.id+'">Select</button></td>');
		$('#prikazRentACarTabela').append(trow);
	})
	$('#prikazRentACarTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });

}

function selektovanRent(btn){
	var rent_id=$(btn).find('input[type=hidden]').attr('id');
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
	
	$("#prikazVoziloTabela").find("tr:gt(0)").remove();
	$("#prikazVoziloTabela").find("th:gt(7)").remove();
	$.each(list, function(index, car){
		console.log("render vozila.....")
		console.log(car)
		var trow=get_row(car, "car", localStorage.getItem('uloga'), 'nema', 'nema');
		trow.append('<td><input type="checkbox" id="car_id'+car.id+'" name="'+car.proizvodjac+', '+car.tablica+', '+car.cena+'" value="'+car.id+'" onclick="selektovanoVozilo(this)"></td>');
		$('#prikazVoziloTabela').append(trow);
	})
	$('#prikazVoziloTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
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
}