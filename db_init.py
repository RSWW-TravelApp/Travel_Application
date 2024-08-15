import argparse
from pymongo import MongoClient


def parse_args():
    parser = argparse.ArgumentParser(description="Data initializer")
    parser.add_argument("--ip", required=True, help="IP address of the MongoDB server")
    parser.add_argument("--port", default=27017, type=int, help="Port number of the MongoDB server")
    return parser.parse_args()


def create_db_and_collections(client):
    db_collection_pairs = [
        ("bills", ["payments"]),
        ("flights", ["flights"]),
        ("hotels", ["offers"]),
        ("reservations", ["bookings"]),
        ("reservations_master", ["bookings", "bookings_master"]),
        ("travel_agency", ["flights", "offers"]),
    ]

    for db_key, collection_keys in db_collection_pairs:
        db = client[db_key]
        for collection_key in collection_keys:
            if collection_key in db.list_collection_names():
                print(f"Collection {collection_key} in {db_key} database already exists")
            else:
                db.create_collection(collection_key)
                print(f"Created collection {collection_key} in {db_key} database")


if __name__ == "__main__":
    args = parse_args()
    mongo_client = MongoClient(f"mongodb://{args.ip}:{args.port}")
    create_db_and_collections(mongo_client)
