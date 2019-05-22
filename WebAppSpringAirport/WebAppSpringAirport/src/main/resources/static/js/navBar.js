/**
 * 
 */
//type sme biti null, info , danger, success
function notify(message, type){
	$.bootstrapGrowl(message, {
		  ele: 'body', // which element to append to
		  type: type, // (null, 'info', 'danger', 'success')
		  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
		  align: 'right', // ('left', 'right', or 'center')
		  width: 'auto', // (integer, or 'auto')
		  delay: 3000, // Time while the message will be displayed. It's not equivalent to the demo timeOut!
		  allow_dismiss: false, // If true then will display a cross to close the popup.
		  stackup_spacing: 10 // spacing between consecutively stacked growls.
		});
}
$(document).ready(function(){
	
	$("#defaultOpen").click();
	var role=localStorage.getItem("uloga");
	console.log(role);
	if(role==null){
		console.log($("#nav-bar"));
		console.log($("#lista1"));
	}else{
		$("#nav-bar").append('<li><a href="profil'+role+'.html">Profile</a></li>');
		$("#nav-bar").append('<li><button class="logout" >Logout</button></li>');
	}
})

function openTab(evt, tab_id) {
  // Declare all variables
  var i, tabcontent, tablinks;

  // Get all elements with class="tabcontent" and hide them
  tabcontent = $(".tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }

  // Get all elements with class="tablinks" and remove the class "active"
  tablinks = $(".tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }

  // Show the current tab, and add an "active" class to the button that opened the tab
  document.getElementById(tab_id).style.display = "block";
  //$("#"+tab_id).css("display", "block");
  
  evt.target.className += " active";
}

$(document).on('click', '.logout', function(e){
	e.preventDefault();
	localStorage.removeItem('accessToken');
	localStorage.removeItem('uloga');
	localStorage.removeItem("prvaPromena");
	localStorage.removeItem("hotel_id");
	localStorage.removeItem("rent_id");
	localStorage.removeItem("avio_id");
	localStorage.removeItem('profil_rent');
    document.location.replace("/");
})

$(document).on('click', '.whoami', function(e){
	e.preventDefault();
	console.log("uloga: "+localStorage.getItem("uloga"));
})

$(document).on('click', '.registerBtn', function(e){
	e.preventDefault();
	console.log("register");
	$("#id02").css("display", "block");
	$("body").addClass("modal-open");
})

$(document).on('click', '.loginBtn', function(e){
	e.preventDefault();
	console.log("login");
	$("#id01").css("display", "block");
	$("body").addClass("modal-open");
})



$(document).on('click', '.profileBtn', function(e){
	e.preventDefault();
	uloga=localStorage.getItem("uloga");
	if(uloga!=null){
		window.location.replace("profil"+uloga+".html");
	}else{
		$("#id01").css("display", "block");
		$("body").addClass("modal-open");
	}
})