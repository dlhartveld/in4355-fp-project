// expect the following format
// always at least one line
//
// aap 2
// aap 4
// aap 3

fetch(function(data) {
	word = getWord(data.value[0]);
	count = 0;
	for (var i = 0; i < data.value.length; i++) {
		count = count + getCount(data.value[i]);
	}
	push(new Result(data.id, word, count));
});

function getWord(line)
{
	var splits = line.split(" ");
	return splits[0];
}

function getCount(oldIndex, line) {
	var splits = line.split(" ");
	return parseInt(splits[1]);
}

function Result(id, word, count) {
	this.id = id;
	this.word = word;
	this.count = count;
}