jQuery(document).ready(function ($) {
  $(document).on("click", ".pull-bs-canvas-right", function () {
    $("body").prepend(
      '<div class="bs-canvas-overlay bg-dark position-fixed w-100 h-100"></div>'
    );
    if ($(this).hasClass("pull-bs-canvas-right"))
      $(".bs-canvas-right").addClass("mr-0");
    else $(".bs-canvas-left").addClass("ml-0");
    return false;
  });

  $(document).on("click", ".bs-canvas-close, .bs-canvas-overlay", function () {
    var elm = $(this).hasClass("bs-canvas-close")
      ? $(this).closest(".bs-canvas")
      : $(".bs-canvas");
    elm.removeClass("mr-0 ml-0");
    $(".bs-canvas-overlay").remove();
    return false;
  });

  //미확인 알람 가져오기
});
