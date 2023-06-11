async function fetchReservations(el) {
    const userId = sessionStorage.getItem("user");
    if (!userId) {
        alert("Please log in to check reservations")
        return;
    }
    await fetch(getEffectiveGatewayUri() + '/reservations/' + userId, {method: "GET"})
        .then(response => response.json())
        .then(data => {
            const listOfReservations = createElement('div');
            data.forEach(item => {
                const reservationItem = createElement('div', {
                    'id': item.reservationId,
                    'user_id': item.userId,
                    'offer_id': item.offerId,
                    'flight_id': item.flightId,
                    'is_paid': item.isPaid,
                    'is_cancelled': item.isCancelled
                });
                const form = createElement('form', {'action': `/offers/${item.offerId}?flightId=${item.flightId}`});
                const button = createElement('button', {
                    'name': 'flightId',
                    'value': item.flightId,
                    'style': "color: transparent; background-color: transparent; border-color: transparent; cursor: default;"
                });
                const postCard = squareFrame(0, 0, 300, 100, 2, 2, txt = `Resrvation ID: ${item.reservationId}\nOffer ID: ${item.offerId}\nFlight ID: ${item.flightId}\nPaid: ${item.isPaid}\nCancelled: ${item.isCancelled}`, {'class': 'svg-button'});
                appendChildren(button, [postCard]);
                appendChildren(form, [button]);
                appendChildren(reservationItem, [form]);
                appendChildren(listOfReservations, [reservationItem]);
            });
            const container = document.getElementById('container');
            appendChildren(container, [listOfReservations]);
        });
}