async function createNotificationListener() {
    createEventListener(
        function(event) {
            const properties = event.properties;
            const changedFlightId = properties.flightId;
            const changedFlightPostCard = document.getElementById(changedFlightId);
            if (properties.groups === undefined || !properties.groups.includes("all") || event.type !== "multicast" || properties.changes === undefined
                || changedFlightPostCard === null || changedFlightId === null) {
                return;
            }
            const textLines = changedFlightPostCard.getElementsByTagName('tspan');
            const departure_country = properties.changes['departure_country'] !== undefined ? properties.changes['departure_country'] : changedFlightPostCard.getAttribute('departure_country');
            const departure_city = properties.changes['departure_city'] !== undefined ? properties.changes['departure_city'] : changedFlightPostCard.getAttribute('departure_city');
            const arrival_country = properties.changes['arrival_country'] !== undefined ? properties.changes['arrival_country'] : changedFlightPostCard.getAttribute('arrival_country');
            const arrival_city = properties.changes['arrival_city'] !== undefined ? properties.changes['arrival_city'] : changedFlightPostCard.getAttribute('arrival_city');
            const available_seats = properties.changes['available_seats'] !== undefined ? properties.changes['available_seats'] : changedFlightPostCard.getAttribute('available_seats');
            textLines[0].innerHTML = `From: ${departure_country}, ${departure_city}`;
            textLines[1].innerHTML = `To: ${arrival_country}, ${arrival_city}`;
            textLines[2].innerHTML = `Available seats: ${available_seats}`;
            console.log(`[${event.type}] ${event.message}`);
        },
        function(error) {
            console.log(error);
        })
}

function createRecentChangesButton() {
    const recentChangesDiv = document.getElementById('recentChanges');
    const button = createElement('button', {
        'name': 'recentChangesButton',
        'id': 'recentChangesButton',
        'style': "color: transparent; background-color: transparent; border-color: transparent; cursor: default;"
    });
    button.onclick = function() {
        window.location.href = "/recentChanges";
    };
    const postCard = squareFrame(0, 0, 100, 50, 2, 2, `Check changes\nin data`, {'class': 'svg-button', 'id': 'recentChangesPostCard'});
    appendChildren(button, [postCard]);
    appendChildren(recentChangesDiv, [button]);
}

function createStatisticsButton() {
    const statisticsDiv = document.getElementById('statistics');
    const button = createElement('button', {
        'name': 'statisticsButton',
        'id': 'statisticsButton',
        'style': "color: transparent; background-color: transparent; border-color: transparent; cursor: default;"
    });
    button.onclick = function() {
        window.location.href = "/statistics";
    };
    const postCard = squareFrame(0, 0, 100, 50, 2, 2, `Check\nstatistics`, {'class': 'svg-button', 'id': 'statisticsPostCard'});
    appendChildren(button, [postCard]);
    appendChildren(statisticsDiv, [button]);
}

function createReservationsButton() {
    const reservationsDiv = document.getElementById('reservations');
    const button = createElement('button', {
        'name': 'reservationsButton',
        'id': 'reservationsButton',
        'style': "color: transparent; background-color: transparent; border-color: transparent; cursor: default;"
    });
    button.onclick = function() {
        window.location.href = "/reservations";
    };
    const postCard = squareFrame(0, 0, 100, 50, 2, 2, `Check your\nreservations`, {'class': 'svg-button', 'id': 'reservationsPostCard'});
    appendChildren(button, [postCard]);
    appendChildren(reservationsDiv, [button]);
}

async function fetchDestinations(el) {
    var queryParams = window.location.search;
    const numberOfPeople = getSearchRequestParams(['available_seats'])['available_seats'];
    if (!numberOfPeople) {
        queryParams += (queryParams === "" ? "?" : "&") + `available_seats=${document.getElementById('available_seats').value}`
    }
    await fetch(getEffectiveGatewayUri() + '/flights' + queryParams, {method: "GET"})
        .then(response => checkResponse(response))
        .then(data => {
            const listOfFlights = createElement('div');
            data.forEach(item => {
                const flightItem = createElement('div', {
                    'id': item.flightId,
                    'departure_country': item.departure_country,
                    'departure_city': item.departure_city,
                    'arrival_country': item.arrival_country,
                    'arrival_city': item.arrival_city,
                    'available_seats': item.available_seats
                })
                const form = createElement('form', {'action': '/offers'});
                const button = createElement('button', {
                    'name': 'flightId',
                    'value': item.flightId,
                    'style': "color: transparent; background-color: transparent; border-color: transparent; cursor: default;"
                });
                const arrivalCountryInput = createElement('input', {
                    'type': 'hidden',
                    'id': 'country',
                    'name': 'country',
                    'value': item.arrival_country
                });
                const departureCountryInput = createElement('input', {
                    'type': 'hidden',
                    'id': 'start_date',
                    'name': 'start_date',
                    'value': item.date
                });
                const numberOfPeopleInput = createElement('input', {
                    'type': 'hidden',
                    'id': 'max_adults',
                    'name': 'max_adults',
                    'value': document.getElementById('available_seats').value
                });
                const postCard = squareFrame(0, 0, 250, 100, 2, 2, `From: ${item.departure_country}, ${item.departure_city}\nTo: ${item.arrival_country}, ${item.arrival_city}\nDate: ${item.date}\nAvailable seats: ${item.available_seats}`, {'class': 'svg-button'});
                appendChildren(button, [postCard]);
                appendChildren(form, [button, arrivalCountryInput, departureCountryInput, numberOfPeopleInput]);
                appendChildren(flightItem, [form]);
                appendChildren(listOfFlights, [flightItem]);
            });
            const flightsCounter = document.getElementById('flightsCounter');
            flightsCounter.textContent = `${data.length} flights found`;
            const container = document.getElementById('container');
            appendChildren(container, [listOfFlights]);
        })
        .catch(error => {
            const flightsCounter = document.getElementById('flightsCounter');
            flightsCounter.textContent = "0 flights found";
        });
}