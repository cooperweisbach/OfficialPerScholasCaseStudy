let modalCreateContainer = document.querySelector(".modal-container")
let createModalButton = document.querySelector('#create-model-modal');
createModalButton.addEventListener('click', displayCreateModal);
let createModalClose = document.querySelector(".create-modal-close-button");
createModalClose.addEventListener("click", closeCreateModal);

function displayCreateModal(){
    modalCreateContainer.style.display = 'flex';
    webBody[0].style.overflow = "hidden";
}
function closeCreateModal(){
    modalCreateContainer.style.display = "none";
    webBody[0].style.overflow = "scroll";
}
