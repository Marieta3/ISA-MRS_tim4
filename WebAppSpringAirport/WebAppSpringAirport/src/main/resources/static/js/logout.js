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
	localStorage.removeItem("profil_rent");
	localStorage.removeItem("profil_avio");
	localStorage.removeItem("profil_hotel");
    document.location.replace("/");
})