//------------------- OLD ------------------------------------
function getScore() {
	
	var score = 0;
	var quest = $("#quiz ul");
	var chois = $("#quiz ul li input"); 		
	var answer = $("#quiz ul li"); 	

	for (i=0; i<chois.length; i++) {
		
		answer.eq(i).css({'color':''}); 
		if (chois[i].checked == true && chois[i].value == "1") {
			score++;
		}

		if (chois[i].checked == true && chois[i].value == "0") {
			answer.eq(i).css({'color':'#ff0000'}); 
		}
		
		
	}			
	
	score = Math.round(score/quest.length*100);
	alert("▬▬▬▬▬▬▬▬▬ஜ۩۞۩ஜ▬▬▬▬▬▬▬▬▬\n\n" 
			+ "\t\t\t\t SCORE: " + score + "%"
			+ "\n\n▬▬▬▬▬▬▬▬▬ஜ۩۞۩ஜ▬▬▬▬▬▬▬▬▬\n");
	
}	

function getAnswers() {
	
	var correctAnswers = "";

	var quest = $("#quiz ul");			
	var chois = $("#quiz ul li input"); 	
	var answer = $("#quiz ul li"); 	
	
	
	for (i=0; i<quest.length; i++) {
		var idx = i*chois.length/quest.length;

		for (j=idx; j<idx+chois.length/quest.length; j++) {
			if (chois[j].value == "1") {
				correctAnswers += "\tQestion " + (i+1) + ": " + answer.eq(j).text() + "\r\n";
				answer.eq(j).css({'color':'#0066ff'}); 
			}				
			
		}
	}			
	
	//alert("ANSWERS: \n"
	//		+ "▬▬▬▬▬▬▬▬▬ஜ۩۞۩ஜ▬▬▬▬▬▬▬▬▬\n\n" 
	//		+ correctAnswers + "\n");
	
}	

function resetAnswers() {

	var chois = $("#quiz ul li input"); 	
	var answer = $("#quiz ul li"); 	

	for (i=0; i<chois.length; i++) {
		
		answer.eq(i).css({'color':''}); 				
		
	}			

}

//------------------- TICK QUIZ -------------------------------
function tickGetScore() {
	
	var score = 0;
	var quest = $("#quiz ul");
	var chois = $("#quiz ul li input"); 		
	var answer = $("#quiz ul li"); 	

	for (i=0; i<chois.length; i++) {
		
		answer.eq(i).css({'color':''}); 
		if (chois[i].checked == true && chois[i].value == "1") {
			score++;
		}

		if (chois[i].checked == true && chois[i].value == "0") {
			answer.eq(i).css({'color':'#ff0000'}); 
		}
		
		
	}			
	
	score = Math.round(score/quest.length*100);
	alert("▬▬▬▬▬▬▬▬▬ஜ۩۞۩ஜ▬▬▬▬▬▬▬▬▬\n\n" 
			+ "\t\t\t\t SCORE: " + score + "%"
			+ "\n\n▬▬▬▬▬▬▬▬▬ஜ۩۞۩ஜ▬▬▬▬▬▬▬▬▬\n");
	
}	

function tickGetAnswers() {
	
	var correctAnswers = "";

	var quest = $("#quiz ul");			
	var chois = $("#quiz ul li input"); 	
	var answer = $("#quiz ul li"); 	
	
	
	for (i=0; i<quest.length; i++) {
		var idx = i*chois.length/quest.length;

		for (j=idx; j<idx+chois.length/quest.length; j++) {
			if (chois[j].value == "1") {
				correctAnswers += "\tQestion " + (i+1) + ": " + answer.eq(j).text() + "\r\n";
				answer.eq(j).css({'color':'#0066ff'}); 
			}				
			
		}
	}			
	
	//alert("ANSWERS: \n"
	//		+ "▬▬▬▬▬▬▬▬▬ஜ۩۞۩ஜ▬▬▬▬▬▬▬▬▬\n\n" 
	//		+ correctAnswers + "\n");
	
}	

function tickResetAnswers() {

	var chois = $("#quiz ul li input"); 	
	var answer = $("#quiz ul li"); 	

	for (i=0; i<chois.length; i++) {
		
		answer.eq(i).css({'color':''}); 				
		
	}			

}

//------------------- INPUT QUIZ -------------------------------

function inputGetScore() {
	
	var score = 0;
	var answer = $("#quiz input[type=text]"); 		

	for (i=0; i<answer.length; i++) {
		
		answer.eq(i).css({'color':'','background-color':''}); 
		if (answer[i].value == answer[i].name) {
			score++;
		} else {
			//answer.eq(i).css({'color':'#fff !important','background-color':'#f00'}); 
			answer.eq(i).attr('style', 'color: #fff !important;background-color:#f00');
		}
	}			
	
	score = Math.round(score/answer.length*100);
	alert("▬▬▬▬▬▬▬▬▬ஜ۩۞۩ஜ▬▬▬▬▬▬▬▬▬\n\n" 
			+ "\t\t\t\t SCORE: " + score + "%"
			+ "\n\n▬▬▬▬▬▬▬▬▬ஜ۩۞۩ஜ▬▬▬▬▬▬▬▬▬\n");
	
}	

function inputGetAnswers() {
	
	var answer = $("#quiz input[type=text]"); 		

	for (i=0; i<answer.length; i++) {
		if (answer[i].value == answer[i].name) {
			//answer.eq(i).css({'color':'#fff !important','background-color':'#0080FF'}); 
			answer.eq(i).attr('style', 'color: #fff !important;background-color:#0080FF');
		}
		else {
			if (answer[i].value.indexOf("(") == -1) {
				//answer.eq(i).css({'color':'#fff !important','background-color':'#f00'}); 
				answer.eq(i).attr('style', 'color: #fff !important;background-color:#f00');
				answer[i].value = answer[i].value + ' (' + answer[i].name + ') ';
			}
		}

	}
	

}	

function inputResetAnswers() {

	var answer = $("#quiz input[type=text]"); 	

	for (i=0; i<answer.length; i++) {
		
		answer.eq(i).css({'color':'','background-color':''});  				
		
	}			

}

//-----------------------------------------------------------------------

$(function() {
$('#quiz li').click(function() {
      $(this).find('input:radio').prop('checked', true);
});
}); 


