const memberIcon = document.querySelector(".span-member-links-container");
memberIcon.addEventListener("mouseover", showSideMenu);
memberIcon.addEventListener("mouseleave", hideSideMenu);
const sideMenu = document.querySelector("#hover-driven-side-panel");

function showSideMenu(){
    sideMenu.classList.add("show-side-menu");
}
function hideSideMenu(){
    sideMenu.classList.remove("show-side-menu");
}
