 const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:80/stockserver' // 서버연결
});



stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        showGreeting(JSON.parse(greeting.body).content); // 구독된 url 에서 넘어오는 메세지 처리
    });
    
    //알람 - stockServiceImpl 에서 보낸 알람
    stompClient.subscribe('/stock/alarm/'+'mem-2', (greeting) => {
        alert(greeting.body); // 구독된 url 에서 넘어오는 메세지 처리
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

function sendName() {
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({'name': $("#name").val()})
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}


connect();

// 연결 상태를 확인하는 함수 정의
function checkStompClientStatus() {
  const isConnected = stompClient.connected;
  console.log(`STOMP 클라이언트 연결 상태: ${isConnected ? '연결됨' : '연결되지 않음'}`);
}

setInterval(checkStompClientStatus,2000);
