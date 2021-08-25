const dropDownMenuResources = document.querySelector("#resources-drop-down-menu");
const dropDownHolderResources = document.querySelector("#resources-drop-down-holder");
const dropDownMenuEvents = document.querySelector("#events-drop-down-menu");
const dropDownHolderEvents = document.querySelector("#events-drop-down-holder");

dropDownHolderResources.addEventListener("mouseover", showResourcesMenu);
dropDownHolderResources.addEventListener("mouseleave", hideResourcesMenu);
dropDownHolderEvents.addEventListener("mouseover", showEventsMenu);
dropDownHolderEvents.addEventListener("mouseleave", hideEventsMenu);

function showResourcesMenu(){
    dropDownMenuResources.classList.add("show-dropdown-menu");
}
function hideResourcesMenu(){
    dropDownMenuResources.classList.remove("show-dropdown-menu");
}

function showEventsMenu(){
    dropDownMenuEvents.classList.add("show-dropdown-menu");
}
function hideEventsMenu(){
    dropDownMenuEvents.classList.remove("show-dropdown-menu");
}

