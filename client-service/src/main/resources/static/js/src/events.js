function createEventListener(onmessage, onerror, userInfoBox= true) {
    const currentUser = sessionStorage.getItem("user");
    if (!currentUser && userInfoBox) {
        createUserInfoBox();
        return;
    }
    let eventSource = new EventSource(getEffectiveGatewayUri() + "/notifications/" + currentUser);
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