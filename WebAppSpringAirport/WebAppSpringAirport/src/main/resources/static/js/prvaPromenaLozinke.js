/**
 * 
 */


$(document).on("submit", "#changePsw1Forma", function(e){
	e.preventDefault();
	var stara=$("#oldPsw").val();
	var nova=$("#newPsw").val();
	var confirm=$("#confirmPsw").val();
	//provera da li je dobra stara sifra
	//provera da li je nova != stara
	if(stara==nova){
		alert("New password should be different than a new password.");
		return;
	}else if(nova!=confirm){
		console.log(nova);
		console.log(confirm);
		alert("Passwords do not match!");
		return;
	}
	//ajax poziv
	$.ajax({
		type:'PUT',
		url:'api/updatePassword',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		contentType:'application/json',
		dataType:"json",
		data: changePswToJSON(stara, nova, confirm),
		success:function(data){
			//obavestenje o uspesnoj promeni lozinke
			var uloga=localStorage.getItem("uloga");
			window.location.replace("profil"+uloga+".html");
		}
	})
})

function changePswToJSON(stara, nova, confirm){
	return JSON.stringify({
		"oldPsw":stara,
		"newPsw":nova,
		"confirmPsw":confirm
	})
}