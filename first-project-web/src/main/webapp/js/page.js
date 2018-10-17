(function ($) {
    $(".pageBtn").click(function () {
        var btn = $(this);
        var pageNum = $.trim(btn.attr("pn"));
        $("#pageNum").val(pageNum) ;
        $("#pageForm").submit() ;
    });
    $("#go_page").click(function () {
        var pageNum = $.trim($("#go_page_num").val());
        var pageSize = $.trim($("#per_page_count").val()) ;
        $("#pageNum").val(pageNum) ;
        $("#pageSize").val(pageSize) ;
        $("#pageForm").submit() ;
    });
    $('#go_page_num').keyup(function(event) {
        if (event.keyCode === $.ui.keyCode.ENTER) {
            $("#go_page").click() ;
        }
    }) ;
    $('#per_page_count').keyup(function(event) {
        if (event.keyCode === $.ui.keyCode.ENTER) {
            $("#go_page").click() ;
        }
    }) ;
})(jQuery);