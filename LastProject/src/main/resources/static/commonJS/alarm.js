jQuery(document).ready(function ($) {
	let membNo = $('#sessionMembNo').text();
	
	// 알람 a 태그 누를시 발동
  $(document).on("click", ".realNew", function () {
    $("body").prepend(
      '<div class="bs-canvas-overlay bg-dark position-fixed w-100 h-100"></div>'
    );
    if ($(this).hasClass("pull-bs-canvas-right"))
      $(".bs-canvas-right").addClass("mr-0");
    else $(".bs-canvas-left").addClass("ml-0");
    return false;
  });

  $(document).on("click", ".bs-canvas-close, .bs-canvas-overlay", function () {
  	$('.bs-canvas').find('.active').toggleClass('active');
    var elm = $(this).hasClass("bs-canvas-close")
      ? $(this).closest(".bs-canvas")
      : $(".bs-canvas");
    elm.removeClass("mr-0 ml-0");
    $(".bs-canvas-overlay").remove();
    return false;
  });
  
  //확인안한 알람 있을시 new
  $.ajax('/stock/getNonChkAlm?membNo='+membNo).done(function(data){
  		console.log(data);
  		let nCnt = 0;
  		
  		// 전체 데이터 반복문
  		for(let i of data){
  			if(i.checked == 'n') nCnt++;
  			 
  		}// end of for of data
  		
  		// 미확인 알람이 있을시 new 표시
  		if(nCnt > 0 ) $('#newPlace').html('<span class="realNew badge badge-danger">New</span>');
  		});
  
	// 알람벨 누를시 기능
  $('#alarmBellBtn').on('click','i',function(){
		$('#newPlace').html('');
	
			//미확인 알람 가져오기
		  
		  $.ajax('/stock/getNonChkAlm?membNo='+membNo).done(function(data){
		  		console.log(data);
		  		let nCnt = 0;
		  		let stockAlm =[];
		  		let mallAlm =[];
		  		let commAlm =[];
		  		
		  		// 전체 데이터 반복문
		  		for(let i of data){
		  			 
		  			 if(i.atype == 'stock'){
		  			 	stockAlm.push(i);
		  			 }else if ( i.atype == 'mall'){
		  			 	mallAlm.push(i);
		  			 }else if ( i.atype == 'comm') {
		  			 	commAlm.push(i);
		  			 }
		  			
		  		}// end of for of data
		  		
		  		// stock 알림 반복문
		  		let html = '';
		  		for(let i of stockAlm){
		  			if(i.checked == 'n') nCnt++;
		  			html +=`<li data-alno="${i.alarmNo}">
					          	<p class="list-group-item list-group-item-action h-100 ">
					          		<button type="button" class="float-left close almXBtn" aria-label="Close">
					          			<span aria-hidden="true" class="text-dark mr-1">&times;</span>
					          		</button>
					          		 체결 알림
					          		<span class="text-muted small">${makeDay(new Date()) == makeDay(new Date(i.alarmDt)) ? '<span class="badge badge-primary">Today</span>' : ''} 
					          		${i.checked == 'n' ? '<span class="badge badge-danger">New</span>' : ''} ${new Date(i.alarmDt).toLocaleDateString()}</span>
					          		<span class="d-block"> ${i.cntn}</span>
				          		</p>
			          		</li>`
		  			
		  			
		  		}
		  		$('#stock-alm ul').html(html)
		  		if(nCnt > 0 ) {
		  		
		  		$('a[href="#stock-alm"]').html('체결<span class="badge badge-danger">New</span>')
		  								 .attr('data-chk','ok');
		  		};
		  });//end of ajax
});//end of 알람벨 누를시 기능

	// 체결 tab 클릭 기능
	$('a[href="#stock-alm"]').on('click',function(){
		if($('a[href="#stock-alm"]').attr('data-chk') == 'ok'){
			$('a[href="#stock-alm"]').html('체결').attr('data-chk','');
			$('#stock-alm li[data-alno]').each(function(idx,item){
				console.log($(item).attr('data-alno'));
				$.ajax('/stock/changeChk?almNo='+$(item).attr('data-alno')).done(function(data){
					$('a[href="#stock-alm"]').html('체결');
				});
			});//end of li반복문
		}//end of if
		
	});// end of 체결 tab 클릭 기능
	
	
	// x 버튼 기능
	$('#alarmTab').on('click','.almXBtn',function(){
		console.log('zz')
		let btn = $(this)
		let almNo = $(this).parent().parent().attr('data-alno');
		console.log(almNo)
		$.ajax('/stock/deleteAlm?almNo='+almNo).done(function(data){
			if(data > 0) btn.parent().parent().remove();
		});//end of ajax
		
	})
});

function makeDay(Date){
	var today = Date;
	
	var year = today.getFullYear();
	var month = ('0' + (today.getMonth() + 1)).slice(-2);
	var day = ('0' + today.getDate()).slice(-2);
	
	var dateString = year + '-' + month  + '-' + day;
	
	return dateString;
}