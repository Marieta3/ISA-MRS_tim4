/**
 * 
 */

var update_korisnika_url="api/users";

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}
$(document).ready(function(){
	dobaviPodatkeKorisnika();
})
$(document).on('click', '#cancel_profile', function(e){
	e.preventDefault();
	dobaviPodatkeKorisnika();
})
$(document).on('submit', "#editProfileForma", function(e){
	e.preventDefault();
	console.log("update korisnika");
	var id = $('#identifikator').val();
	var ime = $('#ime').val();
	var prezime = $('#prezime').val();
	var korisnickoIme=$('#username').val();
	var mail=$('#mail').val();
	var adresa = $('#address').val();
	var telefon = $('#telefon').val();
	var lozinka = $('#lozinka').val();
	console.log(lozinka);
	var slika = $('#slikaEdit').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	console.log("["+slika+"]");
	if(slika=="" || slika==null){
		console.log("slika je null");
		slika=$("#profile_img").attr("src");
		console.log("["+slika+"]");
	}
	
	console.log("aaaaaaaaa");
	console.log(id);
	console.log(lozinka);
	console.log("aaaaaaaaa");
	
	if  (!validateEmail(mail)){
		$.bootstrapGrowl("Bad email format!", {
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
	}
	
	$.ajax({
		type:'PUT',
		url:update_korisnika_url+"/"+id,
		contentType:'application/json',
		dataType:'text',
		data:korisnikToJSON(id,ime, prezime, lozinka, korisnickoIme, mail, slika, adresa, telefon),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log("Successfully saved changes")
			dobaviPodatkeKorisnika();
			$.bootstrapGrowl("Successfully saved changes!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
			//window.location.replace("prikazKorisnika.html");
		}
			, error: function(XMLHttpRequest,textStatus, errorThrown) {
				$.bootstrapGrowl("An error occured!", {
					  ele: 'body', // which element to append to
					  type: 'danger', // (null, 'info', 'danger', 'success')
					  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
					  align: 'right', // ('left', 'right', or 'center')
					  width: 'auto', // (integer, or 'auto')
					  delay: 2000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
					  allow_dismiss: false, // If true then will display a cross to close the popup.
					  stackup_spacing: 10 // spacing between consecutively stacked growls.
					});
			}
	});
});

function korisnikToJSON(id, ime, prezime, lozinka, korisnickoIme, mail, slika, adresa, telefon){
	return JSON.stringify({
		"id":id,
		"ime":ime,
		"prezime":prezime,
		"lozinka":lozinka,
		"korisnickoIme":korisnickoIme,
		"mail":mail ,
		"slika":slika,
		"adresa":adresa,
		"telefon":telefon
	});
}


function dobaviPodatkeKorisnika(){
	//ovde treba da se dobave podaci o trenutno ulogovanom korisniku
	$.ajax({
		type:'GET',
		url:'api/whoami',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	console.log("get role: "+data.authorities[0].authority);
        	$('#imePrezime').text(data.ime + " " + data.prezime);
        	$('#ime').val(data.ime);
        	$('#prezime').val(data.prezime);
        	$('#username').val(data.korisnickoIme);
        	console.log(data.lozinka);
        	$('#lozinka').val(data.lozinka);
        	$('#mail').val(data.mail);
        	if(data.adresa == null){
            	$('#address').val("neka adresa");
        	}else{
        		$('#address').val(data.adresa);
        	}
        	if(data.telefon == null){
            	$('#telefon').val("neki telefon");
        	}else{
        		$('#telefon').val(data.telefon);
        	}
        	
        	$('#identifikator').val(data.id);
        	
        	if(data.slika!=null && data.slika!=""){
        		$("#profile_img").attr("src", data.slika);
        	}else{
        		$("#profile_img").attr("src", '../slike/avatar.png');
        	}
        	//uloga= data.authorities[0].authority;
        	//window.location.replace("profil"+uloga+".html");
        }
	})
	
	
}

