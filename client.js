$(document).ready(function() {

	var running = false;
	var finished = true;
	
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
	
	setInterval(function() { run(); }, 1);
	
	function run() {
		if (running && finished) {
			finished = false;
			start();
		}
	}
	
	function fetch(callback) {
		$.ajax({
			url: 'http://localhost:10000/resources/resources/jobs/input', 
			type: 'POST', 
			dataType: 'json', 
			success: function(data) { 
				$(".data").text(JSON.stringify(data));
				callback.call(this, data);
			},
			error: function(x) {
				if (x.status == 200) {
					callback.call(this, JSON.parse(x.responseText));
					return;
				}
			}
		});
	}
	
	function push(results) {
		$.ajax({
			url: 'http://localhost:10000/resources/resources/jobs/output', 
			type: 'POST', 
			data: results,
			success: function(data) { 
				finished = true;
			},
			error: function(x) {
				if (x.status == 200) {
					callback.call(this, JSON.parse(x.responseText));
					return;
				}
			}
		});
	}
	
	function start() {
		eval("fetch(function(data) { push(data); });");
	}
	
});
