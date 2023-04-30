function buildOfferInfo(offerItem) {
    const offerDetailsContainer = document.getElementById('offerDetails');
    appendChildren(offerDetailsContainer, [
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.country}`, {'class': 'svg-button', 'id': 'arrivalCountryInfo'}, "Country"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.city}`, {'class': 'svg-button', 'id': 'arrivalCityInfo'}, "City"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.hotel_name}`, {'class': 'svg-button', 'id': 'hotelNameInfo'}, "Hotel name"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.start_date}`, {'class': 'svg-button', 'id': 'dateInfo'}, "Starting date"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.max_adults}`, {'class': 'svg-button', 'id': 'adultsInfo'}, "Adults"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.max_children_to_3}`, {'class': 'svg-button', 'id': 'ppl3plusInfo'}, "Max people 3+"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.max_children_to_10}`, {'class': 'svg-button', 'id': 'ppl10plusInfo'}, "Max people 10+"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.max_children_to_18}`, {'class': 'svg-button', 'id': 'ppl18plusInfo'}, "Max people 18+")
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
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${flightItem.date}`, {'class': 'svg-button', 'id': 'dateInfo'}, "Flight date")
    ]);

    setAttributes(document.getElementById("flightId"), {'value': flightItem.flightId});
    setAttributes(document.getElementById("flightArrivalCountry"), {'value': flightItem.arrival_country});
    setAttributes(document.getElementById("flightDate"), {'value': flightItem.date});
}

async function fetchOfferDetails() {
  const id = window.location.pathname.split("/").pop();
  await fetch('http://localhost:8080/offers/' + id, {method: "GET"})
  .then(response => checkResponse(response))
  .then(offerItem => buildOfferInfo(offerItem))
  .catch(error => buildOfferInfo(defaultOfferItem));
}

async function fetchFlightDetails() {
  const flightId = getSearchRequestParams(['flightId'])['flightId'];
  await fetch('http://localhost:8080/flights/' + flightId, {method: "GET"})
  .then(response => checkResponse(response))
  .then(flightItem => buildFlightInfo(flightItem))
  .catch(error => buildFlightInfo(defaultFlightItem));
}

async function reserveOffer() {
    const offerId = window.location.pathname.split("/").pop();
    const flightId = getSearchRequestParams(['flightId'])['flightId'];
    if ([null, "", "null"].includes(offerId) || [null, "", "null"].includes(flightId)) {
            alert("Specified offer or flight does not exist");
            return;
    }
    await fetch('http://localhost:8080/reserve' + `/${offerId}/${flightId}`, {method: "POST"})
    .then(response => response.text())
    .then(response => {
        alert(response);
    });
}

async function purchaseOffer() {
    const offerId = window.location.pathname.split("/").pop();
    const flightId = getSearchRequestParams(['flightId'])['flightId'];
    if ([null, "", "null"].includes(offerId) || [null, "", "null"].includes(flightId)) {
        alert("Specified offer or flight does not exist");
        return;
    }
    await fetch('http://localhost:8080/purchase' + `/${offerId}/${flightId}`, {method: "POST"})
    .then(response => response.text())
    .then(response => {
        alert(response);
    });
}