/**
 * 
 */
$(document).on(
		'submit',
		"#pretragaLetova",
		function(e) {
			e.preventDefault();
			console.log("pretraga leta");
			var radioValue = $("input[name='radio']:checked").val();
			console.log("========================================");
			console.log("Vrednost radio BUTTONA JE: " + radioValue);
			console.log("========================================");
			if (radioValue == "1") { // onda je one way
				radioValue = "oneway";
			} else { // round trip
				radioValue = "twoway";
			}
			var iz = $("#selected_text").val();
			console.log(iz);
			var u = $("#selected_text1").val();
			console.log(u);
			var vremePolaska = $("#datepicker").val();
			console.log(vremePolaska);
			var vremeDolaska = $("#datepicker1").val();
			console.log(vremeDolaska);
			var brojPutnika = $("#selected_text2").val();
			console.log(brojPutnika);
			$.ajax({
				type : 'POST',
				url : "api/let/pretraga",
				contentType: 'application/json',
				dataType : 'json',
				data : letToJSONsearch(radioValue, iz, u, brojPutnika),
				beforeSend : function(request) {
					request.setRequestHeader("Authorization", "Bearer "
							+ localStorage.getItem("accessToken"));
				},
				success : renderLetovi
			});
		});
function letToJSONsearch(radioValue, iz, u, brojPutnika) {
	return JSON.stringify({
		"tipPutovanja" : radioValue,
		"mestoPolaska" : iz,
		"mestoDolaska" : u,
		//"vremePolaska" : vremePolaska,
		//"vremeDolaska" : vremeDolaska,
		"brojPutnika" : brojPutnika
	});
}
