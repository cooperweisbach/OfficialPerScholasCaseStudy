var menuButton = document.querySelector(".hidden-flower-button");
var  menuDiv = document.querySelector(".hidden-menu-container");

menuButton.addEventListener("click", displayMenu);

function displayMenu() {
    if(menuDiv.classList.contains("showMenu")){
        menuDiv.classList.remove("showMenu");
    } else {
        menuDiv.classList.add("showMenu");
    }
}