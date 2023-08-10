const stompClient = new StompJs.Client({
    brokerURL: 'ws://43.202.20.221:83/socketserver' // 서버연결
});

$(document).ready(function(){
	connect();

	let loginMemberName = $('#sessionMembNo').text();
	let destination = '/stock/alarm/' + loginMemberName;
	console.log(loginMemberName);
	console.log(destination);
	
	stompClient.onConnect = (frame) => {
	    
	    console.log('Connected: ' + frame);
	   
	    stompClient.subscribe("/all", (greeting) => {
	     	if(greeting.body == 'close' && window.location.pathname == '/stock/chart'){
	        	// 주문 공간 막아버리기
	        	$('#forNonlogin').css('filter','blur(5px)');
				$('#CP1D2 .blur').css('filter','blur(5px)');
	        	$('#CP1D2').append($('<div id="closeMarket"/>').html(`<h1>개장 전 입니다 .</h1>`).css('width',$('#CP1D2').css('width')).css('height',$('#CP1D2').css('height')));
	        	}
	    });
	    stompClient.subscribe(destination, (greeting) => {
		    	console.log('알림성공');
		        //toastShow("체결 알림" ,greeting.body , "info"); // 구독된 url 에서 넘어오는 메세지 처리
		        Swal.fire({
				    toast: true,
				    icon: 'info',
				    title: '체결 알림',
				    text:greeting.body,
				    animation: false,
				    position: 'bottom-left',
				    showConfirmButton: true,
				    timer: 3000,
				    timerProgressBar: true,
				    confirmButtonText:'주문내역 이동하기',
				    didOpen: (toast) => {
				      toast.addEventListener('mouseenter', Swal.stopTimer)
				      toast.addEventListener('mouseleave', Swal.resumeTimer)
				    }
				  })
				  .then((result) => {
				  	if (result.isConfirmed){
				  		window.location.href="/member/mystock"
				  	}
				  });
		        $('#newPlace').html('<span class="badge badge-danger">New</span>');
		        $.ajax('/stock/resetPoint');
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

function showChat(chat) {
	let chatContent;
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
    
    if($("#chatSpace").children().length>50){
    	$("#chatSpace").children().eq(0).remove();
    }
    
    $("#scrollEvent").scrollTop($("#scrollEvent")[0].scrollHeight);
}

function sendChat() {
	console.log(chatRoomNo);
	fetch("addChat",{
		method: "POST",
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded'
		},
		body: 'cntn='+$("#my-message").val()+'&roomNo='+chatRoomNo+'&anonNick='+myAnonNick+'&membNo='+myMembNo
	})
	.then(response=>response.json())
	.then(result=>{
	    stompClient.publish({
	    	destination : "/mychat/room/"+chatRoomNo,
			body : JSON.stringify({'anonnick':result.anonNick,'drwupdt':result.drwupDt,'message': result.cntn})
			});		
		$("#my-message").val('');
	})			
}

function enterkey() {
	if($("#my-message").val()==null || $("#my-message").val()==''){
		return;
	}
	if (window.event.keyCode == 13) {
    	sendChat();
    } 
}


$(function () {
    $( "#sendChat" ).click(() => {
    	if($("#my-message").val()==null || $("#my-message").val()==''){
			return;
		}
    	sendChat()
    });
   
});

