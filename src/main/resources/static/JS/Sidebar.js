// Sidebar.js

$(document).ready(function () {
  $(".menu > ul > li").click(function (e) {
    e.preventDefault(); // Prevent the default behavior of anchor tags
    // remove active from already active
    $(this).siblings().removeClass("active");
    // add active to clicked
    $(this).toggleClass("active");
    // if has sub menu open it
    $(this).find("ul").slideToggle();
    // close other sub menu if any open
    $(this).siblings().find("ul").slideUp();
    // remove active class of sub menu items
    $(this).siblings().find("ul").find("li").removeClass("active");
  });

  $(".menu-btn").click(function () {
    $(".sidebar").toggleClass("active");
  });
});
