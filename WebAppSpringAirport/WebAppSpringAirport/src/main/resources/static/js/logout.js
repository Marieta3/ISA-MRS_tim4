/**
 * 
 */
$(document).on('click', '.logout', function(e){
	e.preventDefault();
	localStorage.removeItem('accessToken');
    document.location.replace("/");
})