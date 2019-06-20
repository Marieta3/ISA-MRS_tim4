findAllFriends();
findAllRequests();
findAllPotentialFriends();
findAllReservationHistory();	//posto prvo istoriju ucitam tamo imamo proveru da li je aktivan i setujemo na false
								//ako nije, tj ne treba provera za aktivne
findAllReservations();
findAllInvitations();
function findAllInvitations(){
	console.log('fff');
	$.ajax({
		type:'GET',
		url:'api/myInvitations',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:renderInvitations
	});
}
function findAllFriends(){
	$.ajax({
		type:'GET',
		url:'/api/friends/',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:renderKorisnici
	})
}

function findAllRequests(){
	$.ajax({
		type:'GET',
		url:'/api/friendrequests',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:renderRequests
	})
}

function findAllPotentialFriends(){
	$.ajax({
		type:'GET',
		url:'/api/potentialfriends',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:renderPotentionalFriends
	})
}

function findAllReservations(){
	$.ajax({
		type:'GET',
		url:'api/myReservations',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:renderReservations
	});
	
}

function findAllReservationHistory(){
	$.ajax({
		type:'GET',
		url:'api/myReservationHistory',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:renderReservationHistory
	});
	
}

function renderInvitations(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, poziv){
		var tr=$('<tr id="poziv_'+poziv.id+'"></tr>');
		var rezervacija=poziv.rezervacija;
		if(poziv.rezervacija.odabranaSedista.length>0){
			tr.append('<td>'+poziv.rezervacija.odabranaSedista[0].let.pocetnaDestinacija+'-'+poziv.rezervacija.odabranaSedista[0].let.krajnjaDestinacija+'</td>');
		}else{
			notify("Error! No seats reserved!");
		}
		if(rezervacija.odabraneSobe.length>0){
			tr.append('<td>'+rezervacija.odabraneSobe[0].hotel.naziv+'</td>');
		}else{
			tr.append('<td>-</td>');
		}
		if(rezervacija.odabranaVozila.length>0){
			tr.append('<td>'+rezervacija.odabranaVozila[0].filijala.rentACar.naziv+'</td>');
		}else{
			tr.append('<td>-</td>');
		}
		var tokens=rezervacija.datumRezervacije.split("T");
		var tokens1=tokens[1].split(".");
		tr.append('<td>'+tokens[0]+' '+tokens1[0]+'</td>');
		
		tr.append('<td>'+poziv.koSalje.ime+' '+poziv.koSalje.prezime+'</td>');
		tr.append('<td>'+rezervacija.cena+'</td>');
		tr.append('<td><button id="accept_'+poziv.id+'" value="'+poziv.id+'" onclick="reagujNaPoziv(event, this)">Accept</button></td>');
		tr.append('<td><button id="decline_'+poziv.id+'" value="'+poziv.id+'" onclick="reagujNaPoziv(event, this)">Decline</button></td>');
		$("#invitationsTable").find("tbody").append(tr);
	})
	$('#invitationsTable').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "order":[[0,'asc']],
	      "columnDefs": [
	                     { "orderable": false, "targets": 0 },
	                     { "orderable": false, "targets": 6 }
	                   ]
	  });
}

function reagujNaPoziv(e, btn){
	e.preventDefault();
	var id_poziva=btn.value;
	console.log($(btn).text());
	console.log('api/pozivi/'+$(btn).text()+'/'+id_poziva);
	
	$.ajax({
		type:'PUT',
		url:'api/pozivi/'+$(btn).text()+'/'+id_poziva,
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log('jdsjadisad');
			console.log(data);
			$('#invitationsTable').DataTable().clear().destroy();
			findAllInvitations();
		}
	});
}
function renderReservations(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$("#activeReservationsTable").find("tr:gt(0)").remove();
	$("#activeReservationsTable").find("th:gt(6)").remove();
	$.each(list, function(index, rezervacija){
		var tr=$('<tr id="active_rez_'+rezervacija.id+'"></tr>');
		if(rezervacija.odabranaSedista.length>0){
			tr.append('<td>'+rezervacija.odabranaSedista[0].let.pocetnaDestinacija+'-'+rezervacija.odabranaSedista[0].let.krajnjaDestinacija+'</td>');
		}else{
			notify("Error! No seats reserved!");
		}
		if(rezervacija.odabraneSobe.length>0){
			tr.append('<td>'+rezervacija.odabraneSobe[0].hotel.naziv+'</td>');
		}else{
			tr.append('<td>-</td>');
		}
		if(rezervacija.odabranaVozila.length>0){
			tr.append('<td>'+rezervacija.odabranaVozila[0].filijala.rentACar.naziv+'</td>');
		}else{
			tr.append('<td>-</td>');
		}
		var tokens=rezervacija.datumRezervacije.split("T");
		var tokens1=tokens[1].split(".");
		tr.append('<td>'+tokens[0]+' '+tokens1[0]+'</td>');
		var td_putnici=$('<td></td>');
		var putnici_list=$('<select></select>');
		$.each(rezervacija.korisnici, function(index, putnik){
			if(index==0){
				putnici_list.append('<option disabled="disabled" selected="selected">'+putnik.ime+' '+putnik.prezime+'</option>');
			}else{
				putnici_list.append('<option disabled="disabled">'+putnik.ime+' '+putnik.prezime+'</option>');
			}
		})
		td_putnici.append(putnici_list);
		tr.append(td_putnici);
		tr.append('<td>'+rezervacija.cena+'</td>');
		var funDummy = " onclick= \"otvoriModal('id03'), resetCancel()\""
		tr.append("<td align = 'center' ><form id='formaCancelReservation' onsubmit= 'formaCancelReservation(event,this)'><input type='hidden' value='" + rezervacija.id 
				+ "'><input type='submit' value='Cancel reservation' " + funDummy + " style=\'margin-left:0px ; margin-top : 0px\'></form> </td>");
			
		$("#activeReservationsTable").find("tbody").append(tr);
	})
	
	if ( ! $.fn.DataTable.isDataTable( '#activeReservationsTable' ) ) {
		$('#activeReservationsTable').DataTable({
		      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
		      "iDisplayLength": 5,
		      "order":[[0,'asc']],
		      "columnDefs": [
		                     { "orderable": false, "targets": 4 },
		                     { "orderable": false, "targets": 6 }
		                   ]
		  });
	}
}

function renderReservationHistory(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$("#reservationHistoryTable").find("tr:gt(0)").remove();
	$("#reservationHistoryTable").find("th:gt(6)").remove();
	$.each(list, function(index, rezervacija){
		var tr=$('<tr id="history_rez_'+rezervacija.id+'"></tr>');
		if(rezervacija.odabranaSedista.length>0){
			tr.append('<td>'+rezervacija.odabranaSedista[0].let.pocetnaDestinacija+'-'+rezervacija.odabranaSedista[0].let.krajnjaDestinacija+'</td>');
		}else{
			notify("Error! No seats reserved!");
		}
		if(rezervacija.odabraneSobe.length>0){
			tr.append('<td>'+rezervacija.odabraneSobe[0].hotel.naziv+'</td>');
		}else{
			tr.append('<td>-</td>');
		}
		if(rezervacija.odabranaVozila.length>0){
			tr.append('<td>'+rezervacija.odabranaVozila[0].filijala.rentACar.naziv+'</td>');
		}else{
			tr.append('<td>-</td>');
		}
		var tokens=rezervacija.datumRezervacije.split("T");
		var tokens1=tokens[1].split(".");
		tr.append('<td>'+tokens[0]+' '+tokens1[0]+'</td>');
		var td_putnici=$('<td></td>');
		var putnici_list=$('<select></select>');
		$.each(rezervacija.korisnici, function(index, putnik){
			if(index==0){
				putnici_list.append('<option disabled="disabled" selected="selected">'+putnik.ime+' '+putnik.prezime+'</option>');
			}else{
				putnici_list.append('<option disabled="disabled">'+putnik.ime+' '+putnik.prezime+'</option>');
			}
		})
		td_putnici.append(putnici_list);
		tr.append(td_putnici);
		tr.append('<td>'+rezervacija.cena+'</td>');
		var funDummy = " onclick= \"otvoriModal('id04')\""
		tr.append("<td align = 'center' ><form id='formaRateServices' onsubmit= 'formaRateServices(event,this)'><input type='hidden' value='" + rezervacija.id 
				+ "'><input type='submit' value='Rate services' " + funDummy + " style=\'margin-left:0px ; margin-top : 0px\'></form> </td>");
		
		$("#reservationHistoryTable").find("tbody").append(tr);
	})
	$('#reservationHistoryTable').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "order":[[0,'asc']],
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 },
	                     { "orderable": false, "targets": 6 }
	                   ]
	  });
}

function formaRateServices(e, forma){
	e.preventDefault();
	resetLabels();
	$('.page-link').css('z-index','auto');
	var id_rezervacija = $(forma).find('input[type=hidden]').val();
	console.log(id_rezervacija);
	$.ajax({
		type:"GET",
		url:"api/reserve/"+id_rezervacija,
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	console.log(data);
        	var idLet = data.odabranaSedista[0].let.id;
        	$("#lab1").append(data.odabranaSedista[0].let.avioKompanija.naziv);
        	$("#lab2").append(data.odabranaSedista[0].let.pocetnaDestinacija+'-'+data.odabranaSedista[0].let.krajnjaDestinacija);
        	$("#hidden1").val(data.odabranaSedista[0].let.avioKompanija.id);
        	$("#hidden2").val(data.odabranaSedista[0].let.id);

    		if(data.odabraneSobe.length>0){
    			$("#lab3").append(data.odabraneSobe[0].hotel.naziv);
            	$("#lab4").append(data.odabraneSobe[0].opis);
            	$("#hidden3").val(data.odabraneSobe[0].hotel.id);
            	$("#hidden4").val(data.odabraneSobe[0].id);
    		}else{
    			$("#lab3").append('-');
            	$("#lab4").append('-');
                $("#hotelRating").attr('disabled','disabled');   
                $("#roomRating").attr('disabled','disabled');   
    		}
    		if(data.odabranaVozila.length>0){
            	$("#lab5").append(data.odabranaVozila[0].filijala.rentACar.naziv);
            	$("#lab6").append(data.odabranaVozila[0].proizvodjac + " " + data.odabranaVozila[0].model);
            	$("#hidden5").val(data.odabranaVozila[0].filijala.rentACar.id);
            	$("#hidden6").val(data.odabranaVozila[0].id);
    		}else{
    			$("#lab5").append('-');
            	$("#lab6").append('-');
                $("#rentRating").attr('disabled','disabled');   
                $("#carRating").attr('disabled','disabled');  
    		}
    		
        	$("#identifikatorRezervacijaUpd").val(id_rezervacija);
        }
		
	})
}

//			hogy kezeld a checkbox változását ha flightot checkeli => id2 id3 checked, disabled 
$("#cancelReservationForma input:checkbox").change(function() {
	if ($('#check1Cancel').is(':checked')) {
        $("#check2Cancel").prop('checked', true);
        $("#check3Cancel").prop('checked', true);       
        $("#check2Cancel").attr('disabled','disabled');
        $("#check3Cancel").attr('disabled','disabled');             
    }else{  
    	if($("#txt2Check").text() != '-'){
    		$("#check2Cancel").removeAttr('disabled');
    	}else{
            $("#check2Cancel").prop('checked', false);  
    	}
    	if($("#txt3Check").text() != '-'){
    		$("#check3Cancel").removeAttr('disabled');  
    	}else{
            $("#check3Cancel").prop('checked', false);
    	}
    }
  });

function formaCancelReservation(e, forma){
	e.preventDefault();
	var id_rezervacija = $(forma).find('input[type=hidden]').val();
	$("#identifikatorRezervacijaCancel").val(id_rezervacija);
	$.ajax({
		type:"GET",
		url:"api/reserve/"+id_rezervacija,
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	console.log(data);
        	var idLet = data.odabranaSedista[0].let.id;
        	$("#txt1Check").text(data.odabranaSedista[0].let.pocetnaDestinacija+'-'+data.odabranaSedista[0].let.krajnjaDestinacija);
        	$("#flightCancel").val(data.odabranaSedista[0].let.id);

    		if(data.odabraneSobe.length>0){
    			$("#txt2Check").text(data.odabraneSobe[0].hotel.naziv);
            	$("#hotelCancel").val(data.odabraneSobe[0].id);
    		}else{
    			$("#txt2Check").text('-');
    	        $("#check2Cancel").attr('disabled','disabled');
    		}
    		if(data.odabranaVozila.length>0){
            	$("#txt3Check").text(data.odabranaVozila[0].proizvodjac + " " + data.odabranaVozila[0].model);
            	$("#carCancel").val(data.odabranaVozila[0].id);
    		}else{
    			$("#txt3Check").text('-');
    	        $("#check3Cancel").attr('disabled','disabled');
    		}
    		
        	$("#identifikatorRezervacijaUpd").val(id_rezervacija);
        }
		
	})
	
}

function resetCancel()
{
    $("#check1Cancel").prop('checked', false);
    $("#check2Cancel").prop('checked', false);
    $("#check3Cancel").prop('checked', false); 
    $("#check1Cancel").removeAttr('disabled'); 
    $("#check2Cancel").removeAttr('disabled');
    $("#check3Cancel").removeAttr('disabled');   
}

$(document).on('submit', "#cancelReservationForma", function(e){
	e.preventDefault();
	var rezId = $("#identifikatorRezervacijaCancel").val();
	var id1 = $('#check1Cancel').is(':checked');
	var id2 = $('#check2Cancel').is(':checked');
	var id3 = $('#check3Cancel').is(':checked');
	if((id1 + id2 + id3) == 0)	//nista nije checkirano
	{
		$("#id03").css("display", "none");
		$("body").removeClass("modal-open");
		$.bootstrapGrowl("No service selected to cancel!", {
			  ele: 'body', // which element to append to
			  type: 'info', // (null, 'info', 'danger', 'success')
			  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
			  align: 'right', // ('left', 'right', or 'center')
			  width: 'auto', // (integer, or 'auto')
			  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
			  allow_dismiss: false, // If true then will display a cross to close the popup.
			  stackup_spacing: 10 // spacing between consecutively stacked growls.
			});
		resetCancel();
		return;
	}
	
	$.ajax({
		type:'POST',
		url:'/api/reserve/cancel/' + rezId ,
		dataType:'json',
		contentType: 'application/json',
		data : JSON.stringify({"flightID" : id1, "hotelID" : id2, "carID" : id3}),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success: function(data)
		{
			$("#id03").css("display", "none");
			$("body").removeClass("modal-open");
			resetCancel();
			$('#activeReservationsTable').DataTable().clear().destroy();
			findAllReservations();
			$.bootstrapGrowl("Succesful cancelation!", {
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
		error:function(){

			$("#id03").css("display", "none");
			$("body").removeClass("modal-open");
			resetLabels();
			$.bootstrapGrowl("Cannot cancel reservation!", {
				  ele: 'body', // which element to append to
				  type: 'danger', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		}
	});
})

function resetLabels()
{
	$("#lab1").text("Rate airline: ");
	$("#lab2").text("Rate flight: ");
	$("#lab3").text("Rate hotel: ");
	$("#lab4").text("Rate room: ");
	$("#lab5").text("Rate rent-a-car: ");
	$("#lab6").text("Rate car: ");

	$("#airlineRating").val($("#airlineRating option:first").val());
	$("#flightRating").val($("#flightRating option:first").val());
	$("#hotelRating").val($("#hotelRating option:first").val());
	$("#roomRating").val($("#roomRating option:first").val());
	$("#rentRating").val($("#rentRating option:first").val());
	$("#carRating").val($("#carRating option:first").val());
}

$(document).on('submit', "#rateServicesForma", function(e){
	e.preventDefault();
	var arr = [];
	
	var ocena1=$("#airlineRating option:selected").val();
	var ocena2=$("#flightRating option:selected").val();
	var ocena3=$("#hotelRating option:selected").val();
	var ocena4=$("#roomRating option:selected").val();
	var ocena5=$("#rentRating option:selected").val();
	var ocena6=$("#carRating option:selected").val();
	var id1 = $("#hidden1").val();
	var id2 = $("#hidden2").val();
	var id3 = $("#hidden3").val();
	var id4 = $("#hidden4").val();
	var id5 = $("#hidden5").val();
	var id6 = $("#hidden6").val();
	
	var rezId = $("#identifikatorRezervacijaUpd").val();
	var ent = ['avio', 'let', 'hotel', 'soba', 'rent', 'vozilo'];
	
	arr.push({ 'entitetId' : ent[0] + '_' + id1, 'ocena' : ocena1, 'rezervacijaId' : rezId})
	arr.push({ 'entitetId' : ent[1] + '_' + id2, 'ocena' : ocena2, 'rezervacijaId' : rezId})
	arr.push({ 'entitetId' : ent[2] + '_' + id3, 'ocena' : ocena3, 'rezervacijaId' : rezId})
	arr.push({ 'entitetId' : ent[3] + '_' + id4, 'ocena' : ocena4, 'rezervacijaId' : rezId})
	arr.push({ 'entitetId' : ent[4] + '_' + id5, 'ocena' : ocena5, 'rezervacijaId' : rezId})
	arr.push({ 'entitetId' : ent[5] + '_' + id6, 'ocena' : ocena6, 'rezervacijaId' : rezId})
	console.log(arr);

	$.ajax({
		type:'POST',
		url:'/api/ocenjivanje' ,
		dataType:'json',
		contentType: 'application/json',
		data : ocenaDTOToJSON(arr),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success: function(data)
		{

			$("#id04").css("display", "none");
			$("body").removeClass("modal-open");
			resetLabels();
			$.bootstrapGrowl("Services rated!", {
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
		error:function(){

			$("#id04").css("display", "none");
			$("body").removeClass("modal-open");
			resetLabels();
			$.bootstrapGrowl("No new rating inputted", {
				  ele: 'body', // which element to append to
				  type: 'danger', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		}
	});
})

function ocenaDTOToJSON(arr)
{
	return JSON.stringify(arr);
}

function renderKorisnici(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");

	$("#friendsTable").find("tr:gt(0)").remove();
	$("#friendsTable").find("th:gt(4)").remove();
	$.each(list, function(index, korisnik){
		var tr=$("<tr id=\'friend_" + korisnik.id + "\' ></tr>");
		console.log(korisnik.ime);

		tr.append('<td>' + korisnik.ime + '</td>'+ '<td>' + korisnik.prezime + '</td>'+'<td>' + korisnik.korisnickoIme + '</td>' + '<td>'
				+ korisnik.mail + '</td>');
		/*
		if (uloga == "ROLE_USER") {
			var formaObrisi = $('<form id="formaObrisi" onsubmit="formaObrisi(event, this, \'identifikator\', \'imePrezimeUsername\')"></form>');
			formaObrisi.append('<input type="hidden" value="' + korisnik.id + '">');
			formaObrisi.append('<input id="hiddenKorisnik" type="hidden" value="' + korisnik.ime+" "+korisnik.prezime + ", "+korisnik.korisnickoIme+'">');
			formaObrisi.append('<input type="submit" value="Delete friend" onclick="otvoriModal(\'id01\')">');
			var td = $('<td></td>');
			td.append(formaObrisi);
			tr.append(td);
		
		}*/
		/*if (uloga == "ROLE_USER") {
			
			tr.append("<td align= 'center'><button id=\'deleteFriendBtn\' onClick = deleteFriend(" + korisnik.id +")>Delete friend</button></td>");
		
		}*/
		if(uloga=="ROLE_USER"){
			tr.append("<td align= 'center'><button id=\'deleteFriendBtn\' onClick = \"formaObrisi1(event, "+korisnik.id+", \'id01\', \'Are you sure you want to delete friend: "+korisnik.korisnickoIme+"?\'), otvoriModal(\'id01\')\">Delete friend</button></td>");
		}
		
		$("#friendsTable").find("tbody").append(tr);
		
	})
	$('#friendsTable').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });
}

/*function deleteFriend(id)*/
$(document).on('submit', '#deleteFriendForma', function(e){
	e.preventDefault();
	//console.log("trying to delete friend with id " + id);
	var id=$('#identifikatorFriend').val();
	$.ajax({
		type:'DELETE',
		url:'/api/friends/' + id,
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(){
			$('#friend_' + id).remove();
			$('#friendsTable').DataTable().clear().destroy();
			zatvoriModal('id01');
			findAllFriends();
			$('#newFriendsTable').DataTable().clear().destroy();
			findAllPotentialFriends();
			$.bootstrapGrowl("Friend deleted!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If false then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		},
		error:function(){
			$.bootstrapGrowl("There was an error while deleting friend!", {
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

function renderRequests(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");
	
	$("#friendRequestsTable").find("tr:gt(0)").remove();
	$("#friendRequestsTable").find("th:gt(5)").remove();
	$.each(list, function(index, korisnik){
		var tr=$("<tr id=\'request_" + korisnik.id + "\' ></tr>");
		console.log(korisnik.ime);

		tr.append('<td>' + korisnik.ime + '</td>'+ '<td>' + korisnik.prezime + '</td>'+'<td>' + korisnik.korisnickoIme + '</td>' + '<td>'
				+ korisnik.mail + '</td>');

		if (uloga == "ROLE_USER") {
			tr.append("<td align= 'center'><button id=\'acceptRequestBtn\' onClick = acceptRequest(" + korisnik.id +")>Accept</button></td>");
			tr.append("<td align= 'center'><button id=\'rejectRequestBtn\' onClick = rejectRequest(" + korisnik.id +")>Reject</button></td>");
		}
		
		$("#friendRequestsTable").find("tbody").append(tr);
		
	})
	$('#friendRequestsTable').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 },
	                     { "orderable": false, "targets": 5 }
	                   ]
	  });
}

function acceptRequest(id){
	//alert("Accepted user " + id);
	console.log("trying to accept request from sender with id " + id);
	$.ajax({
		type:'PUT',
		url:'/api/friendrequests/accept/' + id,
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(){
			//brisem acceptovan red, i ponovo crtam prijatelje
			$('#request_' + id).remove();
			$('#friendRequestsTable').DataTable().clear().destroy();
			findAllRequests();
			$('#friendsTable').DataTable().clear().destroy();
			findAllFriends();
			$.bootstrapGrowl("Request accepted!", {
				  ele: 'body', // which element to append to
				  type: 'info', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		},
		error:function(){
			$.bootstrapGrowl("Error while accepting request!", {
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
}
function rejectRequest(id){
	//alert("Rejected user " + id);
	console.log("trying to reject request from sender with id " + id);
	$.ajax({
		type:'PUT',
		url:'/api/friendrequests/reject/' + id,
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(){
			//brisem rejectovan red
			$('#request_' + id).remove();
			$('#friendRequestsTable').DataTable().clear().destroy();
			findAllRequests();
			$.bootstrapGrowl("Request rejected!", {
				  ele: 'body', // which element to append to
				  type: 'info', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
			
		},
		error:function(){
			$.bootstrapGrowl("Error while rejecting request!", {
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
}

function renderPotentionalFriends(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");
	
	$("#newFriendsTable").find("tr:gt(0)").remove();
	$("#newFriendsTable").find("th:gt(4)").remove();
	$.each(list, function(index, korisnik){
		var tr=$("<tr id=\'newfriend_" + korisnik.id + "\' ></tr>");
		console.log(korisnik.ime);

		tr.append('<td>' + korisnik.ime + '</td>'+ '<td>' + korisnik.prezime + '</td>'+'<td>' + korisnik.korisnickoIme + '</td>' + '<td>'
				+ korisnik.mail + '</td>');

		if (uloga == "ROLE_USER") {
			tr.append("<td align= 'center'><button id=\'addFriendBtn\' onClick = addFriend(" + korisnik.id +")>Add as friend</button></td>");
		}
		
		$("#newFriendsTable").find("tbody").append(tr);
		
	})
	$('#newFriendsTable').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });
}

function addFriend(id){
	console.log('send friend request to id ' + id);
	$.ajax({
		type:'POST',
		url:'/api/friends/sendrequest/' + id,
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(){
			//delete row
			$('#newfriend_' + id).remove();
			$('#newFriendsTable').DataTable().clear().destroy();
			findAllPotentialFriends();
			$.bootstrapGrowl("Friend request sent!", {
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
		error:function(){
			$.bootstrapGrowl("Error while sending request!", {
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
}


