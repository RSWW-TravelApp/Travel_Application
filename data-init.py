import requests
import json
import random
import uuid


def initFlights():
    master_url = "http://localhost:8085/flights"
    for i in range(200):
        dstCountry = random.choice(
            ["Poland", "Spain", "Germany", "USA", "France", "UK", "Belgium", "Netherlands", "Italy"])
        dstCity = random.choice(["Warsaw", "Madrid", "Berlin", "Washington", "London", "Brussels", "Amsterdam", "Roma"])
        srcCountry = random.choice(
            ["Poland", "Spain", "Germany", "USA", "France", "UK", "Belgium", "Netherlands", "Italy"])
        srcCity = random.choice(["Warsaw", "Madrid", "Berlin", "Washington", "London", "Brussels", "Amsterdam", "Roma"])
        date = random.choice(["2023-05-01", "2023-05-02"])
        payload = json.dumps({
            "flightId": uuid.uuid1().hex[:25],
            "departure_country": dstCountry,
            "departure_city": dstCity,
            "arrival_country": srcCountry,
            "arrival_city": srcCity,
            "available_seats": random.randint(5, 40),
            "date": date
        })
        headers = {
            'Content-Type': 'application/json'
        }

        response = requests.request("POST", master_url, headers=headers, data=payload)

        print(response.text)
        print("-------------------------------------------------------------------------------------------------------")


def initOffers():
    master_url = "http://localhost:8085/offers"
    for i in range(200):
        Country = random.choice(
            ["Poland", "Spain", "Germany", "USA", "France", "UK", "Belgium", "Netherlands", "Italy", "Greece"])
        City = random.choice(["Warsaw", "Madrid", "Berlin", "Washington", "London", "Brussels", "Amsterdam", "Roma"])
        HotelName = random.choice(["Sheraton", "Kisawa Residence", "Sommerro House", "Six Senses Israel",
                                   "Royal Mansour", "Hotel Château du Grand-Lucé", "Capella Ubud", "The Alpina Gstaad",
                                   "Grootbos Private Nature Reserve", "Amangiri", "Nihi Sumba", "Amanyara",
                                   "InterContinental Geneva"])
        startDate = random.choice(["2023-05-01", "2023-05-02"])
        endDate = random.choice(["2023-05-07", "2023-05-08"])
        payload = json.dumps({
            "offerId": uuid.uuid1().hex[:25],
            "hotel_name": HotelName,
            "image": "img1",
            "country": Country,
            "city": City,
            "stars": random.randint(1, 5),
            "start_date": startDate,
            "end_date": endDate,
            "room_type": random.choice(["small", "medium", "large", "apartment", "studio"]),
            "max_adults": random.randint(1, 5),
            "max_children_to_3": random.randint(0, 5),
            "max_children_to_10": random.randint(0, 5),
            "max_children_to_18": random.randint(0, 5),
            "meals": random.choice(["onlyBreakfast", "breakfastAndDinner", "allInclusive"]),
            "price": random.randint(100, 400),
            "available": True
        })
        headers = {
            'Content-Type': 'application/json'
        }

        response = requests.request("POST", master_url, headers=headers, data=payload)

        print(response.text)
        print("-------------------------------------------------------------------------------------------------------")


initFlights()
initOffers()
