/**
 * 
 */
uloga="";

$(document).ready(function(){
	uloga=localStorage.getItem("uloga");
	if(uloga=="ROLE_ADMIN"){
		$("#nav-bar").append('<li><button class="dodajKorisnikaBtn" onclick="otvoriModal(\'id03\')">New Admin</button></li>');

		findAll();
		findPoints();
	}
});
function findAll(){
	$.ajax({
		type:'GET',
		url:'api/users',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:renderKorisnici
	})
}

$(document).on('submit', "#userPointsForma", function(e){
	e.preventDefault();
	var max = $("#maxPoints").val();
	var km =  $("#kmForPoints").val();
	$.ajax({
		type:'POST',
		url:'/api/points/',
		dataType:'json',
		contentType: 'application/json',
		data : JSON.stringify({"kmZaBroj" : km, "maxBroj" : max}),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success: function(data)
        {
			$.bootstrapGrowl("Bonus point logic changed succesfully!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'center', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the demo timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
        }
	})
})

function findPoints(){
	$.ajax({
		type:'GET',
		url:'/api/points/',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success: function(data)
        {
			$("#maxPoints").val(data.maxBroj);
			$("#kmForPoints").val(data.kmZaBroj);
        }
	})
}


function renderKorisnici(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");
	
	if(uloga=="ROLE_ADMIN"){
		var th_nbsp=$('<th>&nbsp;</th>');
		$('#prikazKorisnikaTabela').find('tr:eq(0)').append(th_nbsp);
	}
	$("#prikazKorisnikaTabela").find("tr:gt(0)").remove();
	$("#prikazKorisnikaTabela").find("th:gt(7)").remove();
	$.each(list, function(index, korisnik){
		/*var tr=$('<tr id="korisnik_' + korisnik.id + '"></tr>');
		console.log(korisnik.ime);
		console.log(korisnik.authorities);
        var prom = korisnik.authorities[0].authority;
        var ul="";
		if (korisnik.slika == null){
			korisnik.slika = "../slike/avatar.png";
		}
        if (prom == "ROLE_ADMIN"){
            ul = "SYSTEM ADMIN";
        } else if(prom == "ROLE_USER") {
            ul = "REGISTERED USER"
        } else if(prom == "ROLE_RENT") {
            ul = "ADMIN RENT-A-CAR";
        } else if (prom == "ROLE_HOTEL") {
            ul = "ADMIN HOTEL";
        } else {
            ul = "ADMIN AVIOCOMPANY";
        }
		tr.append('<td align="center" width=100px height=100px>'+ '<div class="divEntitet" id="divKorisnik">' +
				'<img src=" ' + korisnik.slika +' " id="imgProfilnaKorisnici" class="imgEntitet"> ' + '</div>' +
				'</td>'+ '<td align="center">' + korisnik.ime + '</td>'+ '<td align="center">' + 
				korisnik.prezime + '</td>'+'<td align="center">' + korisnik.korisnickoIme + '</td>' + '<td>'
				+ korisnik.mail + '</td>' + '<td align="center">' + ul + '</td>');
		
		if (uloga == "ROLE_ADMIN") {
			var formaObrisi = $('<form id="formaObrisi" onsubmit="formaObrisi(event, this, \'identifikator\', \'imePrezimeUsername\')"></form>');
			formaObrisi.append('<input type="hidden" value="' + korisnik.id + '">');
			formaObrisi.append('<input id="hiddenKorisnik" type="hidden" value="' + korisnik.ime+" "+korisnik.prezime + ", "+korisnik.korisnickoIme+'">');
			formaObrisi.append('<input type="submit" value="Delete" onclick="otvoriModal(\'id02\')">');
			var td = $('<td></td>');
			td.append(formaObrisi);
			tr.append(td);
		
		}*/
		
		$('#prikazKorisnikaTabela').append(get_row(korisnik, "user", localStorage.getItem('uloga'), 'id02', 'nema'));
		
	})
	$('#prikazKorisnikaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });
}



$(document).on('submit', ".modal-content2", function(e){
	e.preventDefault();
	var id=$("#identifikator").val();
	$.ajax({
		type : 'DELETE',
		url : '/api/users/' + id,
		/*
		 * Milan: drugi nacin da token posaljete u zahtevu kroz ajax poziv kroz
		 * header
		 */
		headers : {
			Authorization : "Bearer " + localStorage.getItem("accessToken")
		},
		success : function() {
			console.log('blaa');
			$('#user_' + id).remove();
			$("#id02").css("display", "none");
			$("body").removeClass("modal-open");
			$('#prikazKorisnikaTabela').DataTable().clear().destroy();
			findAll();
			$.bootstrapGrowl("User deleted!", {
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
			$.bootstrapGrowl("Error while deleting user!", {
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
	})
	
})

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

$(document).on('submit', "#newUserForma", function(e){
	e.preventDefault();
	console.log("dodavanje admina");
	var ime = $('#firstName').val();
	var prezime = $('#lastName').val();
	var korisnickoIme=$('#userName').val();
	var lozinka="$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra"; //sifra je 123
	var mail=$('#email').val();
	if  (!validateEmail(mail)){
		//alert("Bad email format");
		$.bootstrapGrowl("Bad email format!", {
			  ele: 'body', // which element to append to
			  type: 'danger', // (null, 'info', 'danger', 'success')
			  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
			  align: 'right', // ('left', 'right', or 'center')
			  width: 'auto', // (integer, or 'auto')
			  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
			  allow_dismiss: false, // If true then will display a cross to close the popup.
			  stackup_spacing: 10 // spacing between consecutively stacked growls.
			});
		return;
	}
	var e = document.getElementById("ulogeDDL");
	var uloga = e.options[e.selectedIndex].value;
	var adminOf = $("#adminOfSel option:selected").val();

	$.ajax({
		type:'POST',
		url:"api/users",
		contentType:'application/json',
		dataType:'json',
		data:korisnikToJSONadd(ime,prezime,korisnickoIme, lozinka, mail, uloga, adminOf),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			$("#id03").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('newUserForma');
			$('#prikazKorisnikaTabela').DataTable().clear().destroy();
			findAll();
			//dodajNoviEntitet('prikazKorisnikaTabela', get_row(data, "user", localStorage.getItem('uloga'), 'id02', 'id01'));
			$.bootstrapGrowl("User added!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 2000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		},
		error:function(){
			//alert("Username or email is taken");
			$.bootstrapGrowl("Username or email is taken!", {
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
function korisnikToJSONadd(ime,prezime,korisnickoIme, lozinka, mail, uloga, adminOf){
	return JSON.stringify({
		"ime":ime,
		"prezime":prezime,
		"korisnickoIme":korisnickoIme,
		"lozinka":lozinka,
		"mail":mail,
		"uloga":uloga,
		"adminOf":adminOf
	});
}



$(document).ready(function(){
	//If parent option is changed
	$("#ulogeDDL").change(function() {
	        var parent = $(this).val(); //get option value from parent
	       
	        switch(parent){ //using switch compare selected option and populate child
	              case 'avio':
	                renderAvioOptions();
	                break;
	              case 'rent':
	            	renderRentOptions();
	                break;             
	              case 'hotel':
	            	renderHotelOptions();
	                break;
	              case 'sis':
	            	renderSisOptions();
	                break;
	            default: //default child option is blank
	                $("#child_selection").html('');  
	                break;
	           }
	});
	
	function renderSisOptions()
	{
	    $("#adminOfSel").html(""); //reset child options
	    $("#adminOfSel").prop('disabled', true);
        $("#adminOfSel").append("<option value=System>System</option>");
	    
	}
	function renderAvioOptions()
	{
	    $("#adminOfSel").html(""); //reset child options
	    $("#adminOfSel").prop('disabled', false);
	    $.ajax({
			type : 'GET',
			url : 'api/avioKompanije',
			dataType : 'json',
			/*
			 * Milan: jedan nacin da token koji ste dobili prilikom logina posaljete
			 * kroz ajax zahtev kroz header
			 */
			beforeSend : function(request) {
				request.setRequestHeader("Authorization", "Bearer "
						+ localStorage.getItem("accessToken"));
			},
			success : function (data){
				var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
				$.each(list, function(index, avio) {
					$("#adminOfSel").append("<option value=\"avio_"+avio.id+"\">"+avio.naziv+"</option>");
					console.log("avio_" + avio.id);
				})
			}
		})
	    
	}
	function renderRentOptions()
	{
	    $("#adminOfSel").html(""); //reset child options
	    $("#adminOfSel").prop('disabled', false);
	    $.ajax({
			type : 'GET',
			url : 'api/rentACars',
			dataType : 'json',
			/*
			 * Milan: jedan nacin da token koji ste dobili prilikom logina posaljete
			 * kroz ajax zahtev kroz header
			 */
			beforeSend : function(request) {
				request.setRequestHeader("Authorization", "Bearer "
						+ localStorage.getItem("accessToken"));
			},
			success : function (data){
				var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
				$.each(list, function(index, rent) {
					$("#adminOfSel").append("<option value=\"rent_"+rent.id+"\">"+rent.naziv+"</option>");
					console.log("rent_" + rent.id);
				})
			}
		})
	    
	}
	function renderHotelOptions()
	{
	    $("#adminOfSel").html(""); //reset child options
	    $("#adminOfSel").prop('disabled', false);
	    $.ajax({
			type : 'GET',
			url : 'api/hotels',
			dataType : 'json',
			/*
			 * Milan: jedan nacin da token koji ste dobili prilikom logina posaljete
			 * kroz ajax zahtev kroz header
			 */
			beforeSend : function(request) {
				request.setRequestHeader("Authorization", "Bearer "
						+ localStorage.getItem("accessToken"));
			},
			success : function (data){
				var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
				$.each(list, function(index, hotel) {
					$("#adminOfSel").append("<option value=\"hotel_"+hotel.id+"\">"+hotel.naziv+"</option>");
					console.log("hotel_" + hotel.id);
				})
			}
		})    
	}
})

