/**
 * 
 */

findAllFlights();
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
	$("#prikazLetovaTabela").find("th:gt(6)").remove();
	$.each(list, function(index, flight) {
		/*var tr=$('<tr id="flight_'+flight.id+'"></tr>');
		var slika=flight.slika;
		if(slika==null || slika==""){
			slika = "../slike/avatar.png";
		}
		tr.append('<td align="center" width=100px height=100px><div class="divEntitet"><img class="imgEntitet" src="'+slika+'"></div></td>');
		*/
		var trow=get_row(flight, "flight", localStorage.getItem('uloga'), 'nema', 'nema');
		trow.append('<td><button  onclick="selektovanLet(this)"><input type="hidden" id="'+flight.id+'">Select</button></td>');
		$('#prikazLetovaTabela').append(trow);
	})
	$('#prikazLetovaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });

}
function pokupiRezervisanaSedista(){
	var lista=$('#selected-seats li');
	console.log(lista);
	$.each(lista, function(index, item){
		var tokens=item.id.split('-');
		var row_col=tokens[2].split('_');
		var row=row_col[0];
		var col=row_col[1];
		console.log('red: '+row+', kolona: '+col);
	})
	$('#hotels-tab').click();
}
var firstSeatLabel = 1;
		
		function selektovanLet(btn) {
				var let_id=$(btn).find('input[type=hidden]').attr('id');
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
						$('#seat-map').prepend(data.pocetnaDestinacija+'-'+data.krajnjaDestinacija);
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