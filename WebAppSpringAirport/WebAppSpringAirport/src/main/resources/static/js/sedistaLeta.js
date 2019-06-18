/**
 * 
 */

findAllFlights();
findAllDestinations();
findAllFriendsForInvitation();

function findAllFriendsForInvitation(){
	$.ajax({
		type:'GET',
		url:'/api/friends/',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:renderFrendovi
	})
}

function renderFrendovi(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$('#prikazFrendovaTabela').DataTable().clear().destroy();
	
	$.each(list, function(index, frend){
		var tr=$('<tr id="frend_'+frend.id+'"></tr>');
		var slika=frend.slika;
		if(slika==null || slika==""){
			slika="../slike/avatar.png";
		}
		tr.append('<td align="center" width=100px height=100px><div class="divEntitet"><img class="imgEntitet" src="'+slika+'"></div></td>');
		tr.append('<td>'+frend.ime+' '+frend.prezime+'</td>');
		tr.append('<td>'+frend.korisnickoIme+'</td>');
		//tr.append('<td><input type="button" value="Invite"></td>');
		//tr.append('<td><button onclick="pozvanPrijatelj(event, this)"><input type="hidden" id="friend_name" value="'+frend.ime+' '+frend.prezime+'"><input type="hidden" id="friend_id" value="'+frend.id+'">Invite</button></td>')
		tr.append('<td><input type="checkbox" class="invited-friend" id="friend_id'+frend.id+'" name="'+frend.ime+', '+frend.prezime+'" value="'+frend.id+'" onclick="selektovanFrend(this)"></td>');
		$('#prikazFrendovaTabela').append(tr);
	})
	
	$('#prikazFrendovaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 3 }
	                   ]
	  });
}
function selektovanFrend(checkbox){
	var br_sedista=$('#selected-seats li').length; //broj selektovanih sedista
	//update progress bar
	var br_pozivnica=br_sedista-1;
	var procenat=100/br_pozivnica;
	console.log(br_sedista);
	console.log(br_pozivnica);
	console.log(procenat);
	var stari_procenat=$('.progress-bar').prop('id');
	var nova_vrednost;
	console.log(stari_procenat);
	
	if($(checkbox).prop('checked')==true){
		nova_vrednost=parseInt(stari_procenat, 10)+procenat;
		$('.progress-bar').css('width', nova_vrednost+'%');
		$('.progress-bar').attr('id', nova_vrednost);
	}else{
		nova_vrednost=parseInt(stari_procenat, 10)-procenat;
		$('.progress-bar').css('width',nova_vrednost+'%');
		$('.progress-bar').attr('id', nova_vrednost);
	}
	
	
}
/*function pozvanPrijatelj(e, btn){
	e.preventDefault();
	
	var br_sedista=$('#broj-sedista-hidden').val();
	var frend_name=$(btn).find('#friend_name').val();
	var frend_id=$(btn).find('#friend_id').val();
	$('#pozvan_za_'+br_sedista).text(frend_name);
	
	$('#pozvan_za_'+br_sedista+'_frend').val(frend_id);
	zatvoriModal('id01');
	
}*/
function findAllFlights(){
	$.ajax({
		type : 'GET',
		url : 'api/let',
		dataType : 'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success : renderLetovi
	})
}

function findAllDestinations(){
	$.ajax({
		type:'GET',
		url:'api/destinacije',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success : renderDestinacije
	});
}

function renderDestinacije(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$('#from-dest').find('option:gt(0)').remove();
	$('#to-dest').find('option:gt(0)').remove();
	
	$.each(list, function(index, destinacija){
		$('#from-dest').append('<option value="'+(index+1)+'">'+destinacija.adresa+'</option>');
		$('#to-dest').append('<option value="'+(index+1)+'">'+destinacija.adresa+'</option>');
	})
}
function renderLetovi(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");
	
	if(list.length == 0){
		console.log("Not found data");
		notify("No flights found!", 'info');
		
		//return;
	}
	$('#prikazLetovaTabela').DataTable().clear().destroy();
	$("#prikazLetovaTabela").find("tr:gt(0)").remove();
	$("#prikazLetovaTabela").find("th:gt(7)").remove();
	
	$.each(list, function(index, flight) {
		/*var tr=$('<tr id="flight_'+flight.id+'"></tr>');
		var slika=flight.slika;
		if(slika==null || slika==""){
			slika = "../slike/avatar.png";
		}
		tr.append('<td align="center" width=100px height=100px><div class="divEntitet"><img class="imgEntitet" src="'+slika+'"></div></td>');
		*/
		console.log('broj sedista: '+flight.sedista.length);
		var trow=get_row(flight, "flight", localStorage.getItem('uloga'), 'nema', 'nema');
		trow.append('<td><a href="#detaljna-sedista"><button  onclick="selektovanLet(this)"><input type="hidden" id="'+flight.id+'">Select</button></a></td>');
		$('#prikazLetovaTabela').append(trow);
	})
	if ( ! $.fn.DataTable.isDataTable( '#prikazLetovaTabela' ) ) {
	$('#prikazLetovaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "order":[[1,'asc']],
	      "columnDefs": [
	                     { "orderable": false, "targets": 0 },
	                     { "orderable": false, "targets": 7 }
	                   ]
	  });
	}
}

function pokupiPozvanePrijatelje(e){
	e.preventDefault();
	
	$('#invited-friends').empty();
	$.each($('.invited-friend:checked'), function(index, fren){
		console.log(fren);
		
		var fren_id=fren.value;
		var li=$('<li id="'+fren_id+'">'+fren.name+'</li>');
		$('#invited-friends').append(li);
	})
	
	zatvoriModal('id01');
	
	
}

function previewRezervacije(){
	var lista_sedista=$('#selected-seats li');
	if(lista_sedista.length==0){
		console.log("nece moci");
		notify("Could not proceed reservation. You should reserve at least one seat!", 'info');
		return;
	}
	
	
	var lista_pozvanih=$('#invited-friends li');
	if(lista_sedista.length - 1 != lista_pozvanih.length){
		notify("Could not proceed reservation. Invite friends!", 'info');
		return;
	}
	
	var pozvani_prijatelji=[];
	$.each(lista_pozvanih, function(index, item){
		pozvani_prijatelji.push(item.id);
	})
	
	
	var lista_soba=$('#selected-rooms li');
	var lista_vozila=$('#selected-cars li');
	
	var let_id=$('#id-odabranog-leta').val();
	var sedista=[];
	var sobe= $('.cart-item-room:checked').map(function() {
		return this.value;
	}).get();
	
	var vozila=$('.cart-item-car:checked').map(function() {
		return this.value;
	}).get();
	
	var sobeRezervisaneOd=$('#datepicker6').val();
	var broj_nocenja=$('#broj_nocenja').val();
	
	var vozilaRezervisanaOd=$('#datepicker7').val();
	var vozilaRezervisanaDo=$('#datepicker8').val();
	
	var total=parseInt($('#total').text(), 10)+ parseInt($('#total-rooms').text(), 10)+parseInt($('#total-cars').text(), 10);
	console.log('total: '+total);
	$.each(lista_sedista, function(index, item){
		var tokens=item.id.split('-');
		sedista.push(tokens[2]);
		var row_col=tokens[2].split('_');
		var row=row_col[0];
		var col=row_col[1];
		console.log('red: '+row+', kolona: '+col);
	})
	$.ajax({
		type:'POST',
		url:'api/reserve/preview',
		contentType:'application/json',
		dataType:'json',
		data:rezervacijaToJSONadd(let_id, sedista, sobe, vozila, pozvani_prijatelji, total, sobeRezervisaneOd, broj_nocenja, vozilaRezervisanaOd, vozilaRezervisanaDo),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			$('#id02 #relacija').text($('#relacija-leta').text());
			$.each(data.odabranaSedista, function(index, sediste){
				$('#id02 #sedista-preview').append('<li>Seat: '+sediste.row_col+', Class: '+sediste.klasa+'</li>');
			})
			$.each(data.odabraneSobe, function(index, soba){
				$('#id02 #sobe-preview').append('<li>'+soba.opis+', '+soba.hotel.naziv+'</li>');
			})
			$.each(data.odabranaVozila, function(index, vozilo){
				$('#id02 #car-preview').append('<li>'+vozilo.model+', '+vozilo.filijala.rentACar.naziv+'</li>');
			})
			$.each(data.korisnici, function(index, korisnik){
				$('#id02 #pozvani-lista-preview').append('<li>'+korisnik.ime+' '+korisnik.prezime+'</li>');
			})
			otvoriModal('id02');
		}
	});
	
}
function pokupiRezervisanaSedista(e){
	e.preventDefault();
	var lista_sedista=$('#selected-seats li');
	if(lista_sedista.length==0){
		console.log("nece moci");
		notify("Could not proceed reservation. You should reserve at least one seat!", 'info');
		return;
	}
	
	
	var lista_pozvanih=$('#invited-friends li');
	if(lista_sedista.length - 1 != lista_pozvanih.length){
		notify("Could not proceed reservation. Invite friends!", 'info');
		return;
	}
	var pozvani_prijatelji=[];
	$.each(lista_pozvanih, function(index, item){
		pozvani_prijatelji.push(item.id);
	})
	console.log('pozvani prijatelji: '+pozvani_prijatelji);
	
	
	var lista_soba=$('#selected-rooms li');
	var lista_vozila=$('#selected-cars li');
	console.log(lista_sedista);
	
	var let_id=$('#id-odabranog-leta').val();
	var sedista=[];
	var sobe= $('.cart-item-room:checked').map(function() {
		return this.value;
	}).get();
	console.log('odabrane sobe: '+sobe);
	var vozila=$('.cart-item-car:checked').map(function() {
		return this.value;
	}).get();
	console.log('odabrana: vozila: '+vozila);
	console.log('odabran let: '+let_id);
	var sobeRezervisaneOd=$('#datepicker6').val();
	var broj_nocenja=$('#broj_nocenja').val();
	//var sobeRezervisaneDo=$('#datepicker3').val();
	var vozilaRezervisanaOd=$('#datepicker7').val();
	var vozilaRezervisanaDo=$('#datepicker8').val();
	//var broj_nocenja=$('#broj_nocenja').val();
	var total=parseInt($('#total').text(), 10)+ parseInt($('#total-rooms').text(), 10)+parseInt($('#total-cars').text(), 10);
	console.log('total: '+total);
	$.each(lista_sedista, function(index, item){
		var tokens=item.id.split('-');
		sedista.push(tokens[2]);
		var row_col=tokens[2].split('_');
		var row=row_col[0];
		var col=row_col[1];
		console.log('red: '+row+', kolona: '+col);
	})
	console.log("odabrana sedista: "+sedista);
	$.ajax({
		type:'POST',
		url:'api/reserve',
		contentType:'application/json',
		dataType:'json',
		data:rezervacijaToJSONadd(let_id, sedista, sobe, vozila, pozvani_prijatelji, total, sobeRezervisaneOd, broj_nocenja, vozilaRezervisanaOd, vozilaRezervisanaDo),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			notify("Successful reservation!", 'info');
			//redirekcija na profil, trebalo bi da defaultni tab bude myreservations
		}
	});
	//$('#hotels-tab').click();
}
function rezervacijaToJSONadd(let_id, sedista, sobe, vozila, pozvani_prijatelji, cena, sobeOd, broj_nocenja, vozilaOd, vozilaDo){
	return JSON.stringify({
		"sedista":sedista,
		"id_leta":let_id,
		"sobe":sobe,
		"vozila":vozila,
		"pozvani_prijatelji":pozvani_prijatelji,
		"ukupnaCena":cena,
		"sobaOD":sobeOd,
		"brojNocenja":broj_nocenja,
		"voziloOD":vozilaOd, 
		"voziloDO":vozilaDo
	});
}

function pokupiNeregPutnik(e){
	e.preventDefault();
	console.log('nereg putnik');
	var ime=$('#nereg-ime').val();
	var prezime=$('#nereg-prezime').val();
	var pasos=$('#nereg-pasos').val();
	var datum=$('#nereg-datum-rodjenja').val();
	var email=$('#nereg-email').val();
	$('#nereg-lista').append('<li>'+ime+' '+prezime+' '+pasos+' '+datum+' '+email+'</li>')
}
var firstSeatLabel = 1;
function renderDetaljanLet(){
	firstSeatLabel=1;
	$('#detaljna-sedista').empty();
	$('#detaljna-sedista').append('<div class="container">'+
				'<h3 id="relacija-leta"></h3>'+
				'<div id="seat-map">'+
					'<div class="front-indicator">Front</div>'+

				'</div>'+
				'<div class="booking-details">'+
					'<h2>Booking Details</h2>'+

					'<h3>'+
						'Selected Seats (<span id="counter">0</span>):'+
					'</h3>'+
					'<ul id="selected-seats"></ul>'+

					'Total: <b>$<span id="total">0</span></b>'+
					'<br/><div class="grid-container2" id="user-container"><div class="grid-item invited" id="invited-friends-container"><h3>Invited Friends</h3>'+
					'<ul id="invited-friends"></ul>'+
					'<br/><button class="invite-button" disabled onclick="otvoriModal(\'id01\')">Invite Friends</button></div>'+
					
					'<div class="grid-item nereg" id="nereg-container"><h3>Other Passengers</h3><ul id="nereg-lista"></ul>'+
					'<br/><button class="add-nereg-button" disabled onclick="otvoriModal(\'id03\')">Add Passenger</button></div></div>'+
					'<button class="checkout-button"'+
						'onclick="previewRezervacije()">Checkout &raquo;</button>'+
					'<button class=\'next-button\' onclick="$(\'#hotels-tab\').click()">Next &raquo;</button>'+
					'<br><br><br>'+
					'<div id="legend"></div>'+
				'</div>'+
			'</div>');
}

	
		function selektovanLet(btn) {
				renderDetaljanLet();
				var let_id=$(btn).find('input[type=hidden]').attr('id');
				$('#id-odabranog-leta').val(let_id);
				//dobaviti let
				$.ajax({
					type:'GET',
					url:'api/let/'+let_id,
					dataType:'json',
					beforeSend : function(request) {
						request.setRequestHeader("Authorization", "Bearer "
								+ localStorage.getItem("accessToken"));
					},
					success:function(data){
						$('#relacija-leta').text(data.pocetnaDestinacija+'-'+data.krajnjaDestinacija);
						$('.seatCharts-row').remove();
						//$('.booking-details').empty();
						var br_kolona=data.brojKolona;
						var br_redovaFC=data.brojRedovaFC;
						var br_redovaEC=data.brojRedovaEC;
						var br_redovaBC=data.brojRedovaBC;
						var lista=[];
						for(var i=1; i<=br_redovaFC; i++){
							var red='';
							for(var j=1; j<=br_kolona; j++){
								red+='f';
							}
							lista.push(red);
						}
						for(var i=1; i<=br_redovaEC; i++){
							var red='';
							for(var j=1; j<=br_kolona; j++){
								red+='e';
							}
							lista.push(red);
						}
						for(var i=1; i<=br_redovaBC; i++){
							var red='';
							for(var j=1; j<=br_kolona; j++){
								red+='b';
							}
							lista.push(red);
						}
						console.log(lista)
						var $cart = $('#selected-seats'),
						$counter = $('#counter'),
						$total = $('#total'),
						sc = $('#seat-map').seatCharts({
						map: lista,
						seats: {
							f: {
								price   : 100,
								classes : 'first-class', //your custom CSS class
								category: 'First Class'
							},
							e: {
								price   : 40,
								classes : 'economy-class', //your custom CSS class
								category: 'Economy Class'
							},
							b: {
								price:90,
								classes:'business-class',
								category:'Business Class'
							}					
						
						},
						naming : {
							top : false,
							getLabel : function (character, row, column) {
								return firstSeatLabel++;
							},
						},
						legend : {
							node : $('#legend'),
						    items : [
								[ 'f', 'available',   'First Class' ],
								[ 'e', 'available',   'Economy Class'],
								[ 'b', 'available',   'Business Class'],
								[ 'f', 'unavailable', 'Already Booked']
						    ]					
						},
						click: function () {
							if (this.status() == 'available') {
								//let's create a new <li> which we'll add to the cart items
								//var invite_friend="";
								
								$('<li>'+this.data().category+' Seat # '+this.settings.label+': <b>$'+this.data().price+'</b> <a href="#detaljna-sedista" class="cancel-cart-item">[cancel]</a></li>')
									.attr('id', 'cart-item-'+this.settings.id)
									.data('seatId', this.settings.id)
									.appendTo($cart);
								console.log('duzina: '+$('#selected-seats li').length);
								if($('#selected-seats li').length>=2){
									//invite_friend='<input type="button" value="Invite a Friend" onclick="otvoriModal(\'id01\'), $(\'#broj-sedista-hidden\').val('+this.settings.label+')">';
									$('.add-nereg-button').prop('disabled', false);
									$('.invite-button').prop('disabled', false);
									
								}else{
									$('.add-nereg-button').prop('disabled', true);
									$('.invite-button').prop('disabled', true);
									
								}
								/*
								 * Lets update the counter and total
								 *
								 * .find function will not find the current seat, because it will change its stauts only after return
								 * 'selected'. This is why we have to add 1 to the length and the current seat price to the total.
								 */
								$counter.text(sc.find('selected').length+1);
								$total.text(recalculateTotal(sc)+this.data().price);
								
								return 'selected';
							} else if (this.status() == 'selected') {
								//update the counter
								$counter.text(sc.find('selected').length-1);
								//and total
								$total.text(recalculateTotal(sc)-this.data().price);
							
								//remove the item from our cart
								$('#cart-item-'+this.settings.id).remove();
								//$('#selected-seats').find('li:eq(0)').find('input[type=button]').remove();
								if($('#selected-seats li').length<=1){
									//invite_friend='<input type="button" value="Invite a Friend" onclick="otvoriModal(\'id01\'), $(\'#broj-sedista-hidden\').val('+this.settings.label+')">';
									$('.invite-button').prop('disabled', true);
									$('.add-nereg-button').prop('disabled', true);
								}
								//kad se neko sediste deselektuje, brisu se liste pozvanih jbg
								$('#invited-friends').empty();
								$('#nereg-lista').empty();
								//seat has been vacated
								return 'available';
							} else if (this.status() == 'unavailable') {
								//seat has been already booked
								return 'unavailable';
							} else {
								return this.style();
							}
						}
					});

					//this will handle "[cancel]" link clicks
					$('#selected-seats').on('click', '.cancel-cart-item', function () {
						//let's just trigger Click event on the appropriate seat, so we don't have to repeat the logic here
						sc.get($(this).parents('li:first').data('seatId')).click();
					});

					//let's pretend some seats have already been booked
					//sc.get(['1_2', '4_1', '7_1', '7_2']).status('unavailable');
					var rezervisana=[];
					$.each(data.sedista, function(index, sediste){
						console.log($('#'+sediste.row_col));
						if(sediste.rezervisano==true){
							rezervisana.push(sediste.brojReda+'_'+sediste.brojKolone);
						}
					})
					//rezervisana.push(5+'_'+3);
					sc.get(rezervisana).status('unavailable');
					}
					
				})
				
		
		};

		function recalculateTotal(sc) {
			var total = 0;
		
			//basically find every selected seat and sum its price
			sc.find('selected').each(function () {
				total += this.data().price;
			});
			
			return total;
		}