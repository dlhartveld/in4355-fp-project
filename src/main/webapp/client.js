$(document).ready(function() {

	var running = false;
	var packageProcessed = true;
	var allDataProcessed = false;
	
	var startBtn = $("#start");
	var stopBtn = $("#stop");
	
	startBtn.click(function() {
		if (!running) {
			running = true;
			packageProcessed = true;
			allDataProcessed = false; 
			startBtn.attr("disabled", "disabled");
			stopBtn.removeAttr("disabled");
		}
	});
	
	stopBtn.click(function() {
		stop();
	});
	
	setInterval(function() { run(); }, 50);
	
	function run() {
		if (allDataProcessed && running) {
			finished = true;
			stop();
			return;
		}
		
		if (running) {
			if (packageProcessed) {
				packageProcessed = false;
				start();
			}
		}
	}
	
	function fetch(callback) {
		$.ajax({
			url: '/resources/jobs/input', 
			type: 'POST', 
			dataType: 'json', 
			success: function(data) { 
				$("#input").text("Received package: " + data.id);
				callback.call(this, data);
			},
			error: function(x) {
				if (x.status == 200) {
					callback.call(this, JSON.parse(x.responseText));
					return;
				}
				else if (x.status == 204) {
					allDataProcessed = true;
					return;
				}
				stop();
			}
		});
	}
	
	function push(results) {
		var serializedData = JSON.stringify(results);
		$("#output").text(results.id + " -> " + results.wordCounts.length + " distinct words...");
		$.ajax({
			url: '/resources/jobs/output', 
			type: 'POST', 
			contentType: 'application/json',
			dataType: 'json', 
			data: serializedData,
			success: function(data) { 
				packageProcessed = true;
				allDataProcessed = !data.hasMoreData;
			},
			error: function(x) {
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
				if (data.length == 0) {
					allDataProcessed = true;
					finished = true;
					return;
				}
				eval(data);
			},
			error: function(x) {
				if (x.status == 204) {
					allDataProcessed = true;
					finished = true;
					return;
				}
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
