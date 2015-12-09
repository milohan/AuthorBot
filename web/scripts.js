var transitioned = false;


function changeText(callBackFunction) {
    $('#authorBot').html('<h1> Author Bot is imitating ' + rAuthor + '</h1>');

    if (!transitioned) {
    $('#shift').animate({"margin-left": '-=400'}, 750);

    $('#authorBot').animate({"opacity": '+=1'}, 750);
    $('#more').animate({"opacity": '+=1'}, 750);
    $('#more').html('<div class="text-center"><button type="button" onclick="readBookByAuthor()" style="margin-left: 50px; margin-right: 25px" class="btn btn-default btn-lg"> I Like! </button><button type="button" class="btn btn-default btn-lg" onclick="startNewAuthor()"> New Author </button></div>');
    transitioned = true;
    }
   callBackFunction('url');
}


//function changeText() {
//        $('#shift').animate({"margin-left": '-=200'}, 750);
//        $('#authorBot').html('<h1> Author Bot is impersonating [insert author] </h1>');
//        $('#authorBot').animate({"opacity": '+=1'}, 750);
//        $('#more').animate({"opacity": '+=1'}, 750);
//        $('#more').html('<div class="text-center"><button type="button" onclick="readBookByAuthor()" style="margin-left: 50px; margin-right: 25px" class="btn btn-default btn-lg"> Read more! </button><button type="button" class="btn btn-default btn-lg" onclick="newAuthor"> Something new, please! </button></div>');
//    }

function changeSpeech(speechLines) {
    console.log('changing speech');
    $('#speech').html('<p>' + speechLines[0] + '<br>'
            + speechLines[1] + '<br>' + speechLines[2] + '<br>'
            + speechLines[3] + '<br>' + speechLines[4] + '<br>'
            + speechLines[5] + '<br>' + speechLines[6] + '<br>'
            + speechLines[7] + '<br>' + speechLines[8] + '</p>');
}

(function () { // call open socket directly
    openSocket();
})(); 



var webSocket;
var rText;
var rAuthor;
var rURL;
var sendType = 0;

function openSocket() {
    // Ensures only one connection is open at a time
    if (webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED) {
        console.log("WebSocket is already opened.");
        return;
    }
    // Create a new instance of the websocket
    webSocket = new WebSocket("ws://localhost:8080/EchoChamber/echo");

    /**
     * Binds functions to the listeners for the websocket.
     */
    webSocket.onopen = function (event) {
        // For reasons I can't determine, onopen gets called twice
        // and the first time event.data is undefined.
        // Leave a comment if you know the answer.
        if (event.data === undefined)
            return;

        console.log(event.data);
    };

    webSocket.onmessage = function (event) {
        //console.log(event.data);
        if (sendType == 0) {
            rAuthor = event.data; // set the text 
            console.log("author: " + rAuthor);
            changeText(sends);
        } else if (sendType == 2) {
            rText = event.data;
            console.log("text: " + rText);
            processText();
        } else if (sendType == 1) {
            rURL = event.data;
            console.log("url"+rURL);
            sends('text');
        }
        sendType++;
        sendType %= 3;
    };

    webSocket.onclose = function (event) {
        console.log("Connection closed");
    };
}

/**
 * Sends the value of the text input to the server
 */
function sends(type) {
    //var text = document.getElementById("messageinput").value;
    webSocket.send(type);
}

function closeSocket() {
    webSocket.close();
}

function writeResponse(text) {
    messages.innerHTML += "<br/>" + text;
}





var text;
var canvas, context, toggle;
var testString;
var sentences = [];
var curSentence = 0;
var MAX_LINE_LENGTH = 35;
var startedSpeaking = false;


    var NUM_LINES = 5;
    var chunks = [];
    var words = [];
    var curChunk = 0;

//gets text from sean's function
function setText() {
    text = 'test';
}




	setInterval(function(){ 

		//console.log('in checkDoneWithLine');

		//if new author has started
		if (startedSpeaking){
			console.log('in startedSpeaking');

			//if previous sentence has ended, start next one
			if (responsiveVoice.isPlaying() == false){
                console.log('responsive voice no longer playing');

				//if we're not yet done with all the sentences, keep going
				if (curChunk < chunks.length){
					console.log('playing next sentence');
                    play(chunks[curChunk]);
					changeSpeech(getNextSentence());
				}
			}
		}
	},100);




function processText() {
    //testString = 'This is sentence one its a really long sentence one two three blah blah test one two three four five six seven.  This is sentence two!  Now sentence three.  Blah blah sentence four! Five.'
//		splitBigString(rText);
//		console.log(rText);
//		play(sentences[curSentence]);
//		getNextSentence();
        
        console.log('processText is being called');
        splitBigString(rText);
	//console.log(rText);
	play(chunks[curChunk]);
        changeSpeech(getNextSentence());
}
function startNewAuthor() {
    responsiveVoice.cancel();
    startedSpeaking = false;
    //chunks = [];
    sends('author');
//		sends('text');
//		sends('url');

}

//to be called by bootstrap button.  starts playing.  calls setText itself?
function play(string) {
    setText();
    //var a = new Audio(url);
    //a.play();
    responsiveVoice.speak(string, "UK English Male", {rate: 0.9});
    startedSpeaking = true;
    /*	a.addEventListener("ended", function() 
     {
     a.currentTime = 0;
     });
     //readBookByAuthor();
     */
}

//adds sentences to the 'sentences' array
    function splitBigString(bigString){
    	//sentences = bigString.split(/\!|\?|\./g);
        chunks = [];
    	words = bigString.split(' ');
        var curLine = 0;
        chunks[curLine] = '';
        for (i = 0; i < words.length; i++){
            var curString = chunks[curLine];
            var newString = chunks[curLine].concat(words[i]);
            if (newString.length > MAX_LINE_LENGTH * NUM_LINES - 16){
                curLine++;
                chunks[curLine] = '';
            }
            chunks[curLine] = chunks[curLine].concat(words[i]).concat(' ');
        }
        
        curChunk = 0;
        chunks[0] = chunks[0].charAt(0).toUpperCase() + chunks[0].slice(1);

        //console.log(chunks);
        //var linesArray = getNextSentence();
        //console.log(linesArray);
    }

    //pre: splitBigString has already been called
    //returns the next chunk as an array of strings. each string is of max length MAX_LINE_LENGTH.
    function getNextSentence(){


    	var lines = [];

        for (j = 0; j < 9; j++){
            lines[j] = '';
        }

    	var curLine = 0;
    	lines[curLine] = '';
    	var chunkWords = chunks[curChunk].split(" ");
    	for (i = 0; i < chunkWords.length; i++) {
    		var curStr = lines[curLine];
    		var newStr = curStr.concat(chunkWords[i]).concat(' ');
    		if (newStr.length > MAX_LINE_LENGTH){
    			curLine++;
    			lines[curLine] = '';
    		}
    		lines[curLine] = lines[curLine].concat(chunkWords[i]).concat(' ');
    	}
    	curChunk = curChunk+1;

    	return lines;
    }


/////

function printString() {
    var toPrint = getNextSentence();
    console.log(toPrint);
    console.log(toPrint.length);
    for (i = 0; i < toPrint.length; i++) {
        console.log(toPrint[i]);
    }
}

function animateBot() {
    var frames = document.getElementById("bot").children;
    var frameCount = frames.length;
    var i = 0;
    setInterval(function () {
        frames[i % frameCount].style.display = "none";
        frames[++i % frameCount].style.display = "block";
    }, 100);
}


//function that sends user to specified link
function readBookByAuthor() {
    window.open(rURL);
}


