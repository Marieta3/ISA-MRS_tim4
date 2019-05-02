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
		alert("Bad email format");
		return;
	}
	
	$.ajax({
		type:'PUT',
		url:update_korisnika_url+"/"+id,
		contentType:'application/json',
		dataType:'text',
		data:korisnikToJSON(id,ime, prezime, lozinka, korisnickoIme, mail, slika),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			console.log("EVO MEEEEEE")
			dobaviPodatkeKorisnika();
			alert("Successfully saved changes!");
			//window.location.replace("prikazKorisnika.html");
		}
			, error: function(XMLHttpRequest,textStatus, errorThrown) 
				{alert("No such user!!"+errorThrown);
			}
	});
});

function korisnikToJSON(id, ime, prezime, lozinka, korisnickoIme, mail, slika){
	return JSON.stringify({
		"id":id,
		"ime":ime,
		"prezime":prezime,
		"lozinka":lozinka,
		"korisnickoIme":korisnickoIme,
		"mail":mail ,
		"slika":slika
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
        	$('#lozinka').val(data.lozinka);
        	$('#mail').val(data.mail);
        	$('#address').val("neka adresa");
        	$('#telefon').val("neki telefon");
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
function previewFile(src, dest){
	var putanja=$("#"+src).val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	$("#"+dest).attr("src", putanja);
}
/*function previewFile(){
      var preview = document.querySelector('img'); //selects the query named img
      var file    = document.querySelector('input[type=file]').files[0]; //sames as here 
      var reader  = new FileReader();
      

      reader.onloadend = function () {
           preview.src = reader.result;
      }

      if (file) {
           reader.readAsDataURL(file); //reads the data as a URL
      } else {
           preview.src = "";
      }
      }         */ 
     
      
       //previewFile();  //calls the function named previewFile()
