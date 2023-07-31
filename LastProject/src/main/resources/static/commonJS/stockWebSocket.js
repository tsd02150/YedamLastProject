const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:80/socketserver' // 서버연결
});
console.log(stompClient);
let loginMemberName = $('#sessionMembNo').text();
let destination = '/stock/alarm/' + loginMemberName;
console.log(loginMemberName);
console.log(destination);

stompClient.onConnect = (frame) => {
    
    console.log('Connected: ' + frame);
    
    //알람 - empController 에서 보낸 알람
    stompClient.subscribe(destination, (greeting) => {
    	console.log('알림성공');
    	
        toastShow("체결 알림" ,greeting.body , "info"); // 구독된 url 에서 넘어오는 메세지 처리
       
    });
};

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



connect();

// 연결 상태를 확인하는 함수 정의
function checkStompClientStatus() {
  const isConnected = stompClient.connected;
  console.log(`STOMP 클라이언트 연결 상태: ${isConnected ? '연결됨' : '연결되지 않음'}`);
}

setInterval(checkStompClientStatus,2000);
