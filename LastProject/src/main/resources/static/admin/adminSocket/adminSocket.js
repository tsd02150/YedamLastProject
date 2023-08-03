const stompClient = new StompJs.Client({
    brokerURL: 'ws://43.202.20.221:83/socketserver' // 서버연결
});

$(document).ready(function(){
	connect();

	
	stompClient.onConnect = (frame) => {
	    
	    console.log('Connected: ' + frame);
	    
	    //알람 - empController 에서 보낸 알람
	    stompClient.subscribe("/admin/alarm", (greeting) => {
	    	console.log('신고알람성공');
	    	 $.ajax('/admin/nonChkRptCnt').done(function(data){
				if($('#reportCnt').hasClass('d-none')) $('#reportCnt').removeClass('d-none');
		    	$('#reportCnt').text(data);
		    	$('.dropdown-header').text(`처리되지 않은 신고가 ${data} 건 있습니다.`);
       	 	});
	        toastShow("NEW" ,greeting.body , "info"); // 구독된 url 에서 넘어오는 메세지 처리
	        
	    });
	
	};
});
stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

   


