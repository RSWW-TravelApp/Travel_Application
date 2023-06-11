function updateOfferPostCard(properties) {
    const changedOfferId = properties.offerId;
    const changedOfferPostCard = document.getElementById(changedOfferId);
    if (changedOfferPostCard === null || changedOfferId === undefined) {
        return;
    }
    const textLines = changedOfferPostCard.getElementsByTagName('tspan');
    const country = properties.changes['country'] !== undefined ? properties.changes['country'] : changedOfferPostCard.getAttribute('country');
    const city = properties.changes['city'] !== undefined ? properties.changes['city'] : changedOfferPostCard.getAttribute('city');
    const hotel_name = properties.changes['hotel_name'] !== undefined ? properties.changes['hotel_name'] : changedOfferPostCard.getAttribute('hotel_name');
    textLines[0].innerHTML = `${country}`;
    textLines[1].innerHTML = `${city}`;
    textLines[2].innerHTML = `${hotel_name}`;
}

async function createNotificationListener() {
    createEventListener(
        function(event) {
            const properties = event.properties;
            if (properties.groups === undefined || !properties.groups.includes("all") || event.type !== "multicast" ||
                properties.changes === undefined) {
                return;
            }

            if (properties.offerId !== undefined) {
                updateOfferPostCard(properties);
            } else if (properties.flightId !== undefined) {
                updateFlightInfo(properties);
                showNotification("Picked flight offer\nhas been modified");
            }
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
                const offerItem = createElement('div', {
                    'id': item.offerId,
                    'country': item.country,
                    'city': item.city,
                    'hotel_name': item.hotel_name
                });
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