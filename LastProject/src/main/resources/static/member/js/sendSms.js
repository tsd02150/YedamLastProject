let testcode;
		
		let count1 = 0; // 본인인증 횟수 5회 제한 count
		let count2 = 0; // 인증번호 확인 횟수 5회 제한 count
		
		//가입당시 이름+연락처 비교해서 정보 확인
		$("#testBtn1").on("click", function(){
			if($("#telInput").val() == ""){
				$("#telInput").focus();
				return;
			}
			
			if($("#mname").val() == ""){
				$("#mname").focus();
				return;
			}
			console.log($("#telInput").val());
			console.log($("#mname").val());
			
			let toUser = telInput.value.replace(/[^0-9]/g, '').replace(/^(\d{3})(\d{4})(\d{4})$/, '$1$2$3');
			testcode = createSmsKey();
			console.log(testcode);
			
			//let telUrl = "/member/getMemberTel?tel=" + encodeURI($("#telInput").val()) + "&nm=" + encodeURI($("#mname").val());
			$.ajax({
				url : "getMemberTel",
				method: "POST",
				data: {
		      tel: $("#telInput").val(),
		      nm: $("#mname").val()
				},
				success : function(data){
					count1++;
					if(data.tel != $("#telInput").val() && data.nm != $("#mname").val()){
						console.log(data.tel);
						$("#telCheck").text("가입 정보가 없습니다. 입력정보가 맞는지 다시 확인해주세요.")
							.css({
								"color": "#FA3E3E",
								"font-weight": "bold",
								"font-size": "14px"
							});
					}else {
					  /*$.ajax({
						    url: "sendSms",
						    method: "POST",
						    data: {
						      to: toUser,
						      content: "[SMS] 인증번호 [" + testcode + "]를 입력해주세요"
						    },
						    success: function(response) {
						      console.log("key: " + testcode);
						    },
						    error: function(err) {
						      console.log(err);
						    }
						  });// sms 발송 끝*/
						$("#idInput").val(data.id);
						$("#telCheck").text("인증번호가 발송되었습니다.")
							.css({
								"color": "#0D6EFD",
								"font-weight": "bold",
								"font-size": "14px"
						}); // 인증번호 확인 끝.
						$("#testBtn1").addClass("isOk") //본인인증 실행 여부 체크
					}
					
					if (count1 >= 5) {
			      alert("5회 이상 실패하셨습니다.\n본인인증을 다시 진행해주세요.");
			      window.location.reload();
					}
				},
				error : function(err){
					console.log(err);
				}
			})
		})
			
			//인증번호 비교
			$("#testBtn2").on("click", function(){
				if($("#telInput").val() == ""){
					$("#telInput").focus();
					return;
				}
				
				if($("#mname").val() == ""){
					$("#mname").focus();
					return;
				}
				console.log("인증번호 비교");
				console.log(testcode);
				
				if($("#codeCheckInput").val() != testcode){
					count2++;
					$("#codeCheck").text("인증번호가 일치하지 않습니다. 다시 입력하세요")
					.css({
						"color": "#FA3E3E",
						"font-weight": "bold",
						"font-size": "14px"
					});
				} else {
					$("#codeCheck").text("인증이 완료되었습니다.")
					.css({
						"color": "#0D6EFD",
						"font-weight": "bold",
						"font-size": "14px"
					});// 인증번호 확인 비교 끝
					
					$("#testBtn2").addClass("isOk"); //인증번호 비교 실행 여부 체크
					
				}
				if (count2 >= 5) {
		      alert("5회 이상 실패하셨습니다.\n본인인증을 다시 진행해주세요.");
		      window.location.reload();
				}
			})

			//연락처 저장 포맷 & 유효성검사
		 const autoHyphen = (target) => {
			    target.value = target.value.replace(/[^0-9]/g, '').replace(/^(\d{3})(\d{4})(\d{4})$/, `$1-$2-$3`);
			};
			
			//이름 유효성 검사
			const nameInput = document.querySelector("#mname");
			const nameCheck = document.querySelector("#nameCheck");
			const telInput = document.querySelector("#telInput");

			nameInput.addEventListener("input", nameCheckFunc);
			
			function nameCheckFunc() {
			    const name = nameInput.value;

			    // Regular expression to validate Korean name
			    const nameRegex = /^[가-힣]{2,5}$/;

			    if (nameRegex.test(name)) {
			        nameCheck.textContent = "";
			    } else {
			        nameCheck.textContent = "잘못 입력하셨습니다.";
			    }
			};
			
			function createSmsKey() {
				  let key = '';
				  
				  for (let i = 0; i < 5; i++) { 
				    key += Math.floor(Math.random() * 10);
				  }
				  return key;
				}