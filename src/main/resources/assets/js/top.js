$(function(){
	$(document).ready( function () {
		$("#articlesTable").DataTable({
	        lengthChange: false,
	        searching: false,
	        order: [ [ 1, "desc" ] ],
	        info: false,
	        paging: false
	    });
	});
});