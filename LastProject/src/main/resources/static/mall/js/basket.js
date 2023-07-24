	
	/* 	basketList();
	//장바구니 리스트 출력
	function basketList(){
			console.log('aaa');
			fetch("/mall/getBasketList",{
				method:"POST",
				headers: {
		            'Content-Type': 'application/x-www-form-urlencoded'
		        },
				body: JSON.stringify({
		            membNo: [[${basketList.membNo}]]
			})
			.then(response=>response.json())
			.then(result=>{
				result.forEach(item=>{
					console.log(item);
					let trTag=`
						  <tr th:each="item :${basketList}">
                  	  <td th:text="${item.membNo}"></td>
					<td th:text="${item.prdtNo}">
                      <td class="shoping__cart__item">
                         <img style="width: 200px; height: 200px;"
			th:src="|/mall/images/main/${item.thumb}|">
                          <h5>Vegetable’s Package</h5>
                      </td>
                      <td class="shoping__cart__price">
                          $55.00
                      </td>
                      <td class="shoping__cart__quantity">
                          <div class="quantity">
                              <div class="pro-qty">
                              <span class="dec qtybtn">-</span>
                                  <input type="text" value="1">
                                  <span class="inc qtybtn">+</span>
                              </div>
                          </div>
                      </td>
                      <td class="shoping__cart__total">
                          $110.00
                      </td>
                      <td class="shoping__cart__item__close">
                        <button type="button" onclick="deleteBasket(event)"data-prdt-no="${item.prdtNo}">삭제</button>
                      </td>
                  </tr>
                
					`;
					document.querySelector("#basketList").innerHTML+=trTag;
				})
			
			})
			.catch(err=>console.log(err));
		}
		 */

	//장바구니 삭제
	/* function deleteBasket(event){
		
		 var prdtNo = $(event.target).closest('tr').find('.prdtNo').text();
		fetch("deleteBasket", {
			method : "POST",
			headers : {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			body : "prdtNo=" + prdtNo
		})
		.then(response => response.json())
		.then(result => {
			 if (result=="success") {
               window.location.href="/mall/basketList"
            		location.reload();
            }else {
            	toastShow("Fail Delete", "삭제 실패", "error");
            }
		})
		.catch(err => console.log(err));
	}
	 */
	 


