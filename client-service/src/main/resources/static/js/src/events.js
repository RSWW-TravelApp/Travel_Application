function updateFlightInfo(properties) {
    const pickedFlightId = getSearchRequestParams(['flightId'])['flightId'];
    const changedFlightId = properties.flightId;
    if (changedFlightId !== pickedFlightId || changedFlightId === undefined) {
        return;
    }

    const helperDict = {
        'departure_country': 'departureCountryInfo', 'departure_city': 'departureCityInfo', 'available_seats': 'availableSeats',
        'arrival_country': 'arrivalCountryInfo', 'arrival_city': 'arrivalCityInfo', 'date': 'dateInfo'
    }
    for (const propertyKey in properties.changes) {
        if (!propertyKey in helperDict || document.getElementById(helperDict[propertyKey]) === null) {
            continue;
        }
        document.getElementById(helperDict[propertyKey]).getElementsByTagName('tspan')[0].textContent = properties.changes[propertyKey];
    }
}

function updateOfferInfo(properties) {
    const pickedOfferId = window.location.pathname.split("/").pop();
    const changedOfferId = properties.offerId;
    if (changedOfferId !== pickedOfferId || changedOfferId === undefined) {
        return;
    }

    const helperDict = {
        'country': 'arrivalCountryInfo', 'city': 'arrivalCityInfo', 'hotel_name': 'hotelNameInfo',
        'stars': 'Stars', 'start_date': 'dateInfo', 'end_date': 'endDateInfo', 'max_adults': 'adultsInfo',
        'max_children_to_3': 'ppl3plusInfo', 'max_children_to_10': 'ppl10plusInfo', 'max_children_to_18': 'ppl18plusInfo',
        'room_type': 'room_type', 'meals': 'meals', 'price': 'price', 'available': 'availability',
    }
    for (const propertyKey in properties.changes) {
        if (!propertyKey in helperDict || document.getElementById(helperDict[propertyKey]) === null) {
            continue;
        }
        document.getElementById(helperDict[propertyKey]).getElementsByTagName('tspan')[0].textContent = properties.changes[propertyKey];
    }
}

function getRandomId() {
    return Math.random().toString(36).substring(2, 16);
}

function createEventListener(onmessage, onerror, userInfoBox= true) {
    const currentUser = sessionStorage.getItem("user");
    let notLoggedUserId = sessionStorage.getItem("notLoggedUserId");
    if (notLoggedUserId === null) {
        notLoggedUserId = getRandomId();
        sessionStorage.setItem("notLoggedUserId", notLoggedUserId);
    }
    const effectiveUserId = currentUser !== null ? currentUser : notLoggedUserId;
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