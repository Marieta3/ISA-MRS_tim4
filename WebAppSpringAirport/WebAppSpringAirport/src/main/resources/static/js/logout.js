/**
 * 
 */
$(document).on('click', '.logout', function(e){
	e.preventDefault();
	localStorage.removeItem('accessToken');
	localStorage.removeItem("uloga");
	localStorage.removeItem("prvaPromena");
    document.location.replace("/");
})