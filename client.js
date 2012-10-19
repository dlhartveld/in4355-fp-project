$(document).ready(function() {

	var running = false;
	var startBtn = $("#start");
	var stopBtn = $("#stop");
	
	startBtn.click(function() {
		if (!running) {
			running = true;
			startBtn.attr("disabled", "disabled");
			stopBtn.removeAttr("disabled");
			start();
		}
	});
	
	stopBtn.click(function() {
		if (running) {
			running = false;
			stopBtn.attr("disabled", "disabled");
			startBtn.removeAttr("disabled");
		}
	});
	
	
	function start() {
		$.ajax({
			url: "http://localhost:8080/jobs/code",
			type: "post",
			dataType: "text",
			success: function(data) {
				eval(data);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert(jqXHR.responseText);
			}
		});
	}
	
	function startNextJob() {
		if (running) {
			start();
		}
	}
	
});
