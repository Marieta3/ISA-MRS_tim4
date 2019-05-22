/**
 * 
 */

$(document).ready(function(){
	//sprecava da se izbegne prva promena lozinke
	//u postmenu se moze izbeci
	if(localStorage.getItem("prvaPromena")=="prvaPromena"){
		window.location.replace("prvaPromenaLozinke.html");
	}
})