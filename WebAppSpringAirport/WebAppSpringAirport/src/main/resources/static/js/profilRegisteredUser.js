findAllFriends();
findAllRequests();
findAllPotentialFriends();
findAllReservations();
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
function renderReservations(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$("#activeReservationsTable").find("tr:gt(0)").remove();
	$("#activeReservationsTable").find("th:gt(5)").remove();
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
		
		$("#activeReservationsTable").find("tbody").append(tr);
	})
	$('#activeReservationsTable').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });
	/*
	$('#reservationHistoryTable').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });*/
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