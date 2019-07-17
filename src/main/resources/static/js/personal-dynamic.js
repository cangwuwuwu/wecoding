// 右上角小箭头动画
$(".ul-right > li > a").hover(function () {
    $(".mylist").addClass("mylist-hover");
    $(".down-arrow").addClass("rotate");
});
$(".ul-right > li").mouseleave(function () {
    $(".mylist").removeClass("mylist-hover");
    $(".down-arrow").removeClass("rotate");
});

// 头像放大动画
$(".uploadfile").hover(function () {
    $("#headimg").addClass("changesize");
}).mouseleave(function () {
    $("#headimg").removeClass("changesize");
});

// 标签变色特效
var tags = $(".left-tags");
tags.mouseover(function () {
    $(this).addClass("light");
}).mouseleave(function () {
    $(this).removeClass("light");
});
tags.click(function () {
    tags.removeClass("active");
    $(this).addClass("active");
});