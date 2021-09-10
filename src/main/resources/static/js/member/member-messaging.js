$(()=>{
        let enterThreadButtons = document.querySelectorAll(".enter-message-thread-button");
        enterThreadButtons.forEach((button)=>{
            button.addEventListener("click", (event) => viewThread(event));
        })
    }
)

let currentThread;
let enteredArea;
let unenteredView;

let stompClient = null;
let userId = document.querySelector("#current-user-id").innerHTML;
let connectingElement;
let messageInput;
let messageArea;
let messageForm;
let closeViewContainer;
let closeViewButton;
let inAThread = false;
let pageNum;
let numOfResults = 30;
let returnedMessages;

function viewThread(event){

    if(inAThread){
        closeCurrentThread();
    }
    pageNum = 0;
    inAThread = true;
    currentThread = event.currentTarget.getAttribute('id').split("-")[0];
    messageArea = document.querySelector("#message-area-"+currentThread);
    enteredArea = document.querySelector("#entered-view-"+currentThread);
    enteredArea.classList.remove("hidden");
    unenteredView = document.querySelector("#unentered-view-"+currentThread);
    unenteredView.classList.add("hidden");
    messageForm = document.querySelector("#message-form-"+currentThread);
    messageForm.addEventListener('submit', (event)=>send(event), true);
    closeViewContainer = document.querySelector("#close-thread-"+currentThread+"-container");
    closeViewContainer.classList.remove('hidden');
    closeViewButton = document.querySelector("#close-thread-"+currentThread);
    closeViewButton.addEventListener('click', (event)=>closeCurrentThread(event));
    messageInput = document.querySelector("#message-input-"+currentThread);
    let formData = new FormData();
    formData.append('threadId', currentThread);
    formData.append('pageNum', pageNum);
    formData.append('numOfResults', numOfResults);

    fetch('/api/messages/load', {method: 'POST', body: formData})
        .then(response => response.json())
        .then(data => {
                returnedMessages = data['content'];
                console.log(returnedMessages);
                if(returnedMessages.length != 0){
                    for(let message of returnedMessages){
                        loadMessage(message['member'].fullName.toUpperCase(), message.messageContent, message.messageID, message.messageSent, false)
                    }
                }
                let socket = new SockJS('/ws');
                stompClient = Stomp.over(socket);
                stompClient.connect({}, () => onConnected(currentThread), onError);
            }
        )
    event.preventDefault();

}


function onConnected(room) {
    // Subscribe to the Public Topic
    stompClient.subscribe('/thread/'+room, onMessageReceived);
    // Tell your username to the server
    // stompClient.send("/app/chat.registerTo/"+room,
    //     {},
    //     JSON.stringify({member: userId, type: 'JOIN'})
    // )
    connectingElement = document.querySelector("#connecting-"+room);
    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Não foi possível se conectar ao WebSocket! Atualize a página e tente novamente ou entre em contato com o administrador.';
    connectingElement.style.color = 'red';
}


function send(event) {
    var messageContent = messageInput.value.trim();
    console.log("message content");
    console.log(messageContent);
    if(messageContent && stompClient) {
        var chatMessage = {
            member: userId,
            content: messageInput.value,
            type: 'CHAT',
            messageThread: currentThread
        };

        stompClient.send("/app/chat.sendTo/"+currentThread, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);
    loadMessage(user.toUpperCase(), message.content, message.messageId, message.messageSent, true);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function closeCurrentThread(){
    stompClient.disconnect();
    enteredArea.classList.add("hidden");
    unenteredView.classList.remove("hidden");
    closeViewContainer.classList.add('hidden');
    connectingElement.classList.remove('hidden');
    inAThread = false;
}


function loadMessage(name, content, id, sent, newMessage){
    let messageElement = document.createElement('tr');
    messageElement.classList.add('flex');
    messageElement.classList.add('flex-vertical');
    messageElement.classList.add('align-items-start');
    messageElement.classList.add('width-fill-parent');
    messageElement.classList.add('chat-message');
    var messageHeader = document.createElement('td');
    messageHeader.classList.add('message-header');
    messageHeader.classList.add('width-fill-parent');
    let usernameElement =document.createElement('span');
    usernameElement.setAttribute('class', 'user-name-span');
    let timeElement = document.createElement('span');
    timeElement.setAttribute('class', 'time-span');
    var usernameText = document.createTextNode(name.toUpperCase());
    let timeText = document.createTextNode(sent.substring(0, sent.indexOf('T')));
    usernameElement.appendChild(usernameText)
    timeElement.appendChild(timeText);
    messageHeader.appendChild(usernameElement);
    messageHeader.appendChild(timeElement);
    messageElement.appendChild(messageHeader);
    var textElement = document.createElement('td');
    var messageText = document.createTextNode(content);
    textElement.appendChild(messageText);
    textElement.classList.add('message-body');
    textElement.classList.add('width-fill-parent');

    messageElement.appendChild(textElement);

    if(newMessage){
         messageArea.appendChild(messageElement);
    } else{
        messageArea.insertBefore(messageElement, messageArea.firstChild);
    }
}