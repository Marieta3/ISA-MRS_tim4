/**
 * 
 */
function previewFile(src, dest){
	var putanja=$("#"+src).val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	$("#"+dest).attr("src", putanja);
}