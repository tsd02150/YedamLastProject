
		let totalData; //총 데이터 수
		let dataPerPage; //한 페이지에 나타낼 글 수
		let pageCount = 10; //페이징에 나타낼 페이지 수
		let globalCurrentPage=1; //현재 페이지
		let dataList; //표시하려하는 데이터 리스트
		let lastpage;
		
		$(document).ready(function () {
		 
		 dataPerPage = 10;
		 
		 $.ajax({ // ajax로 데이터 가져오기
			url: "allItemList",
			success: function (d) {
			   //totalData(총 데이터 수) 구하기
			   totalData = d.itemList.length;
		           //데이터 대입
		       dataList=d.itemList;
		       lastPage = Math.ceil(totalData/10);
	 			console.log(lastPage)
			 //글 목록 표시 호출 (테이블 생성)
			 displayData(1, dataPerPage);	
			 
			 //페이징 표시 호출
			 paging(totalData, dataPerPage, pageCount, 1);
			}
		 });
		});
		
		//현재 페이지(currentPage)와 페이지당 글 개수(dataPerPage) 반영
		function displayData(currentPage, dataPerPage) {

			let list = '';

		//Number로 변환하지 않으면 아래에서 +를 할 경우 스트링 결합이 되어버림.. 
		  currentPage = Number(currentPage);
		  dataPerPage = Number(dataPerPage);
		  if(currentPage == lastPage){
			  for(var i =(currentPage - 1) * dataPerPage ; i < dataList.length ; i++ ){
				  let plma = dataList[i].change == 0 ? ' ': (dataList[i].change > 0 ? 'plus' : 'minus');
	    			list += `<tr>
	    				<td>${dataList[i].sc}</td>
					<td>${dataList[i].theme}</td>
					<td><a href="chart?itemNo=${dataList[i].itemNo}">${dataList[i].nm}</a></td>
					<td>${dataList[i].tprc}</td>
					<td><span class="${plma}"> ${dataList[i].change <= 0 ? dataList[i].change : "+"+dataList[i].change } </span></td>
					<td><span class="${plma}" > (${dataList[i].rate <= 0 ? dataList[i].rate : "+"+dataList[i].rate}%) </span></td>
					</tr>`;
			  }
			  
		  }else{
			  
			  for (var i = (currentPage - 1) * dataPerPage ; i < (currentPage - 1) * dataPerPage + dataPerPage; i++) {
				  let plma = dataList[i].change == 0 ? ' ': (dataList[i].change > 0 ? 'plus' : 'minus');
	    			list += `<tr>`;
					list +=`<td>${dataList[i].sc}</td>`;
					list +=`<td>${dataList[i].theme}</td>`;
					list +=`<td><a href="chart?itemNo=${dataList[i].itemNo}">${dataList[i].nm}</a></td>`;
					list +=`<td>${dataList[i].tprc}</td>`;
					list +=`<td><span class="${plma}"> ${dataList[i].change <= 0 ? dataList[i].change : "+"+dataList[i].change } </span></td>`;
					list +=`<td><span class="${plma}" > (${dataList[i].rate <= 0 ? dataList[i].rate : "+"+dataList[i].rate}%) </span></td>`;
					list +=`</tr>`;
			  } //dataList는 임의의 데이터임.. 각 소스에 맞게 변수를 넣어주면 됨...
			  
		  }
		  
		  $("#allList").html(list);
		}//displayData end
		
		//페이징
		function paging(totalData, dataPerPage, pageCount, currentPage) {
			  console.log("currentPage : " + currentPage);

			  totalPage = Math.ceil(totalData / dataPerPage); //총 페이지 수
			  
			  if(totalPage<pageCount){
			    pageCount=totalPage;
			  }
			  
			  let pageGroup = Math.ceil(currentPage / pageCount); // 페이지 그룹
			  let last = pageGroup * pageCount; //화면에 보여질 마지막 페이지 번호
			  
			  if (last > totalPage) {
			    last = totalPage;
			  }

			  let first = parseInt((currentPage-1) / 10) * 10 +1;  //화면에 보여질 첫번째 페이지 번호
			  let next = last + 1;
			  let prev = first - 1;

			  let pageHtml = "";

			  if (prev > 0) {
			    pageHtml += "<li><a href='#' id='prev' onclick='return false'> 이전 </a></li>";
			  }

			 //페이징 번호 표시 
			  for (var i = first; i <= last; i++) {
			    if (currentPage == i) {
			      pageHtml +=
			        "<li class='on'><a href='#' id='" + i + "' onclick='return false'>" + i + "</a></li>";
			    } else {
			      pageHtml += "<li><a href='#' id='" + i + "' onclick='return false'>" + i + "</a></li>";
			    }
			  }

			  if (last < totalPage) {
			    pageHtml += "<li><a href='#' id='next' onclick='return false'> 다음 </a></li>";
			  }

			  $("#pagingul").html(pageHtml);

			  //페이징 번호 클릭 이벤트 
			  $("#pagingul li a").click(function () {
			    let $id = $(this).attr("id");
			    selectedPage = $(this).text();

			    if ($id == "next") selectedPage = next;
			    if ($id == "prev") selectedPage = prev;
			    
			    //전역변수에 선택한 페이지 번호를 담는다...
			    globalCurrentPage = selectedPage;
			    //페이징 표시 재호출
			    paging(totalData, dataPerPage, pageCount, selectedPage);
			    //글 목록 표시 재호출
			    displayData(selectedPage, dataPerPage);
			  });
			}//paging end
		
		
        //2초마다 인기검색어 순위 / 종목정보 변동 가져오기
        setInterval(() => {
        
        //인기검색어순위
          $.ajax({
            url: "inqChart",
            success: function (data) {
            	
      			let list = '';
      			for(let i =0; i< data.length ; i++){
      				list +=`<li><a href="chart?itemNo=${data[i].itemNo}">${i+1} ${data[i].nm}</a> <span ${data[i].change > 0 ? 'class="plus"':'class="minus"'}> ${data[i].change > 0 ? "+"+data[i].change : data[i].change} (${data[i].rate > 0 ? "+"+data[i].rate : data[i].rate}%)</span>`;
      			}
              $("#hot ul").html(list);
            },
          });
		//종목
          $.ajax({
        	  url:"allItemList",
        	  success:function(data){
        		  let list='';
        		  let lastPage = Math.ceil(data.itemList.length/10);
        		  if(globalCurrentPage == lastPage){
        			  for(var i =(globalCurrentPage - 1) * 10 ; i < data.itemList.length ; i++ ){
        				  let plma = data.itemList[i].change == 0 ? ' ': (data.itemList[i].change > 0 ? 'plus' : 'minus');
        	    			list += `<tr>`;
        					list +=`<td>${data.itemList[i].sc}</td>`;
        					list +=`<td>${data.itemList[i].theme}</td>`;
        					list +=`<td><a href="chart?itemNo=${data.itemList[i].itemNo}">${data.itemList[i].nm}</a></td>`;
        					list +=`<td>${data.itemList[i].tprc}</td>`;
        					list +=`<td><span class="${plma}"> ${data.itemList[i].change <= 0 ? data.itemList[i].change : "+"+data.itemList[i].change } </span></td>`;
        					list +=`<td><span class="${plma}" > (${data.itemList[i].rate <= 0 ? data.itemList[i].rate : "+"+data.itemList[i].rate}%) </span></td>`;
        					list +=`</tr>`;
        			  }
        			  
        		  }else{
        			  
        			  for (var i = (globalCurrentPage - 1) * 10 ; i < (globalCurrentPage - 1) * 10 + 10; i++) {
        				  let plma = data.itemList[i].change == 0 ? ' ': (data.itemList[i].change > 0 ? 'plus' : 'minus');
        	    			list += `<tr>`;
        					list +=`<td>${data.itemList[i].sc}</td>`;
        					list +=`<td>${data.itemList[i].theme}</td>`;
        					list +=`<td><a href="chart?itemNo=${data.itemList[i].itemNo}">${data.itemList[i].nm}</a></td>`;
        					list +=`<td>${data.itemList[i].tprc}</td>`;
        					list +=`<td><span class="${plma}"> ${data.itemList[i].change <= 0 ? data.itemList[i].change : "+"+data.itemList[i].change } </span></td>`;
        					list +=`<td><span class="${plma}" > (${data.itemList[i].rate <= 0 ? data.itemList[i].rate : "+"+data.itemList[i].rate}%) </span></td>`;
        					list +=`</tr>`;
        			  } //dataList는 임의의 데이터임.. 각 소스에 맞게 변수를 넣어주면 됨...
        			  
        		  }
        		  $("#allList").html(list);
        	  }
          })
             
        }, 2000);
			
        
        