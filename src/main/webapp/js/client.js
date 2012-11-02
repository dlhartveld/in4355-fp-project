var currentTask = "";
var taskId = -1;
var state = "stopped";
var waitUntil = 0;

function startclient() {
	if (state == "stopped") {
		state = "poll";
		run();
	}
}

function stopclient() {
	if (state != "stopped") {
		state = "stopped";
	}
}

setInterval(function() { run(); }, 100);

function run () {
	if (waitUntil > new Date().getTime()) {
		return;
	}
	
	if (state == "poll") {
		state = "polling";
		$("#debug-text").text("Polling for next job...");
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

	if (state == "stop") {
		stop();
	}
}

function fetch(callback) {
	$.ajax({
		url: '/resources/jobs/' + taskId + '/input', 
		type: 'POST', 
		dataType: 'json', 
		success: function(data) { 
			callback.call(this, data);
		},
		error: function(x) {
			waitUntil = new Date().getTime() + 5000;
			state = "poll";
		}
	});
}

function push(results) {
	var serializedData = JSON.stringify(results);
	$("#debug-text").text("Processed data-package: " + results.id + " of task: " + taskId);
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
			waitUntil = new Date().getTime() + 5000;
			state = "poll";
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
			waitUntil = new Date().getTime() + 5000;
			state = "poll";
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
			waitUntil = new Date().getTime() + 5000;
			state = "poll";
		}
	});
}