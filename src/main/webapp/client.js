$(document).ready(function() {

	var startBtn = $("#start");
	var stopBtn = $("#stop");

	var currentTask = "";
	var taskId = -1;
	var state = "stopped";
	
	startBtn.click(function() {
		start();
	});
	
	stopBtn.click(function() {
		stop();
	});
	
	setInterval(function() { run(); }, 50);
	
	function run() {
		if (state == "poll") {
			state = "polling";
			pollNextTask(function(id) {
				if (id != "" && id >= 0) {
					taskId = id;
					pullCode(id);
				}
				else {
					state = "poll";
				}
			});
		}
		else if (state == "finished-package" || state == "ready-to-run") {
			state = "executing";
			eval(currentTask);
		}
		else if (state == "stop") {
			stop();
		}
	}
	
	function fetch(callback) {
		$.ajax({
			url: '/resources/jobs/' + taskId + '/input', 
			type: 'POST', 
			dataType: 'json', 
			success: function(data) { 
				$("#input").text("Received package: (" + taskId + ", " + data.id + ")");
				callback.call(this, data);
			},
			error: function(x) {
				if (x.status == 200) {
					callback.call(this, JSON.parse(x.responseText));
					return;
				}
				else if (x.status == 204) {
					state = "poll";
					return;
				}
				state = "stop";
			}
		});
	}
	
	function push(results) {
		var serializedData = JSON.stringify(results);
		$("#output").text(taskId + ": " + results.id + " -> " + results.wordCounts.length + " distinct words...");
		$.ajax({
			url: '/resources/jobs/' + taskId + '/output', 
			type: 'POST', 
			contentType: 'application/json',
			dataType: 'json', 
			data: serializedData,
			success: function(data) { 
				if (data.hasMoreData) {
					state = "finished-package";
				}
				else {
					state = "poll";
				}
			},
			error: function(x) {
				state = "stop";
			}
		});
	}
	
	function pollNextTask(callback) {
		$.ajax({
			url: '/resources/jobs', 
			type: 'POST', 
			dataType: 'text', 
			success: function(data) { 
				callback.call(this, data);
			},
			error: function(x) {
				$("#output").text(x.responseText);
				state = "stop";
			}
		});
	}
	
	function pullCode(taskId) {
		$.ajax({
			url: '/resources/jobs/' + taskId + '/code', 
			type: 'POST', 
			dataType: 'text', 
			success: function(data) { 
				if (data.length == 0) {
					state = "poll";
					return;
				}
				currentTask = data;
				state = "ready-to-run";
			},
			error: function(x) {
				if (x.status == 204) {
					state = "poll";
					return;
				}
				$("#output").text(x.responseText);
			}
		});
	}
	
	function start() {
		if (state == "stopped") {
			state = "poll";
			startBtn.attr("disabled", "disabled");
			stopBtn.removeAttr("disabled");
		}
	}
	
	function stop() {
		if (state != "stopped") {
			state = "stopped";
			stopBtn.attr("disabled", "disabled");
			startBtn.removeAttr("disabled");
		}
	}
	
	start();
	
});
