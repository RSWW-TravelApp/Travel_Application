//sanity check
print("################################################INITIALIZING DATABASE########################################")

// Create DB and collection
db = db.getSiblingDB('SI_180268')

// db.createUser({
//     user: 'root',
//     pwd: 'student',
//     roles: [
//         {
//             role: 'readWrite',
//             db: 'SI_180268',
//         },
//     ],
// });


db.createCollection('offers', { capped: false });       // READ ONLY
db.offers.insertOne({
    offerId: "0",
    hotelName: "Hilton",
    image: "httpshthhpthp.com",
    stars: 4,
    country: "Bułgaria",
    city: "Gdynia",
    start_date: new Date("2023-06-12"),
    end_date: new Date("2023-06-20"),
    max_adults: 5,
    max_children_to_3: 2,
    max_children_to_10: 0,
    max_children_to_18: 5,
    meals: "breakfast_lunch",
    price: 1.00,
    room_type: "Villa",
    available: true
});

db.createCollection('flights', { capped: false });              // READ ONLY
db.flights.insertOne({
    flightId: "0",
    departure_country: "Polska",
    departure_city: "Sopot",
    arrival_country: "Turcja",
    arrival_city: "Oczko",
    date: new Date("2023-02-12"),
    available_seats: 5
});

db.createCollection('payments', { capped: false });                 // CRUD

db.createCollection('bookings', { capped: false });                 // READ ONLY
db.bookings.insertOne({
    reservationId: "0",
    userId: "1",
    offerId: "0",
    flightId: "0",
    paymentId: "0",
    price: 25.0,
    isPaid: true,
    isCancelled: false,
    travellers: 5,
    isReserved: true
});

db.createCollection('bookings_master', { capped: false });          // CRUD
db.createCollection('offers_travel_agency', { capped: false });     // CRUD
db.createCollection('flights_travel_agency', { capped: false });    // CRUD


// db.createCollection('offersNested', { capped: false });
// db.createCollection('flightsNested', { capped: false });
// db.createCollection('bookingsNested', { capped: false });


print("################################################END OF THE INITIALIZATION########################################")