const chatClient = new StompJs.Client({
    brokerURL: 'ws://localhost:80/chatserver'
});

chatClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    chatClient.subscribe('/topic/sendto/'+$('#roomno').data("roomno"), (chatMessage) => {
    	console.log(JSON.parse(chatMessage.body));
        showChat(JSON.parse(chatMessage.body));
    });
};

chatClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

chatClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#chatSpace").html("");
}

function connect() {
    chatClient.activate();
}

function disconnect() {
    chatClient.deactivate();
    console.log("Disconnected");
}

function sendChat() {
	fetch("addChat",{
		method: "POST",
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded'
		},
		body: 'cntn='+$("#my-message").val()+'&roomNo='+$('#roomno').data("roomno")+'&anonNick='+myAnonNick+'&membNo='+myMembNo
	})
	.then(response=>response.json())
	.then(result=>{
		$("#my-message").val('');
	    chatClient.publish({
	        destination: "/mychat/room/"+$('#roomno').data("roomno"),
	        body: JSON.stringify({
	        	'anonnick':result.anonNick,
	        	'drwupdt':result.drwupDt,
	        	'message': result.cntn        	
	        })
	    });		
	})			
}

function showChat(chat) {
	let chatContent=`
		<div class="direct-chat-msg stock-chat">
			<div class="direct-chat-info clearfix">
				<span class="direct-chat-name pull-left">`+chat.anonnick+`&nbsp;&nbsp;</span> 
				<span class="direct-chat-timestamp">`+chat.drwupdt+`</span>
			</div>
			<!-- /.direct-chat-info -->
			<div class="direct-chat-text">
				`+chat.message+`
				
			</div>
			<!-- /.direct-chat-text -->
		</div>
	`;
    $("#chatSpace").append(chatContent);
}

$(function () {
    connect();
    $( "#send" ).click(() => sendChat());
});