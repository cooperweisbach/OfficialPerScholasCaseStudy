let inputs;
let imagePath;
let profilePic;
let expandInfo;
let passwordInfo = false;
let passwordSection = document.querySelector("#change-password-container");
let passwordForm = document.querySelector("#password-info-form");
let updatePasswordButton = document.querySelector("#submit-password-change");
updatePasswordButton.addEventListener("click", (event) => confirmChanges(event));

let generalInfo = false;
let generalSections = document.querySelectorAll(".member-info-update");
let generalForm = document.querySelector("#general-info-form");
let updateGeneralInfoButton = document.querySelector("#submit-general-updates");
updateGeneralInfoButton.addEventListener("click", (event) => confirmChanges(event));

let leaseInfo = false;
let leaseSection = document.querySelector("#lease-history-container");
let leaseForm = document.querySelector("#lease-info-form");
let leaseTerminationButton = document.querySelector("#submit-lease-termination");
leaseTerminationButton.addEventListener("click", (event)=> confirmChanges(event));

let modalContainer = document.querySelector("#modal-container");
let modalContent = document.querySelector("#modal-content");
let confirmUpdatesButton = document.querySelector("#confirm-updates");
let cancelUpdatesButton = document.querySelector("#cancel-updates");
cancelUpdatesButton.addEventListener("click", closeConfirmationModal);

let profilePicForm = document.querySelector("#profile-pic-form");

$(function() {
    imagePath = document.querySelector("#imagePath");
    profilePic = document.querySelector("#profile-pic");
    imagePath.onchange = (event) =>fileUpload(event);
    expandInfo = document.querySelectorAll(".expand-collapse-info");
    for(let info of expandInfo){
        info.addEventListener('click', (event) => toggleView(event));
    }
    inputs = document.getElementsByTagName('input');
    for(let input of inputs){
        input.addEventListener('click', function() {
            window.onbeforeunload =(event)=> performUpdate(event);
        })
    }

})

function fileUpload(event) {
    let formData = new FormData();
    formData.append('image', event.target.files[0]);
    fetch('/api/images/profile-pic',{method: 'POST', body: formData})
        .then(response => response.json())
        .then( data => {
            console.log(data);
            console.log(data[0]);
            profilePic.setAttribute('src',data[0]);
            }
        );
    confirmChanges(event);
}

function generalInfoUpdate(event){
    generalForm.submit();
    // closeConfirmationModal();
}

function passwordUpdate(event){
   passwordForm.submit();
   // closeConfirmationModal();
}

function leaseUpdate(event){
  leaseForm.submit();
  // closeConfirmationModal();
}

function imageUpdate(event){
    profilePicForm.submit();
    // closeConfirmationModal();
}

function performUpdate(event){
    return "this stringggg";
}

function toggleView(event){
    let id = event.target.getAttribute('id');
    switch(id){
        case "expand-collapse-password-info":
                    if(passwordInfo){
                        collapsePasswordInfo();
                    } else {
                        expandPasswordInfo();
                    }
            break;
        case "expand-collapse-general-info":
                    if(generalInfo){
                        collapseGeneralInfo();
                    }else{
                        expandGeneralInfo();
                    }
            break;
        case "expand-collapse-lease-info":
                    if(leaseInfo){
                        collapseLeaseInfo();
                    } else{
                        expandLeaseInfo();
                    }
            break;
        default: break;
    }
}

function collapsePasswordInfo(){
    passwordSection.classList.add("collapse");
    passwordSection.classList.remove("expand");
    passwordInfo=false;
    passwordForm.reset();
    window.onbeforeunload = null;
}

function expandPasswordInfo(){
    passwordSection.classList.add("expand")
    passwordSection.classList.remove("collapse");
    passwordInfo=true;
    collapseGeneralInfo();
    collapseLeaseInfo()
}

function collapseLeaseInfo(){
    leaseSection.classList.add("collapse");
    leaseSection.classList.remove("expand");
    leaseInfo = false;
    leaseForm.reset();
    window.onbeforeunload = null;
}

function expandLeaseInfo(){
    leaseSection.classList.add("expand");
    leaseSection.classList.remove("collapse");
    leaseInfo = true;
    collapseGeneralInfo();
    collapsePasswordInfo()
}

function collapseGeneralInfo(){
    for(let element of generalSections){
        element.classList.add("hidden");
    }
    generalInfo = false;
    generalForm.reset();
    window.onbeforeunload = null;
}

function expandGeneralInfo(){
    for(let element of generalSections){
        element.classList.remove("hidden");
    }
    generalInfo = true;
    collapseLeaseInfo();
    collapsePasswordInfo();
}

function confirmChanges(event){
    modalContainer.style.display = "flex";
    console.log(event);
    window.onbeforeunload = null;
    switch(event.target.getAttribute('id')){
        case "imagePath":
            confirmUpdatesButton.onclick = (event)=> imageUpdate(event);
            break;
        case "submit-lease-termination":
            confirmUpdatesButton.onclick = (event)=> leaseUpdate(event);
            break;
        case "submit-password-change":
            confirmUpdatesButton.onclick = (event)=> passwordUpdate(event);
            break;
        case "submit-general-updates":
            confirmUpdatesButton.onclick = (event)=> generalInfoUpdate(event);
            break;
        default: break;
    }
}


function closeConfirmationModal(){
    modalContainer.style.display = "none";
    window.onbeforeunload = (event)=> performUpdate(event);
}