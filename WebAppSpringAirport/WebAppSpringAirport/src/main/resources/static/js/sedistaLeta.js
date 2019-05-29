/**
 * 
 */

findAllFlights();
findAllDestinations();
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
function pokupiRezervisanaSedista(){
	var lista_sedista=$('#selected-seats li');
	if(lista_sedista.length==0){
		console.log("nece moci");
		notify("Could not proceed reservation. You should reserve at least one seat!", 'info');
		return;
	}
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
	var sobeRezervisaneOd=$('#datepicker2').val();
	var sobeRezervisaneDo=$('#datepicker3').val();
	var vozilaRezervisanaOd=$('#datepicker4').val();
	var vozilaRezervisanaDo=$('#datepicker5').val();
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
		data:rezervacijaToJSONadd(let_id, sedista, sobe, vozila, total, sobeRezervisaneOd, sobeRezervisaneDo, vozilaRezervisanaOd, vozilaRezervisanaDo),
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
function rezervacijaToJSONadd(let_id, sedista, sobe, vozila, cena, sobeOd, sobeDo, vozilaOd, vozilaDo){
	return JSON.stringify({
		"sedista":sedista,
		"id_leta":let_id,
		"sobe":sobe,
		"vozila":vozila,
		"ukupnaCena":cena,
		"sobaOD":sobeOd,
		"sobaDO":sobeDo,
		"voziloOD":vozilaOd, 
		"voziloDO":vozilaDo
	});
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

					'<button class="checkout-button"'+
						'onclick="pokupiRezervisanaSedista()">Checkout &raquo;</button>'+
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
								$('<li>'+this.data().category+' Seat # '+this.settings.label+': <b>$'+this.data().price+'</b> <a href="#" class="cancel-cart-item">[cancel]</a></li>')
									.attr('id', 'cart-item-'+this.settings.id)
									.data('seatId', this.settings.id)
									.appendTo($cart);
								
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