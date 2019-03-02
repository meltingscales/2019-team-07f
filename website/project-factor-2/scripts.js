$(window).on("scroll", function () {
    if ($(window).scrollTop() > 50) {
        $(".Home-Navigation").addClass("active");
    } else {
        //remove the background property so it comes transparent again (defined in your css)
        $(".Home-Navigation").removeClass("active");
    }
});