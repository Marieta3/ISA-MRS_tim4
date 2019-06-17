/**
 * 
 */
/**
 * 
 */
$(document).on(
		'submit',
		"#pretragaHotela",
		function(e) {
			e.preventDefault();
			console.log("pretraga hotela");
			var tipPretrage = $("input[name='radio1']:checked").val();
			var lokacijaNaziv = $("#locName").val();
			console.log("========================================");
			console.log("Vrednost radio BUTTONA JE: " + tipPretrage);
			console.log("========================================");
			if (tipPretrage == "1") { // onda je one way
				tipPretrage = "location";
			} else { // round trip
				tipPretrage = "name";
			}
			console.log(lokacijaNaziv);
			var vremePolaska = $("#datepicker2").val();
			console.log(vremePolaska);
			var vremeDolaska = $("#datepicker3").val();
			console.log(vremeDolaska);
			$.ajax({
				type : 'POST',
				url : "api/hotels/pretraga",
				contentType: 'application/json',
				dataType : 'json',
				data : hotelsToJSONsearch(lokacijaNaziv,vremePolaska,vremeDolaska,tipPretrage),
				beforeSend : function(request) {
					request.setRequestHeader("Authorization", "Bearer "
							+ localStorage.getItem("accessToken"));
				},
				success : renderHoteli
			});
		});
function hotelsToJSONsearch(lokacijaNaziv,vremePolaska,vremeDolaska,tipPretrage) {
	return JSON.stringify({
		"lokNaziv" : lokacijaNaziv,
		"datumPolaska" : vremePolaska,
		"datumDolaska" : vremeDolaska,
		"tipPretrage" : tipPretrage
	});
}


