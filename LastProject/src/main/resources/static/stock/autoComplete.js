let itemNo = $('p[data-in]').data('in'); // 아이템 번호
// 차트생성함수
function makeChart(chart){
				makeCandleChart(chart);
				makeStickChart(chart);
			}

$(".searchItem").autocomplete({
  source: function (request, response) {
    //source: 입력시 보일 목록
    $.ajax({
      url: "autoComplete",
      type: "POST",
      dataType: "JSON",
      data: { value: request.term }, // 검색 키워드
      success: function (data) {
        // 성공
        console.log(data);
        response(
          $.map(data.resultList, function (item) {
            return {
              label: item.CTGR, // 목록에 표시되는 값
              value: item.CTGR, // 선택 시 input창에 표시되는 값
              idx: item.SEQ, // index
            };
          })
        ); //response
      },
      error: function () {
        //실패
        alert("오류가 발생했습니다.");
      },
    });
  },
  focus: function (event, ui) {
    // 방향키로 자동완성단어 선택 가능하게 만들어줌
    return false;
  },
  minLength: 1, // 최소 글자수
  autoFocus: true, // true == 첫 번째 항목에 자동으로 초점이 맞춰짐
  delay: 100, //autocomplete 딜레이 시간(ms)
  select: function (evt, ui) {
    // 아이템 선택시 실행 ui.item 이 선택된 항목을 나타내는 객체, lavel/value/idx를 가짐
    console.log(ui.item.value);
    console.log($(this).hasClass('move'))
    if($(this).hasClass('move')){
    	$.ajax('nmGetNo?nm='+ui.item.value).done(function(data){
    		location.href='chart?itemNo='+data;
    	})
    }else if($(this).hasClass('interest')){
	    $.ajax('nmGetNo?nm='+ui.item.value).done(function(data){
	    		addInterest(data);
	    })
    	
    }
  },
});

// 증권 선택시 테마 리스트 출력
$("#sc").on("click", "li", themeList);

// 테마 리스트 출력
function themeList(value) {
$("#itemInfo").html("");
  let ctg;
  console.log(value);
  if (typeof value == "object") {
    ctg = $(event.target).data("cd");
    console.log("1" + ctg);
  } else {
    ctg = value;
    console.log("2" + ctg);
  }
  $("#theme").html("");
  $("#item").html("");

  $.ajax({
    url: "themeList?code=" + encodeURI(ctg),
    success: function (result) {
      result.forEach((th) => {
        $("#theme").append(
          $("<li/>").text(th.CTGR).attr("data-cd", th.COMMON_CD)
        );
      });
    },
  });
}

//테마 클릭시 종목 리스트 출력
$("#theme").on("click", "li", itemList);

//종목리스트 출력
function itemList(value) {
$("#itemInfo").html("");
  let ctg;
  if (typeof value == "object") {
    ctg = $(event.target).data("cd");
  } else {
    ctg = value;
  }
  $("#item").html("");

  $.ajax({
    url: "themeList?code=" + encodeURI(ctg),
    success: function (result) {
      result.forEach((th) => {
        $("#item").append(
          $("<li/>").text(th.CTGR).attr("data-cd", th.COMMON_CD)
        );
      });
    },
  });
}



// 함수 캡쳐링
$('#itemPtag').on('click','button',function(){
	
				if($('#sessionMembNo').text() != 'nonLoginUser'){
					console.log('zz')
					addInterest(itemNo);
				}else{
					Swal.fire({
							  title: '로그인',
							  text: "로그인이 필요한 서비스입니다.",
							  icon: 'info',
							  showCancelButton: true,
							  confirmButtonColor: '#3085d6',
							  cancelButtonColor: '#d33',
							  confirmButtonText: '로그인하기',
							  cancelButtonText:'취소'
							}).then((result) => {
							  if (result.isConfirmed) {
							   	window.location.href="/member/login"
							  }
							});
				}
			
	
});


// 관심종목 추가기능
function addInterest(itemNumber) {
  
  let iteminfo = itemNumber;
  console.log(this);
  console.log(event.target);
  console.log(iteminfo)
  let membinfo = $('#sessionMembNo').text();
  console.log(membinfo);
  $.ajax("insertIntItem", {
    method: "post",
    data: { membNo: membinfo, itemNo: iteminfo },
    success: function (data) {
    	$('.searchItem.interest').val('');
    	if(data.code == 'success'){
    		  Swal.fire({
							    toast: true,
							    icon: 'success',
							    title: '관심종목 추가',
							    text:data.msg,
							    animation: false,
							    position: 'bottom-right',
							    showConfirmButton: false,
							    timer: 3000,
							    timerProgressBar: true,
							    didOpen: (toast) => {
							      toast.addEventListener('mouseenter', Swal.stopTimer)
							      toast.addEventListener('mouseleave', Swal.resumeTimer)
							    }
							  });
    		  let html='';
		      for (let i = 0; i < data.list.length; i++) {
		        html += `<p class="border my-1"><input  class="btn btn-danger btn-sm" type="button" value="x" data-info="${data.list[i].itemNo}">
		                ${data.list[i].nm} <span class="${data.list[i].change == 0 ? '_' : (data.list[i].change > 0 ? 'plus' : 'minus')}">
		                ${data.list[i].change == 0 ? data.list[i].change : (data.list[i].change > 0 ? "+" + data.list[i].change : data.list[i].change) }
		                (${data.list[i].rate == 0 ? data.list[i].rate : (data.list[i].rate > 0 ? "+"+data.list[i].rate : data.list[i].rate) }%)
		                </span>
		                </p>`;
		      }
		      $("#favItem").html(html);
    	}else if(data.code == 'fail'){
    		toastShow("관심종목 추가" , data.msg , "error");
    	}else if(data.code == 'warning'){
    		Swal.fire({
			    toast: true,
			    icon: 'warning',
			    title: '관심종목 추가',
			    text:data.msg,
			    animation: false,
			    position: 'bottom-right',
			    showConfirmButton: false,
			    timer: 3000,
			    timerProgressBar: true,
			    didOpen: (toast) => {
			      toast.addEventListener('mouseenter', Swal.stopTimer)
			      toast.addEventListener('mouseleave', Swal.resumeTimer)
			    }
			  });
    	}
      
    },
    error: function (xhr) {
      console.log(xhr);
    }
  });
}

//실시간 정보 변동
  setInterval(() => {
  //아이템 번호
  let itemNo = $('p[data-in]').data('in');
  // 로그인한 유저번호 - 비회원은  nonLoginUser 로 표시
  let membinfo = $('#sessionMembNo').text();
  
  	// 로그인 되어 있을시에만 작동
  	if(membinfo != 'nonLoginUser'){
  	
	  	//관심정보 실시간변동
	  	$.ajax('ajaxUsetInt',{
	  		method:'post',
	  		data:{membNo : membinfo},
	  		success:function(data){
	  			let html='';
			      for (let i = 0; i < data.list.length; i++) {
			        html += `<p class="border my-1 pointer"><input class="btn btn-danger btn-sm" type="button" value="x" data-info=${data.list[i].itemNo}>
			                ${data.list[i].nm} <span class="${data.list[i].change == 0 ? '_' : (data.list[i].change > 0 ? 'plus' : 'minus')}">
			                ${data.list[i].change == 0 ? data.list[i].change : (data.list[i].change > 0 ? "+" + data.list[i].change : data.list[i].change) }
			                (${data.list[i].rate == 0 ? data.list[i].rate : (data.list[i].rate > 0 ? "+"+data.list[i].rate : data.list[i].rate) }%)
			                </span>
			                </p>`;
			      }
			      $("#favItem").html(html);
	  		},
	  		error:function(xhr){
	  			console.log(xhr);
	  		}
	  	});
	  	
	  	//보유종목 실시간변동
	  	$.ajax('ajaxUserPoss',{
	  		method:'post',
	  		data:{membNo : membinfo},
	  		success:function(data){
	  			if(data.list.length != 0){
	  				let html='';
			     	for (let i = 0; i < data.list.length; i++) {
				        html += `<p class="border my-1 pointer"><input class="btn btn-danger btn-sm invisible" type="button" value="x"  disabled="disabled" data-info=${data.list[i].itemNo}>
				                ${data.list[i].nm} <span class="${data.list[i].change == 0 ? '_' : (data.list[i].change > 0 ? 'plus' : 'minus')}">
				                ${data.list[i].change == 0 ? data.list[i].change : (data.list[i].change > 0 ? "+" + data.list[i].change : data.list[i].change) }
				                (${data.list[i].rate == 0 ? data.list[i].rate : (data.list[i].rate > 0 ? "+"+data.list[i].rate : data.list[i].rate) }%)
				                </span>
				                </p>`;
			      	}
			      	$("#possItem").html(html);
			      }else{
			      	$("#possItem").html(`<p>보유주식이 없습니다.</p>`);
			      }
	  		},
	  		error:function(xhr){
	  			console.log(xhr);
	  		}
	  	});
	  	
	  	
	  	// 보유주식 수와 수익률 보유포인트 실시간 변동
	  	$.ajax('possStock',{
	  				method:'post',
	  				data:{membNo: membinfo , itemNo: itemNo},
	  				success:function(data){
	  					let rate = data.rate > 0 ? "+ "+data.rate : data.rate;
	  					let rateClass = data.rate == 0 ? '' : (data.rate > 0 ? 'plus' : 'minus');
	  					$('#CP2D22 div p:nth-of-type(2) span').text(data.cnt);
	  					$('#CP2D22 div p:nth-of-type(3) span').text(rate).addClass(rateClass);
	  					$('#CP2D22 div p:nth-of-type(4)').attr('data-point',data.point);
	  					$('#CP2D22 div p:nth-of-type(4) span').text(data.point.toLocaleString('ko-KR')+' point');
	  				},
	  				error:function(xhr){
	  					console.log(xhr)
	  				}
	  			});// end of ajax
	  	
  	}
  	
  		// 상승률
  		$.ajax('getPercentage?type=plus').done(function(data){
  		let html='';
  		data.perList.forEach(dt => {
  			html +=`<li><a href="chart?itemNo=${dt.itemNo}">${dt.nm}</a> <span class="plus">(+ ${dt.rate} %)</span></li>`
  			})
  		$('#asd ol').html(html);
  		
  		})
  			// 하락률
  		$.ajax('getPercentage?type=minus').done(function(data){
  		let html='';
  		data.perList.forEach(dt => {
  			html +=`<li><a href="chart?itemNo=${dt.itemNo}">${dt.nm}</a> <span class="minus">( ${dt.rate} %)</span></li>`
  			})
  		$('#zxc ol').html(html);
  		
  		})
  		
  		//거래량
  		$.ajax('getPercentage?type=plus').done(function(data){
  		let html='';
  		data.volList.forEach(dt => {
  			html +=`<li><a href="chart?itemNo=${dt.itemNo}">${dt.nm}</a> <span>${dt.vol}</span></li>`
  			})
  		$('#qwe ol').html(html);
  		
  		})
  		
  		//호가
  		//매도
  		
  		$.ajax('orderTable?type=sell&itemNo='+itemNo).done(function(data){
  			let html='';
  			if(data.length == 0){
					return;  					
	  			}
	  		$('#noOrder').remove();
  			data.forEach(dt => {
  				html+=`<tr><td class="minus">${dt.CNT}</td><td>${dt.PRC}</td></tr>`
  			})
  			$('#sell').html(html);
  		})
  		//매수
  		$.ajax('orderTable?type=buy&itemNo='+itemNo).done(function(data){
  			let html='';
  			if(data.length == 0){
					return;  					
	  			}
  			$('#noOrder').remove();
  			data.forEach(dt => {
  				html+=`<tr><td>${dt.PRC}</td><td class="plus">${dt.CNT}</td></tr>`
  			})
  			$('#buy').html(html);
  		})//
  		
  		// 차트위에 아이템 정보
  		$.ajax('getItemInfo?itemNo='+itemNo).done(function(data){
  			let html=''; 																
  			html = `<b style="font-size: 20px;"><p data-in="${data.itemNo}"> 종목 : ${data.nm} </b><br><span class="${data.change == 0 ? '_' : (data.change > 0 ? 'plus' : 'minus' )}"> 전일비 : ${data.change > 0? "+ "+data.change : data.change} 변동률 : ${data.rate > 0 ? "+"+data.rate+"%" : data.rate+"%"}</span></p><button class="btn btn-danger btn-sm" id="addInt2">관심종목추가</button>`
  			$('#itemPtag div:first-of-type').html(html);
  		})
  },3000);
  
  
  // dom tree 형성후 실행
  $(document).ready(function(){
  		let taBeing=0;
  
  			// 상승률
  		$.ajax('getPercentage?type=plus').done(function(data){
  		data.perList.forEach(dt => {
  			$('#asd ol').append($('<li/>').append($('<a/>').text(dt.nm).attr('href','chart?itemNo='+dt.itemNo))
  										  .append($('<span class="plus"/>').text("(+ "+dt.rate+" %)"))
  										  );
  			})
  		})//end 상승률
  			// 하락률
  		$.ajax('getPercentage?type=minus').done(function(data){
  		data.perList.forEach(dt => {
  			$('#zxc ol').append($('<li/>').append($('<a/>').text(dt.nm).attr('href','chart?itemNo='+dt.itemNo))
  										  .append($('<span class="minus"/>').text("( "+dt.rate+" %)"))
  										  );
  			})
  		})//end 하락률
  		
  		// 거래량
  		$.ajax('getPercentage?type=minus').done(function(data){
  		data.volList.forEach(dt => {
  			$('#qwe ol').append($('<li/>').append($('<a/>').text(dt.nm).attr('href','chart?itemNo='+dt.itemNo))
  										  .append($('<span/>').text(dt.vol))
  										  );
  			})
  		})//end 거래량
  		
  		// 호가
  		async function getTaBeing() {
		  let taBeing = 0;
		
		  // 매도
		  const sellPromise = new Promise((resolve, reject) => {
		    $.ajax('orderTable?type=sell&itemNo=' + itemNo).done(function (data) {
		      taBeing += data.length;
		      data.forEach((dt) => {
		        $('#sell').append($('<tr/>').append($('<td class="minus"/>').text(dt.CNT)).append($('<td/>').text(dt.PRC)));
		      });
		      resolve();
		    });
		  });
		
		  // 매수
		  const buyPromise = new Promise((resolve, reject) => {
		    $.ajax('orderTable?type=buy&itemNo=' + itemNo).done(function (data) {
		      taBeing += data.length;
		      data.forEach((dt) => {
		        $('#buy').append($('<tr/>').append($('<td/>').text(dt.PRC)).append($('<td class="plus"/>').text(dt.CNT)));
		      });
		      resolve();
		    });
		  });
		
		  await Promise.all([sellPromise, buyPromise]);
		 
		  // 여기에서 .then과 유사한 로직을 수행합니다.
		  if(taBeing ==0 ) $('#price').append($('<p id="noOrder" class="m-2" />').html('현재 해당 종목의 주문이 없습니다.'));
		  // 모든 AJAX 요청이 완료되었을 때 실행될 코드를 작성할 수 있습니다.
		  console.log('모든 AJAX 요청이 완료되었습니다.');
		  // 추가 작업 수행 가능...
		};
  		getTaBeing();	
  		
  		
  		//로그인 되어있으면 보유주식 수량 수익률 가져옴
  		if($('#sessionMembNo').text() != 'nonLoginUser'){
  			let membInfo = $('#sessionMembNo').text();
  			let itemInfo = itemNo;
  			$.ajax('possStock',{
  				method:'post',
  				data:{membNo: membInfo , itemNo: itemInfo},
  				success:function(data){
  					let rate = data.rate > 0 ? "+ "+data.rate : data.rate;
  					let rateClass = data.rate == 0 ? '' : (data.rate > 0 ? 'plus' : 'minus');
  					$('#CP2D22 div p:nth-of-type(2) span').text(data.cnt); // 보유주 span
  					$('#afford').val(data.cnt);
  					$('#CP2D22 div p:nth-of-type(3) span').text(rate).addClass(rateClass); // 수익률 span
  					$('#CP2D22 div p:nth-of-type(4)').attr('data-point',data.point);
  					$('#CP2D22 div p:nth-of-type(4) span').text(data.point.toLocaleString('ko-KR')+' point');
  				},
  				error:function(xhr){
  					console.log(xhr)
  				}
  			});// end of ajax
  		}// end 
  			
  						
  			
  		// 초기 chart 표시
  		$.ajax('dayChart?itemNo='+itemNo).done(function(chart){
  			
  			makeChart(chart);
  			
  		})//end chart ajax
	});
  	
