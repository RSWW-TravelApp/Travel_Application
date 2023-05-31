function createNotificationListener() {
    createEventListener(
        function(event) {
            console.log(`[${event.type}] ${event.message}`);},
        function(error) {
            console.log(error);
        })
}


async function fetchOffers(el) {
  const flightId = getSearchRequestParams(['flightId'])['flightId'];
  await fetch(getEffectiveGatewayUri() + '/offers' + window.location.search, {method: "GET"})
  .then(response => response.json())
  .then(data => {
    const listOfOffers = createElement('div');
    data.forEach(item => {
                const offerItem = createElement('div', {'id': item.offerId});
                const form = createElement('form', {'action': `/offers/${item.offerId}`});
                const button = createElement('button', {
                    'name': 'flightId',
                    'value': flightId,
                    'style': "color: transparent; background-color: transparent; border-color: transparent; cursor: default;"
                });
                const postCard = squareFrame(0, 0, 250, 100, 2, 2, txt=`${item.country}\n${item.city}\n${item.hotel_name}`, {'class': 'svg-button'});

                appendChildren(button, [postCard]);
                appendChildren(form, [button]);
                appendChildren(offerItem, [form]);
                appendChildren(listOfOffers, [offerItem]);
              });
    const offersCounter = document.getElementById('offersCounter');
    offersCounter.textContent = `${data.length} offers found`;
    const container = document.getElementById('container');
    appendChildren(container, [listOfOffers]);
  });
}

async function fetchChosenFlight(el) {
    const flightId = getSearchRequestParams(['flightId'])['flightId'];
    await fetch(getEffectiveGatewayUri() + '/flights/' + flightId, {method: "GET"})
        .then(response => checkResponse(response))
        .then(flightItem => buildFlightInfo(flightItem))
        .catch(error => buildFlightInfo(defaultFlightItem));
}