/**
 * 
 */
$(document).on('click', '.logout', function(e){
	e.preventDefault();
	localStorage.removeItem('accessToken');
	localStorage.removeItem("uloga");
	localStorage.removeItem("prvaPromena");
	localStorage.removeItem("hotel_id");
	localStorage.removeItem("rent_id");
	localStorage.removeItem("avio_id");
    document.location.replace("/");
})