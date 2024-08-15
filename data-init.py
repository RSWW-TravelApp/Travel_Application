import requests
import json
import random
import uuid
from datetime import datetime, timedelta


def get_flight_data():
    return {
        "Poland": ["Warsaw", "Cracow"],
        "Spain": ["Madrid", "Barcelona"],
        "USA": ["Washington", "Los Angeles"],
        "France": ["Nice", "Paris"],
        "UK": ["London", "Manchester"],
        "Belgium": ["Brussels", "Bruges"],
        "Italy": ["Roma", "Milan"],
        "Netherlands": ["Amsterdam", "Rotterdam"],
    }


def get_hotel_data():
    return (
        ["Sheraton", "Kisawa Residence", "Sommerro House", "Six Senses Israel",
            "Royal Mansour", "Hotel Château du Grand-Lucé", "Capella Ubud", "The Alpina Gstaad",
            "Grootbos Private Nature Reserve", "Amangiri", "Nihi Sumba", "Amanyara",
            "InterContinental Geneva"],
        ["small", "medium", "large", "apartment", "studio"],
        ["onlyBreakfast", "breakfastAndDinner", "allInclusive"]
    )


def init_flights():
    travel_agency_endpoint = "http://localhost:8085/flights"
    data = get_flight_data()
    countries = list(data.keys())
    date_now = datetime.now()
    for i in range(200):
        src_country = random.choice(countries)
        src_city = random.choice(data[src_country])
        dst_country = random.choice(countries)
        dst_city = random.choice(data[dst_country])
        date = str((date_now + timedelta(days=random.randint(0, 60))).date())
        flight = {
            "flightId": uuid.uuid1().hex[:25],
            "arrival_country": src_country,
            "arrival_city": src_city,
            "departure_country": dst_country,
            "departure_city": dst_city,
            "available_seats": random.randint(5, 40),
            "date": date
        }
        headers = {
            'Content-Type': 'application/json'
        }
        response = requests.request("POST", travel_agency_endpoint, headers=headers, data=json.dumps(flight))
        print(response.text)


def init_offers():
    travel_agency_endpoint = "http://localhost:8085/offers"
    flight_data = get_flight_data()
    hotel_names, room_types, meals = get_hotel_data()
    countries = list(flight_data.keys())
    date_now = datetime.now()
    for i in range(1000):
        country = random.choice(countries)
        start_delta = random.randint(0, 60)
        end_delta = start_delta + random.randint(3, 14)
        offer = {
            "offerId": uuid.uuid1().hex[:25],
            "hotel_name": random.choice(hotel_names),
            "image": "img1",
            "country": country,
            "city": random.choice(flight_data[country]),
            "stars": random.randint(1, 5),
            "start_date": str((date_now + timedelta(days=start_delta)).date()),
            "end_date": str((date_now + timedelta(days=end_delta)).date()),
            "room_type": random.choice(room_types),
            "max_adults": random.randint(1, 5),
            "max_children_to_3": random.randint(0, 5),
            "max_children_to_10": random.randint(0, 5),
            "max_children_to_18": random.randint(0, 5),
            "meals": random.choice(meals),
            "price": random.randint(100, 400),
            "available": True
        }
        headers = {
            'Content-Type': 'application/json'
        }
        response = requests.request("POST", travel_agency_endpoint, headers=headers, data=json.dumps(offer))
        print(response.text)


if __name__ == "__main__":
    init_flights()
    init_offers()
