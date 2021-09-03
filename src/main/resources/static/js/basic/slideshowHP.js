const prevImageButton = document.querySelector("#prev-image-container");
const nextImageButton = document.querySelector("#next-image-container");
const slideshowContainer = document.querySelector("#fifth-section-container");
let imagesList = [];

console.log(prevImageButton);
console.log(nextImageButton);
console.log(imagesList);

prevImageButton.addEventListener("click", (event)=> prevImage(event));
nextImageButton.addEventListener("click", nextImage);


function nextImage(){
    console.log(nextImageButton);
    imagesList = document.getElementsByClassName("gallery-slides");
    slideshowContainer.appendChild(imagesList[0]);
}

function prevImage(event){
    console.log(event.currentTarget);
    imagesList = document.getElementsByClassName("gallery-slides");
    slideshowContainer.insertBefore(imagesList[imagesList.length-1],slideshowContainer.firstChild);
}





