let chatClient = stompClient;
console.log(stompClient);
chatClient.onConnect = (frame) => {
    resetChat();
    console.log('Connected: ' + frame);
    chatClient.subscribe('/topic/sendto/'+$('#roomno').data("roomno"), (chatMessage) => {
    	console.log(JSON.parse(chatMessage.body));
        showChat(JSON.parse(chatMessage.body));
    });
};

function onError(error){
    console.error('Error with websocket', error);
};

function showChat(chat) {
	let chatContent;
	console.log(chat,myMembNo);
	if(chat.anonnick==myAnonNick){
		chatContent=`
			<div class="direct-chat-msg stock-chat right">
				<div class="direct-chat-info clearfix">
					<span class="direct-chat-timestamp  pull-right">${chat.drwupdt}</span>
					<span class="direct-chat-name  pull-right">${chat.anonnick}&nbsp;&nbsp;</span> 
				</div>
				<!-- /.direct-chat-info -->
				<div class="direct-chat-text">
					${chat.message}	
				</div>
				<!-- /.direct-chat-text -->
			</div>
		`;	
	}else{
		chatContent=`
			<div class="direct-chat-msg stock-chat">
				<div class="direct-chat-info clearfix">
					<span class="direct-chat-name pull-left">${chat.anonnick}&nbsp;&nbsp;</span> 
					<span class="direct-chat-timestamp">${chat.drwupdt}</span>
				</div>
				<!-- /.direct-chat-info -->
				<div class="direct-chat-text">
					${chat.message}	
				</div>
				<!-- /.direct-chat-text -->
			</div>
		`;	
	}
    $("#chatSpace").append(chatContent);
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
	    chatClient.publish({
	    	destination : "/mychat/room/"+$('#roomno').data("roomno"),
			body : JSON.stringify({'anonnick':result.anonNick,'drwupdt':result.drwupDt,'message': result.cntn})
			});		
		$("#my-message").val('');
	})			
}




function disconnect() {
    chatClient.deactivate();
    console.log("Disconnected");
}





$(function () {

    $( "#send" ).click(() => sendChat());
});