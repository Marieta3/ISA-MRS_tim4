findAllFriends();
findAllRequests();
findAllPotentialFriends()
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
	//get requests and render 
	/*
	console.log("requestss");
	$("#friendRequestsTable").find("tr:gt(0)").remove();
	$("#friendRequestsTable").find("th:gt(5)").remove();
	
	var tr=$("<tr></tr>");
	tr.append("<td>Ime1</td><td>Prz1</td><td>user1</td><td>mail</td><td>btnAccept</td><td>btnDecline</td>");
	$("#friendRequestsTable").find("tbody").append(tr);*/
	$('#friendRequestsTable').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 },
	                     { "orderable": false, "targets": 5 }
	                   ]
	  });
}

function findAllPotentialFriends(){
	//get new friends and render
	/*
	console.log("new friendss");
	$("#newFriendsTable").find("tr:gt(0)").remove();
	$("#newFriendsTable").find("th:gt(4)").remove();
	
	var tr=$("<tr></tr>");
	tr.append("<td>Ime1</td><td>Prz1</td><td>user1</td><td>mail</td><td>btnAddAsFriend</td>")
	$("#newFriendsTable").find("tbody").append(tr);*/
	$('#newFriendsTable').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });
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
		if (uloga == "ROLE_USER") {
			
			tr.append("<td align= 'center'><button class=\'deleteFriendBtn\' onClick = deleteFriend(" + korisnik.id +")>Delete friend</button></td>");
		
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

function deleteFriend(id){
	console.log("trying to delete friend with id " + id);
	$.ajax({
		type:'DELETE',
		url:'/api/friends/' + id,
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(){
			$('#friend_' + id).remove();
		},
		error:function(){
			alert("There was an error while delete friend'");
		}
	})
	
}

$(document).on('submit', "#deleteFriendForma", function(e){
	e.preventDefault();
	var id=$("#identifikator").val();
	$.ajax({
		type : 'DELETE',
		url : '/api/friends/' + id,
		/*
		 * Milan: drugi nacin da token posaljete u zahtevu kroz ajax poziv kroz
		 * header
		 */
		headers : {
			Authorization : "Bearer " + localStorage.getItem("accessToken")
		},
		success : function() {
			console.log('blaa');
			$('#friend_' + id).remove();
			$("#id01").css("display", "none");
			$("body").removeClass("modal-open");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("Error at friend deletion");
			alert(errorThrown);
		}
	})
	
})
