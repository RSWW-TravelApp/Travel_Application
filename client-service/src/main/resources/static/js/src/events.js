function getRandomId() {
    return Math.random().toString(36).substring(2, 16);
}

function createEventListener(onmessage, onerror, userInfoBox= true) {
    const currentUser = sessionStorage.getItem("user");
    const effectiveUserId = currentUser !== null ? currentUser : getRandomId();
    console.log("Getting event source...");
    let eventSource = new EventSource(getEffectiveGatewayUri() + "/notifications/" + effectiveUserId);
    console.log(`Connection opened for ${effectiveUserId}`);
    if (!currentUser && userInfoBox) {
        createUserInfoBox();
    } else if (userInfoBox) {
        createUserInfoBox(function() {},
            function() {
                            eventSource.close();
                            console.log("Connection closed");
                            createEventListener(onmessage, onerror, userInfoBox);
                        });
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