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
let connectingElement = document.querySelector(".connecting");
let messageInput = document.querySelector(".message-input");
let messageArea;
let messageForm;


function viewThread(event){

    currentThread = event.currentTarget.getAttribute('id').split("-")[0];
    console.log(currentThread);
    messageArea = document.querySelector("#message-area-"+currentThread);
    enteredArea = document.querySelector("#entered-view-"+currentThread);
    enteredArea.classList.remove("hidden");
    unenteredView = document.querySelector("#unentered-view-"+currentThread);
    unenteredView.classList.add("hidden");
    messageForm = document.querySelector("#message-form-"+currentThread);
    messageForm.addEventListener('submit', send, true);

    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);

    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.register",
        {},
        JSON.stringify({sender: userId, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Não foi possível se conectar ao WebSocket! Atualize a página e tente novamente ou entre em contato com o administrador.';
    connectingElement.style.color = 'red';
}


function send(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {
            member: userId,
            messageContent: messageInput.value,
            type: 'CHAT',
            messageThread: currentThread
        };

        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);

    let messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.member + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.member + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.member);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.messageContent);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

