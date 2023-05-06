
function createReservationListener() {
    const currentUser = sessionStorage.getItem("user");
    if (!currentUser) {
        createUserInfoBox();
        return;
    }
    eventSource = new EventSource(getEffectiveGatewayURI() + "/notifications/" + currentUser);
    console.log("Connection opened");
    createUserInfoBox(function() {}, function() {eventSource.close(); console.log("Connection closed")});
    eventSource.onmessage = (event) => {
    const result = document.getElementById('actionResult');
    if (event.data == "Reservation success") {
        result.textContent = event.data;
    } else if (event.data == "Reservation failed") {
        result.textContent = event.data;
    }
    else if (event.data == "Purchase success") {
        result.textContent = event.data;
    }
    else if (event.data == "Purchase failed") {
        result.textContent = event.data;
    }
    };
    eventSource.onerror = (error) => {
      console.log(error);
    };
}

function buildOfferInfo(offerItem) {
    const offerDetailsContainer = document.getElementById('offerDetails');
    appendChildren(offerDetailsContainer, [
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.country}`, {'class': 'svg-button', 'id': 'arrivalCountryInfo'}, "Country"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.city}`, {'class': 'svg-button', 'id': 'arrivalCityInfo'}, "City"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.hotel_name}`, {'class': 'svg-button', 'id': 'hotelNameInfo'}, "Hotel name"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.stars}`, {'class': 'svg-button', 'id': 'stars'}, "Stars"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.start_date}`, {'class': 'svg-button', 'id': 'dateInfo'}, "Starting date"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.max_adults}`, {'class': 'svg-button', 'id': 'adultsInfo'}, "Adults"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.max_children_to_3}`, {'class': 'svg-button', 'id': 'ppl3plusInfo'}, "People up to 3"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.max_children_to_10}`, {'class': 'svg-button', 'id': 'ppl10plusInfo'}, "People up to 10"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.max_children_to_18}`, {'class': 'svg-button', 'id': 'ppl18plusInfo'}, "People up to 18"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.room_type}`, {'class': 'svg-button', 'id': 'room_type'}, "Room type"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.meals}`, {'class': 'svg-button', 'id': 'meals'}, "Meals"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.price}`, {'class': 'svg-button', 'id': 'price'}, "Price")
    ])
}

function buildFlightInfo(flightItem) {
    const flightDataFromContainer = document.getElementById('flightDataFrom');
    appendChildren(flightDataFromContainer, [
        createLabel("From", {'for': 'departureCountryInfo'}, 1),
        squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.departure_country}`, {'class': 'svg-button', 'id': 'departureCountryInfo'}),
        squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.departure_city}`, {'class': 'svg-button', 'id': 'departureCityInfo'})
    ]);
    const flightDataToContainer = document.getElementById('flightDataTo');
    appendChildren(flightDataToContainer, [
        createLabel("To", {'for': 'arrivalCountryInfo'}, 1),
        squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.arrival_country}`, {'class': 'svg-button', 'id': 'arrivalCountryInfo'}),
        squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.arrival_city}`, {'class': 'svg-button', 'id': 'arrivalCityInfo'})
    ]);
    const flightDataDateContainer = document.getElementById('flightDataDate');
    appendChildren(flightDataDateContainer, [
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${flightItem.date}`, {'class': 'svg-button', 'id': 'dateInfo'}, "Flight date"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${flightItem.available_seats}`, {'class': 'svg-button', 'id': 'availableSeats'}, "Available seats")
    ]);

    setAttributes(document.getElementById("flightId"), {'value': flightItem.flightId});
    setAttributes(document.getElementById("flightArrivalCountry"), {'value': flightItem.arrival_country});
    setAttributes(document.getElementById("flightDate"), {'value': flightItem.date});
}

async function fetchOfferDetails() {
  const id = window.location.pathname.split("/").pop();
  await fetch(getEffectiveGatewayURI() + '/offers/' + id, {method: "GET"})
  .then(response => checkResponse(response))
  .then(offerItem => buildOfferInfo(offerItem))
  .catch(error => buildOfferInfo(defaultOfferItem));
}

async function fetchFlightDetails() {
  const flightId = getSearchRequestParams(['flightId'])['flightId'];
  await fetch(getEffectiveGatewayURI() + '/flights/' + flightId, {method: "GET"})
  .then(response => checkResponse(response))
  .then(flightItem => buildFlightInfo(flightItem))
  .catch(error => buildFlightInfo(defaultFlightItem));
}

async function reserveOffer() {
    const result = document.getElementById('actionResult');
    result.textContent = "";
    const offerId = window.location.pathname.split("/").pop();
    const flightId = getSearchRequestParams(['flightId'])['flightId'];
    const userId = sessionStorage.getItem("user");
    if (Number(document.getElementById('adultsInfo').getElementsByTagName('tspan')[0].textContent) +
        Number(document.getElementById('ppl3plusInfo').getElementsByTagName('tspan')[0].textContent) +
        Number(document.getElementById('ppl10plusInfo').getElementsByTagName('tspan')[0].textContent) +
        Number(document.getElementById('ppl18plusInfo').getElementsByTagName('tspan')[0].textContent) >
        Number(document.getElementById('availableSeats').getElementsByTagName('tspan')[0].textContent)) {
        alert("Specified offer configuration cannot be reserved cause there is lack of available seats in a plane");
        return;
    }
    if (!userId) {
        alert("Please log in to reserve")
        return;
    }
    if ([null, "", "null"].includes(offerId) || [null, "", "null"].includes(flightId)) {
        alert("Specified offer or flight does not exist");
        return;
    }
    result.textContent = "Do not leave this page";
    await fetch(getEffectiveGatewayURI() + '/reserve' + `/${offerId}/${flightId}`, {method: "POST"})
    .then(response => response.text())
    .then(response => {
        alert(response);
    });
}

async function purchaseOffer(status) {
    const result = document.getElementById('actionResult');
    result.textContent = "";
    const offerId = window.location.pathname.split("/").pop();
    const flightId = getSearchRequestParams(['flightId'])['flightId'];
    const userId = sessionStorage.getItem("user");
    if (Number(document.getElementById('adultsInfo').getElementsByTagName('tspan')[0].textContent) +
        Number(document.getElementById('ppl3plusInfo').getElementsByTagName('tspan')[0].textContent) +
        Number(document.getElementById('ppl10plusInfo').getElementsByTagName('tspan')[0].textContent) +
        Number(document.getElementById('ppl18plusInfo').getElementsByTagName('tspan')[0].textContent) >
        Number(document.getElementById('availableSeats').getElementsByTagName('tspan')[0].textContent)) {
        alert("Specified offer configuration cannot be purchased cause there is lack of available seats in a plane");
        return;
    }
    if (!userId) {
        alert("Please log in to purchase");
        return;
    }
    if ([null, "", "null"].includes(offerId) || [null, "", "null"].includes(flightId)) {
        alert("Specified offer or flight does not exist");
        return;
    }
    result.textContent = "Do not leave this page";
    await fetch(getEffectiveGatewayURI() + '/purchase' + `/${offerId}/${flightId}/${status}`, {method: "POST"})
    .then(response => response.text())
    .then(response => {
        alert(response);
    });
}