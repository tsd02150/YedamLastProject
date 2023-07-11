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
  },
});

// 증권 선택시 테마 리스트 출력
$("#sc").on("click", "li", themeList);

// 테마 리스트 출력
function themeList(value) {
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

//종목 클릭시 정보 출력
$("#item").on("click", "li", itemInfo);
//종목 정보 출력
function itemInfo(value) {
  let ctg;
  if (typeof value == "object") {
    ctg = $(event.target).data("cd");
  } else {
    ctg = value;
  }
  $("#itemInfo").html("");
  $.ajax({
    url: "itemInfo?value=" + ctg,
    success: function (result) {
      for (let i in result) {
        if (i == "itemNo") {
          $("#itemInfo").append(
            $("<p/>")
              .css("display", "none")
              .text(result[i])
              .attr("data-in", result[i])
          );
        } else if (i == "tprc") {
          $("#itemInfo").append(
            $("<p/>").text("현재가 : " + result[i] + " 원")
          );
        } else if (i == "change") {
          let plma = result[i] == 0 ? "zero" : result[i] > 0 ? "plus" : "minus";
          $("#itemInfo").append(
            $("<p class=" + plma + "/>").text("전일비 : " + result[i] + " ")
          );
        } else if (i == "rate") {
          let plma = result[i] == 0 ? "zero" : result[i] > 0 ? "plus" : "minus";
          $("#itemInfo").append(
            $("<p class=" + plma + "/>").text("변동률 : ( " + result[i] + "% )")
          );
        } else {
          $("#itemInfo").append($("<p/>").text(result[i]));
        }
      }

      $("#itemInfo").append(
        $("<button/>").text("관심종목추가").on("click", addInterest)
      );
    },
  });
}

function addInterest() {
  console.log($(this).parent().children("p:first-of-type").data("in"));
  let iteminfo = $(this).parent().children("p:first-of-type").data("in");
  let membinfo = $('#sessionMembNo').text();
  console.log(membinfo);
  $.ajax("insertIntItem", {
    method: "post",
    data: { membNo: membinfo, itemNo: iteminfo },
    success: function (data) {
      let html='';
      for (let i = 0; i < data.length; i++) {
        html += `<p class="border my-1">
                ${data[i].nm} <span class="${data[i].change == 0 ? '_' : (data[i].change > 0 ? 'plus' : 'minus')}">
                ${data[i].change == 0 ? data[i].change : (data[i].change > 0 ? "+" + data[i].change : data[i].change) }
                (${data[i].rate == 0 ? data[i].rate : (data[i].rate > 0 ? "+"+data[i].rate : data[i].rate) }%)
                </span>
                </p>`;
      }
      $("#favItem").html(html);
    },
    error: function (xhr) {
      console.log(xhr);
    },
  });
}
