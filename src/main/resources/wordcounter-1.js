fetch(function(data) {
	var mapping = new Index();
	for (var i = 0; i < data.value.length; i++) {
		mapping = processLine(mapping, data.value[i]);
	}
	push(new Result(data.id, mapping.words));
});

function processLine(oldIndex, line) {
	return combine(oldIndex, getWords(line));
}

function getWords(line) {
	var index = new Index();
	var splits = line.split(" ");
	for (var i = 0; i < splits.length; i++) {
		var split = splits[i];
		index = add(index, new Count(split, 1));
	}
	return index;
}

function Count(word, count) {
	this.word = word;
	this.count = count;
}

function Index() {
	this.words = [];
}

function combine(self, index) {
	for (var i = 0; i < index.words.length; i++) {
		self = add(self, index.words[i]);
	}
	return self;
}

function add(index, count) {
	var found = false;
	for (var i = 0; i < index.words.length; i++) {
		if (index.words[i].word == count.word) {
			index.words[i].count += count.count;
			found = true;
		}
	}
	if (!found) {
		index.words.push(count);
	}
	return index;
}

function Result(id, wordCounts) {
	this.id = id;
	this.wordCounts = wordCounts;
}
