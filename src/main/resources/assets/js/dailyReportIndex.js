$(function(){
	$(".acordioRoute").click( function() {
			$(this).next("tr").toggle();
	});

	$(document).ready( function () {
	    $('#dailyReportTable').DataTable({
	    	"lengthMenu": [ 5, 10, 15, 20],
	    	"searching": true,
	    	"order": [ 0, "desc" ]
	    });
	});
});