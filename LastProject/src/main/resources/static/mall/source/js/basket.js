export let basket = {
	totalCount: 0,
	totalPrice: 0,
	//체크된 아이템 선택삭제
	delCheckedItem: function() {

		let checkItem = document.querySelectorAll('.row.data input[type="checkBox"]:checked');
		checkItem.forEach(function(item) {
			let row = item.closest('.row.data');
			let parent = row.parentNode;
			parent.removeChild(row);
		})
		console.log('delCheckedItem');

	},
	//장바구니 비우기
	delAllItem: function() {

		let delAllItem = document.querySelectorAll('.row.data');

		delAllItem.forEach(function(delAllItem) {
			delAllItem.parentNode.removeChild(delAllItem);
		})

		console.log('delAllItem');
	},

	reCalc: function() {
		console.log('reCalc');
		this.totalCount = 0;
		this.totalPrice = 0;
		
		
	},

	updateUI: function() {
		console.log('updateUI');

		let cval = event.target.parentNode.parentNode.childNodes[1].value;
		event.target.parentNode.parentNode.childNodes[1].value = parseInt(cval) + 1
		
		
		
		document.querySelectorAll('.p_num').forEach(function (item){
			let count = parseInt(item.getAttribute('value'));
			this.totalCount += count;
			
			//let price = 
			
		}, this);
	
		document.querySelector('#sum_p_num').textContent = '상품개수: ' + this.totalCount.formatNumber() + '개'
		document.querySelector('#sum_p_price').textContent = '합계금액: ' + this.totalPrice.formatNumber() + '원'
	},

	//수랑변경 
	changePNum: function(pos) {
		let target = document.querySelectorAll('div.row.data:nth-of-type(' + pos + ')');
		console.log("changePNum");

		console.log(event);
		if (event.target.tagName == 'INPUT') {
			console.log('input 클릭');
		} else if (event.target.tagName == 'I') {
			console.log('icon 클릭');

			//console.log(event.target.classList);
			if (event.target.classList.contains('up')) {
				console.log('icon 클릭. up'); //현재값에서 +1
				let cval = event.target.parentNode.parentNode.childNodes[1].value;
				event.target.parentNode.parentNode.childNodes[1].value = parseInt(cval) + 1
				console.log(event.target.parentNode.parentNode.parentNode.parentNode.childNodes[2].innerText);
				//console.log(event.target.parentNode.parentNode.childNode.value);
				//target.querySelector('input').value = 50;
			} else {
				console.log('icon 클릭. down'); //현재값에서 -1
				//target.querySelector('input').value = -50;
				let cval = event.target.parentNode.parentNode.childNodes[1].value;
				event.target.parentNode.parentNode.childNodes[1].value = parseInt(cval) - 1

			}
		}
	},
	//장바구니 단건 삭제
	delItem: function() {

		/*let row = this.closest('.row.data');
		row.parentNode.removeChild(row);	*/
		console.log(event.target.closest('div.row.data').remove());
		console.log('delItem');

	},

	cartList: function() {
		cartItems.forEach((item, idx) => {
			let template = document.querySelector('#template>div.row.data').cloneNode(true);
			template.querySelector('.img>img').setAttribute('src', '../img/' + item.image)
			template.querySelector('.pname>span').textContent = item.productNm
			template.querySelector('.basketprice>input').value = item.price
			template.querySelector('.basketprice').childNodes[2].textContent = item.price.formatNumber() + "원"
			template.querySelector('.updown>input').value = item.qty
			template.querySelector('.updown>input').setAttribute('value', item.qty)
			template.querySelector('.updown>input').setAttribute('id', 'p_num' + (idx + 1));
			template.querySelector('.sum').textContent = (item.price * item.qty).formatNumber() + "원"

			document.querySelector('#basket').append(template)
		})
	}
};

var cartItems = [{
	no: 1,
	productNm: '이것이 민트다.',
	qty: 2,
	price: 12000,
	image: 'item1.PNG'
},
{
	no: 2,
	productNm: '와 아이스크림.',
	qty: 1,
	price: 22000,
	image: 'item2.PNG'
},
{
	no: 3,
	productNm: '모나카 케익.',
	qty: 1,
	price: 13600,
	image: 'item3.PNG'
}
]

// 숫자 3자리 콤마찍기
Number.prototype.formatNumber = function() {
	if (this == 0) return 0;
	let regex = /(^[+-]?\d+)(\d{3})/;
	let nstr = (this + '');
	while (regex.test(nstr)) nstr = nstr.replace(regex, '$1' + ',' + '$2');
	return nstr;
};