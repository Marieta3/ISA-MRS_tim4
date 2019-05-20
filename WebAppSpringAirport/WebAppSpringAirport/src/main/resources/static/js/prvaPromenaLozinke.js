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
		$.bootstrapGrowl("New password should be different than old password!", {
			  ele: 'body', // which element to append to
			  type: 'danger', // (null, 'info', 'danger', 'success')
			  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
			  align: 'right', // ('left', 'right', or 'center')
			  width: 'auto', // (integer, or 'auto')
			  delay: 2000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
			  allow_dismiss: false, // If true then will display a cross to close the popup.
			  stackup_spacing: 10 // spacing between consecutively stacked growls.
			});
		return;
	}else if(nova!=confirm){
		console.log(nova);
		console.log(confirm);
		$.bootstrapGrowl("Passwords do not match!", {
			  ele: 'body', // which element to append to
			  type: 'danger', // (null, 'info', 'danger', 'success')
			  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
			  align: 'right', // ('left', 'right', or 'center')
			  width: 'auto', // (integer, or 'auto')
			  delay: 2000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
			  allow_dismiss: false, // If true then will display a cross to close the popup.
			  stackup_spacing: 10 // spacing between consecutively stacked growls.
			});
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

				$.bootstrapGrowl("Successfully changed password!", {
					  ele: 'body', // which element to append to
					  type: 'success', // (null, 'info', 'danger', 'success')
					  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
					  align: 'right', // ('left', 'right', or 'center')
					  width: 'auto', // (integer, or 'auto')
					  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
					  allow_dismiss: false, // If true then will display a cross to close the popup.
					  stackup_spacing: 10 // spacing between consecutively stacked growls.
					});
				
				window.location.replace("profil"+uloga+".html");
			}else{
				console.log(data);
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