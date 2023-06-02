
function createReservationListener() {
    createEventListener(
        function(event) {
            const offerId = window.location.pathname.split("/").pop();
            if (event.userId === sessionStorage.getItem("user")) {
                document.getElementById('eventLogs').textContent += event.message + "\r\n";
                document.getElementById('actionResult').textContent = event.message;
            }
            else if (event.type === "multicast" && event.properties['offerId'] === offerId) {
                showNotification("Viewed offer has been \npurchased by " + event.userId).then();
            }
            console.log(event.properties);
            console.log(`[${event.type}] ${event.message}`);},
        function(error) {
            console.log(error);
        })
}

function buildOfferInfo(offerItem) {
    const offerDetailsContainer = document.getElementById('offerDetails');
    appendChildren(offerDetailsContainer, [
        labeledSquareProperty(0, 0, 100, 25, 2, 2, `${offerItem.country}`, {'class': 'svg-button', 'id': 'arrivalCountryInfo'}, "Country"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, `${offerItem.city}`, {'class': 'svg-button', 'id': 'arrivalCityInfo'}, "City"),
        labeledSquareProperty(0, 0, 20 + offerItem.hotel_name.length * 10, 25, 2, 2, `${offerItem.hotel_name}`, {'class': 'svg-button', 'id': 'hotelNameInfo'}, "Hotel name"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, `${offerItem.stars}`, {'class': 'svg-button', 'id': 'stars'}, "Stars"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, `${offerItem.start_date}`, {'class': 'svg-button', 'id': 'dateInfo'}, "Starting date"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, `${offerItem.end_date}`, {'class': 'svg-button', 'id': 'endDateInfo'}, "Ending date"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, `${offerItem.max_adults}`, {'class': 'svg-button', 'id': 'adultsInfo'}, "Adults"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, `${offerItem.max_children_to_3}`, {'class': 'svg-button', 'id': 'ppl3plusInfo'}, "People up to 3"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, `${offerItem.max_children_to_10}`, {'class': 'svg-button', 'id': 'ppl10plusInfo'}, "People up to 10"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, `${offerItem.max_children_to_18}`, {'class': 'svg-button', 'id': 'ppl18plusInfo'}, "People up to 18"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, `${offerItem.room_type}`, {'class': 'svg-button', 'id': 'room_type'}, "Room type"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, `${offerItem.meals}`, {'class': 'svg-button', 'id': 'meals'}, "Meals"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, `${offerItem.price}`, {'class': 'svg-button', 'id': 'price'}, "Price"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, `${offerItem.available}`, {'class': 'svg-button', 'id': 'availability'}, "Availability")
    ])
}

function buildSelectedFlightInfo(flightItem) {
    buildFlightInfo(flightItem)
    appendChildren(document.getElementById('flightDataDate'), [
        labeledSquareProperty(0, 0, 100, 25, 2, 2, `${flightItem.available_seats}`, {'class': 'svg-button', 'id': 'availableSeats'}, "Available seats")])
    setAttributes(document.getElementById("flightId"), {'value': flightItem.flightId});
    setAttributes(document.getElementById("flightArrivalCountry"), {'value': flightItem.arrival_country});
    setAttributes(document.getElementById("flightDate"), {'value': flightItem.date});
}

async function fetchOfferDetails() {
  const id = window.location.pathname.split("/").pop();
  await fetch(getEffectiveGatewayUri() + '/offers/' + id, {method: "GET"})
  .then(response => checkResponse(response))
  .then(offerItem => buildOfferInfo(offerItem))
  .catch(error => buildOfferInfo(defaultOfferItem));
}

async function fetchFlightDetails() {
  const flightId = getSearchRequestParams(['flightId'])['flightId'];
  await fetch(getEffectiveGatewayUri() + '/flights/' + flightId, {method: "GET"})
  .then(response => checkResponse(response))
  .then(flightItem => buildSelectedFlightInfo(flightItem))
  .catch(error => buildSelectedFlightInfo(defaultFlightItem));
}

async function reserveOffer() {
    const offerId = window.location.pathname.split("/").pop();
    const flightId = getSearchRequestParams(['flightId'])['flightId'];
    const userId = sessionStorage.getItem("user");
    const seatsNeeded = Number(document.getElementById('adultsInfo').getElementsByTagName('tspan')[0].textContent) +
                                Number(document.getElementById('ppl3plusInfo').getElementsByTagName('tspan')[0].textContent) +
                                Number(document.getElementById('ppl10plusInfo').getElementsByTagName('tspan')[0].textContent) +
                                Number(document.getElementById('ppl18plusInfo').getElementsByTagName('tspan')[0].textContent)
    const availableSeats = Number(document.getElementById('availableSeats').getElementsByTagName('tspan')[0].textContent)
    const price = Number(document.getElementById('price').getElementsByTagName('tspan')[0].textContent)
    if (isNaN(availableSeats) || isNaN(price)) {
        alert("Specified offer or flight does not exist");
        return;
    }
    if (!userId) {
        alert("Please log in to reserve")
        return;
    }
    await fetch(getEffectiveGatewayUri() + '/reservations' + `?userId=${userId}&offerId=${offerId}&flightId=${flightId}&isReserved=true&isCancelled=false`,{method: "GET"})
        .then(response => response.json())
        .then(response => {
            if (response.length !== 0) {
                if (response[0].isPaid === false) {
                    alert("Your reservation is already active");
                    throw new Error("Your reservation is already active");
                }
                alert("You already purchased this offer");
                throw new Error("You already purchased this offer");
            }
            if (seatsNeeded > availableSeats) {
                alert("Specified offer configuration cannot be reserved cause there is lack of available seats in a plane");
                throw new Error("Specified offer configuration cannot be reserved cause there is lack of available seats in a plane");
            }

            const reservation = {
                flightId: flightId,
                offerId: offerId,
                userId: userId,
                isExpired: false,
                price: price,
                seatsNeeded: seatsNeeded
            };

            return fetch(getEffectiveGatewayUri() + '/makereservation', {
                    method: "POST",
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(reservation)});
        })
        .then(response => response.text())
        .then(response => { alert("Reservation is processing"); })
        .catch(error => { document.getElementById('actionResult').textContent = error.message; });
}

async function purchaseOffer(status) {
    const offerId = window.location.pathname.split("/").pop();
    const flightId = getSearchRequestParams(['flightId'])['flightId'];
    const userId = sessionStorage.getItem("user");
    const seatsNeeded = Number(document.getElementById('adultsInfo').getElementsByTagName('tspan')[0].textContent) +
        Number(document.getElementById('ppl3plusInfo').getElementsByTagName('tspan')[0].textContent) +
        Number(document.getElementById('ppl10plusInfo').getElementsByTagName('tspan')[0].textContent) +
        Number(document.getElementById('ppl18plusInfo').getElementsByTagName('tspan')[0].textContent)
    const availableSeats = Number(document.getElementById('availableSeats').getElementsByTagName('tspan')[0].textContent)
    const price = Number(document.getElementById('price').getElementsByTagName('tspan')[0].textContent)
    if (isNaN(availableSeats) || isNaN(price)) {
        alert("Specified offer or flight does not exist");
        return;
    }
    if (!userId) {
        alert("Please log in to purchase")
        return;
    }
    await fetch(getEffectiveGatewayUri() + '/reservations' + `?userId=${userId}&offerId=${offerId}&flightId=${flightId}&isReserved=true&isCancelled=false`,{method: "GET"})
        .then(reservations => reservations.json())
        .then(reservations => {
            if (reservations.length !== 0) {
                if (reservations[0].isPaid === true) {
                    alert("You already purchased this offer");
                    throw new Error("You already purchased this offer");
                }
                return fetch(getEffectiveGatewayUri() + `/pay?reservationId=${reservations[0].reservationId}`, {method: "GET"})
                    .then(payments => payments.json())
                    .then(payments => {
                        console.log(payments)
                        return fetch(getEffectiveGatewayUri() + `/pay/${payments[0].paymentId}/${status}` , {
                            method: "PUT",
                            headers: {'Content-Type': 'application/json'}});
                    })
            }
            if (seatsNeeded > availableSeats) {
                alert("Specified offer configuration cannot be purchased cause there is lack of available seats in a plane");
                throw new Error("Specified offer configuration cannot be purchased cause there is lack of available seats in a plane");
            }
            // rezerwuje i place
            const payment = {
                userId: userId,
                offerId: offerId,
                flightId: flightId,
                isExpired: false,
                price: price,
                seatsNeeded: seatsNeeded
            };

            return fetch(getEffectiveGatewayUri() + `/pay/${status}` , {
                method: "POST",
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(payment)});
        })
        .then(response => response.text())
        .then(response => { alert("Purchase is processing"); })
        .catch(error => { document.getElementById('actionResult').textContent = error.message; });
}