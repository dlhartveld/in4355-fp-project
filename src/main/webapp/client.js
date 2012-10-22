$(document).ready(function() {

	var running = false;
	var finished = true;
	
	var startBtn = $("#start");
	var stopBtn = $("#stop");
	
	startBtn.click(function() {
		if (!running) {
			running = true;
			finished = true; 
			startBtn.attr("disabled", "disabled");
			stopBtn.removeAttr("disabled");
		}
	});
	
	stopBtn.click(function() {
		stop();
	});
	
	setInterval(function() { run(); }, 1);
	
	function run() {
		if (running && finished) {
			finished = false;
			start();
		}
	}
	
	function fetch(callback) {
		$.ajax({
			url: '/resources/jobs/input', 
			type: 'POST', 
			dataType: 'json', 
			success: function(data) { 
				$("#input").text(data);
				callback.call(this, data);
			},
			error: function(x) {
				if (x.status == 200) {
					callback.call(this, JSON.parse(x.responseText));
					return;
				}
				stop();
			}
		});
	}
	
	function push(results) {
		var serializedData = JSON.stringify(results);
		$("#output").text(serializedData);
		$.ajax({
			url: '/resources/jobs/output', 
			type: 'POST', 
			contentType: 'application/json',
			data: serializedData,
			success: function(data) { 
				finished = true;
			},
			error: function(x) {
				if (x.status == 200) {
					callback.call(this, JSON.parse(x.responseText));
					return;
				}
				stop();
			}
		});
	}
	
	function start() {
		$.ajax({
			url: '/resources/jobs/code', 
			type: 'POST', 
			dataType: 'text', 
			success: function(data) { 
				eval(data);
			},
			error: function(x) {
				alert(x.responseText);
				stop();
			}
		});
	}
	
	function stop() {
		if (running) {
			running = false;
			stopBtn.attr("disabled", "disabled");
			startBtn.removeAttr("disabled");
		}
	}
	
});
