/**
 * 
 */
/**
 * 
 */
$(document).on(
		'submit',
		"#pretragaRentAcar",
		function(e) {
			e.preventDefault();
			console.log("pretraga rent-a-car");
			var tipPretrage = $("input[name='radio2']:checked").val();
			var lokacijaNaziv = $("#rentName").val();
			console.log("========================================");
			console.log("Vrednost radio BUTTONA JE: " + tipPretrage);
			console.log("========================================");
			if (tipPretrage == "1") { // onda je one way
				tipPretrage = "location";
			} else { // round trip
				tipPretrage = "name";
			}
			console.log(lokacijaNaziv);
			var vremePolaska = $("#datepicker4").val();
			console.log(vremePolaska);
			var vremeDolaska = $("#datepicker5").val();
			console.log(vremeDolaska);
			$.ajax({
				type : 'POST',
				url : "api/rentACars/pretraga",
				contentType: 'application/json',
				dataType : 'json',
				data : rentToJSONsearch(lokacijaNaziv,vremePolaska,vremeDolaska,tipPretrage),
				beforeSend : function(request) {
					request.setRequestHeader("Authorization", "Bearer "
							+ localStorage.getItem("accessToken"));
				},
				success : renderRentACars
			});
		});
function rentToJSONsearch(lokacijaNaziv,vremePolaska,vremeDolaska,tipPretrage) {
	return JSON.stringify({
		"lokNaziv" : lokacijaNaziv,
		"vremePolaska" : vremePolaska,
		"vremeDolaska" : vremeDolaska,
		"tipPretrage" : tipPretrage
	});
}
