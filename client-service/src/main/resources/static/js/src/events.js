function createEventListener(onmessage, onerror) {
    const currentUser = sessionStorage.getItem("user");
    if (!currentUser) {
        createUserInfoBox();
        return;
    }
    let eventSource = new EventSource(getEffectiveGatewayUri() + "/notifications/" + currentUser);
    console.log("Connection opened");
    createUserInfoBox(function() {}, function() {eventSource.close(); console.log("Connection closed")});
    eventSource.onmessage = (event) => {
        const eventObj = JSON.parse(event.data);
        onmessage(eventObj)
    };
    eventSource.onerror = (error) => {
        onerror(error)
    };
}