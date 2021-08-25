const resourcesButton = document.querySelector("#resources-button");
const resourcesLinks = document.querySelector("#resources-links");
const eventsButton = document.querySelector("#events-button");
const eventsLinks = document.querySelector("#events-links");
resourcesButton.addEventListener("click", showResourcesLinks);
eventsButton.addEventListener("click", showEventsLinks)


function showResourcesLinks(){
    if(resourcesButton.classList.contains("showingLinks")){
        resourcesLinks.classList.add("hide-resource-links");
        resourcesButton.classList.remove("showingLinks");
    } else {
        if(eventsButton.classList.contains("showingLinks")){
            eventsLinks.classList.add("hide-event-links");
            eventsButton.classList.remove("showingLinks");
        }
        resourcesLinks.classList.remove("hide-resource-links");
        resourcesButton.classList.add("showingLinks");
    }
}

function showEventsLinks(){
    if(eventsButton.classList.contains("showingLinks")){
        eventsLinks.classList.add("hide-event-links");
        eventsButton.classList.remove("showingLinks");
    } else{
        if(resourcesButton.classList.contains("showingLinks")){
            resourcesLinks.classList.add("hide-resource-links");
            resourcesButton.classList.remove("showingLinks");
        }
        eventsLinks.classList.remove("hide-event-links");
        eventsButton.classList.add("showingLinks");
        
    }
}