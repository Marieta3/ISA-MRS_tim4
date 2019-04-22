/**
 * 
 */

var update_korisnika_url="api/users";

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
	
	console.log("aaaaaaaaa");
	console.log(id);
	console.log(ime);
	console.log("aaaaaaaaa");
	$.ajax({
		type:'PUT',
		url:update_korisnika_url+"/"+id,
		contentType:'application/json',
		dataType:'text',
		data:korisnikToJSON(id,ime, prezime, lozinka, korisnickoIme, mail),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			console.log("EVO MEEEEEE")
			window.location.replace("prikazKorisnika.html");
		}
			, error: function(XMLHttpRequest,textStatus, errorThrown) 
				{alert("Ne postoji korisnik sa datom ID-om!"+errorThrown);
			}
	});
});

function korisnikToJSON(id, ime, prezime, lozinka, korisnickoIme, mail){
	return JSON.stringify({
		"id":id,
		"ime":ime,
		"prezime":prezime,
		"lozinka":lozinka,
		"korisnickoIme":korisnickoIme,
		"mail":mail 
	});
}

dobaviPodatkeKorisnika()
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
        	
        	uloga= data.authorities[0].authority;
        	//window.location.replace("profil"+uloga+".html");
        }
	})
	
	
}
function previewFile(){
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
      }           function previewFile(){
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
      }

       //previewFile();  //calls the function named previewFile()
