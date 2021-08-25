const prevImageButton = document.querySelector("#prev-image-container");
const nextImageButton = document.querySelector("#next-image-container");
const slideshowContainer = document.querySelector("#fifth-section-container");
let imagesList = [];

console.log(prevImageButton);
console.log(nextImageButton);
console.log(imagesList);

prevImageButton.addEventListener("click", prevImage);
nextImageButton.addEventListener("click", nextImage);


function nextImage(){
    imageList = document.getElementsByClassName("gallery-slides");
    slideshowContainer.appendChild(imageList[0]);
}

function prevImage(){
    imageList = document.getElementsByClassName("gallery-slides");
    slideshowContainer.insertBefore(imageList[imageList.length-1],slideshowContainer.firstChild);
}





