async function fetchOfferDetails() {
  const id = window.location.pathname.split("/").pop();
  await fetch('http://localhost:8080/offers/' + id, {method: "GET"})
  .then(response => response.json())
  .then(offerItem => {
    const offerDetailsContainer = document.getElementById('offerDetails');
    appendChildren(offerDetailsContainer, [
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.country}`, {'class': 'svg-button', 'id': 'dstCountryInfo'}, "Country"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.city}`, {'class': 'svg-button', 'id': 'dstCityInfo'}, "City"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.name}`, {'class': 'svg-button', 'id': 'hotelNameInfo'}, "Hotel name"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.startDate}`, {'class': 'svg-button', 'id': 'startDateInfo'}, "Starting date"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.adults}`, {'class': 'svg-button', 'id': 'adultsInfo'}, "Adults"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.ppl3plus}`, {'class': 'svg-button', 'id': 'ppl3plusInfo'}, "Max people 3+"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.ppl10plus}`, {'class': 'svg-button', 'id': 'ppl10plusInfo'}, "Max people 10+"),
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${offerItem.ppl18plus}`, {'class': 'svg-button', 'id': 'ppl18plusInfo'}, "Max people 18+")
    ])

  });
}

async function fetchFlightDetails() {
  const flightId = getSearchRequestParams(['flightId'])['flightId'];
  await fetch('http://localhost:8080/flights/' + flightId, {method: "GET"})
  .then(response => response.json())
  .then(flightItem => {
        const flightDataFromContainer = document.getElementById('flightDataFrom');
        appendChildren(flightDataFromContainer, [
            createLabel("From", {'for': 'srcCountryInfo'}, 1),
            squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.srcCountry}`, {'class': 'svg-button', 'id': 'srcCountryInfo'}),
            squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.srcCity}`, {'class': 'svg-button', 'id': 'srcCityInfo'})
        ]);
        const flightDataToContainer = document.getElementById('flightDataTo');
        appendChildren(flightDataToContainer, [
            createLabel("To", {'for': 'dstCountryInfo'}, 1),
            squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.dstCountry}`, {'class': 'svg-button', 'id': 'dstCountryInfo'}),
            squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.dstCity}`, {'class': 'svg-button', 'id': 'dstCityInfo'})
        ]);
        const flightDataStartDateContainer = document.getElementById('flightDataStartDate');
        appendChildren(flightDataStartDateContainer, [
            labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${flightItem.startDate}`, {'class': 'svg-button', 'id': 'startDateInfo'}, "Flight date")
        ]);

        setAttributes(document.getElementById("flightId"), {'value': flightItem.id});
        setAttributes(document.getElementById("flightDstCountry"), {'value': flightItem.dstCountry});
        setAttributes(document.getElementById("flightStartDate"), {'value': flightItem.startDate});
    });
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