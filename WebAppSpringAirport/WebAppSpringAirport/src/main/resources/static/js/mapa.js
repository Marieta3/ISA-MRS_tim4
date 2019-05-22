/**
 * 
 */
ymaps.ready(init);

$(document).on('hover', '.ymaps-2-1-73-map', function(e){
	e.preventDefault();
	console.log(hover);
})
function init(){
	console.log('mapa init');
	/*var searchControl=new ymaps.control.SearchControl({
		
	})*/
	
	var map = new ymaps.Map('map', {
        center: [59.94, 30.32],
        zoom: 12,
        behaviors: ['drag']
    });
	
	
	var placemark=new ymaps.Placemark([59.97, 30.31], {
		hintContent:'<div class="map__hint">Hint hint hint</div>',
		balloonContent: "Ovo je balloon"
	});
	map.geoObjects.add(placemark);
}