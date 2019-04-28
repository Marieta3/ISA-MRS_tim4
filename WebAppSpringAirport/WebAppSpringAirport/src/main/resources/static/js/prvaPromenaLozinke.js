/**
 * 
 */
$(document).ready(function(e){
	//ukoliko je prvi put logovan admin, sprecava se da putem nav bara ode na drugu stranicu
	//nije podrzano za menjanje url-a
	
	if(localStorage.getItem("prvaPromena")=="prvaPromena"){
		$("#chgPswLink").on("click", function(e){
			e.preventDefault();
		})
		//$("#chgPswBtn").attr("disable", true);
		$("#chgPswBtn").on("click", function(e){
			e.preventDefault();
		})
	}
})


$(document).on("submit", "#changePsw1Forma", function(e){
	e.preventDefault();
	var stara=$("#oldPsw").val();
	var nova=$("#newPsw").val();
	var confirm=$("#confirmPsw").val();
	//provera da li je dobra stara sifra
	//provera da li je nova != stara
	if(stara==nova){
		alert("New password should be different than old password.");
		return;
	}else if(nova!=confirm){
		console.log(nova);
		console.log(confirm);
		alert("Passwords do not match!");
		return;
	}/*else if(nova.length<8){
		alert("Password should have minimum 8 characters!");
		return;
	}*/
	//ajax poziv
	$.ajax({
		type:'PUT',
		url:'api/updatePassword',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		contentType:'application/json',
		dataType:"text",
		data: changePswToJSON(stara, nova, confirm),
		success:function(data){
			//obavestenje o uspesnoj promeni lozinke
			if(data=="OK"){
				var uloga=localStorage.getItem("uloga");
				if(localStorage.getItem("prvaPromena")=="prvaPromena"){
					localStorage.removeItem("prvaPromena");
				}
				alert("Successfully changed password!");
				
				window.location.replace("profil"+uloga+".html");
			}else{
				alert(data);
			}
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