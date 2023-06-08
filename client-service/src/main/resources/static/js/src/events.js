function createEventListener(onmessage, onerror, group, userInfoBox= true) {
    const currentUser = sessionStorage.getItem("user");
    if (!currentUser && userInfoBox) {
        createUserInfoBox();
        return;
    }
    let eventSource = new EventSource(getEffectiveGatewayUri() + `/notifications/${group}/${currentUser}`);
    console.log("Connection opened");
    if (userInfoBox) {
        createUserInfoBox(function() {}, function() {eventSource.close(); console.log("Connection closed")});
    }
    eventSource.onmessage = (event) => {
        const eventObj = JSON.parse(event.data);
        onmessage(eventObj)
    };
    eventSource.onerror = (error) => {
        onerror(error)
    };
}

async function showNotification(message) {
    const notificationDiv = document.getElementById("notifications")
    notificationDiv.counter += 1;
    const listEntry = createElement('li', {"style": "color: transparent"});
    const notificationBox = squareFrame(0, 0, 200, 100, 2, 2, message,
        {"id": "notificationBox", "class": "notification appear", "style": "fill: lightgray"});
    appendChildren(listEntry, [notificationBox])
    appendChildren(notificationDiv, [listEntry]);
    await new Promise(r => setTimeout(r, 5000))
        .then(r => {
            listEntry.remove();
        })

}