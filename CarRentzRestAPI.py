import requests
from http import HTTPStatus
import json
import asyncio
import random
import time
from datetime import datetime, timedelta

inventoryUrl = "http://localhost:8080/inventory"
rentalsUrl = "http://localhost:8080/rentals"
availableCarsUrl = "http://localhost:8080/rentals/availableByCarType"

def populateInventoryWithOneTypeOfCar (numberOfBranches, carType, number):
    url = inventoryUrl

    for i in range(number):
        color = random.choice (['Parrot Red', 'Blue Agate', 'Black', 'White', 'Burgundy', 'Steelblue', 'Green'])
        make = random.choice (['Toyota', 'Honda', 'Ford', 'Chevrolet', 'Nissan'])
        model = random.choice (['RAV4', 'CR-V', 'Escape', 'Equinox', 'Rogue'])
        currentBranchID = random.randint(1, numberOfBranches)
        milesDriven = random.randint(0, 10000)
        pricePerDay = random.randint(25, 50)
        year = random.randint(2022, 2025)
        data = {
            "available": True,
            "carType": carType,
            "color": color,
            "make": make,
            "model": model,
            "currentBranchID": currentBranchID,
            "milesDriven": milesDriven,
            "pricePerDay": pricePerDay,
            "year": year
        }

        response = requests.post(url, json=data)
        if response.status_code == HTTPStatus.CREATED:
            # print("Inventory initialized successfully:", response)
            continue
        else:
            print(f"Failed to initialize inventory {response.status_code}, {HTTPStatus.CREATED} - {response.text} for carType {carType} at branch {currentBranchID}")

def initializeInventory (numberOfBranches, numberOfSUVs, numberOfSedans, numberOfVans):
    
    populateInventoryWithOneTypeOfCar(numberOfBranches, "SUV", numberOfSUVs)
    populateInventoryWithOneTypeOfCar(numberOfBranches, "Sedan", numberOfSedans)
    populateInventoryWithOneTypeOfCar(numberOfBranches, "Van", numberOfVans)

def makeRequest (url, method, data=None):
    headers = { "Content-Type": "application/json" }
    if data is not None:
        headers["Content-Length"] = str(len(data))
    response = requests.request(method, url, headers=headers, json=data)

    if response.status_code == HTTPStatus.OK or response.status_code == HTTPStatus.CREATED:
        return response.json()
    else:
        # print(f"Error: {response.status_code} GETting URL {url}")
        return None

def findAnAvailableCar (day, branch, carType, duration, customerID, repID, delay=0):
    url = availableCarsUrl
    
    params = {
        "branchID": branch,  # Assuming a fixed branch ID for simplicity
        "carType": carType
    }

    time.sleep (delay)
    response = requests.get (url, params=params)

    # print (f"Response = {response.json()}")
    if response.status_code == HTTPStatus.OK:
        return response.json()  # Return the first available car
    else:
        # Push to RentalsUnavailable
        url = "http://localhost:8080/rentals/unavailable"
        data = {
            "carType": carType,
            "duration": duration,  # No specific car ID since none is available
            "rentalDate": (datetime.now()+timedelta(days=day)).isoformat(),
            "customerID": customerID,  # Assuming a fixed customer ID for simplicity
            "repID": repID,  # Assuming a fixed representative ID for simplicity
            "branchID": branch,  # Assuming a fixed branch ID for simplicity
            "reason": "No cars available of this type"
        }
        # print (f"Creating RentalUnavailable for carType {carType} for customer {customerID} at branch {branch} for duration {duration} days")
        response = requests.post(url, json=data)
        if response.status_code == HTTPStatus.CREATED:
            # print("RentalUnavailable created successfully:", response)
            return None
        else:
            print(f"Failed to create RentalUnavailable: {response.status_code} - {response.text}")
        return None

def rentACar (day, branch, rep, car, duration, customerID, delay=0):
    url = rentalsUrl
    time.sleep (delay)
    start = datetime.now() + timedelta (days=day)
    end = start + timedelta (days=duration)
    carID = car ['carID']
    expectedCharges = car ['expectedCharges'] * duration
    actualCharges = expectedCharges # Assuming actual charges are the same as expected for simplicity
    rentalBranchID = car ['rentalBranchID']
    repID = rep
    carType = car ['carType']

    data = {
        "carType": carType,
        "carID": carID,
        "repID": repID, # Assuming a fixed representative ID for simplicity
        "rentalBranchID": rentalBranchID, # Assuming a fixed branch ID for simplicity
        "duration": duration,
        "rentalDate": start.isoformat(),
        "returnDate": end.isoformat(),
        "expectedCharges": expectedCharges,
        "actualCharges": actualCharges,
        "customerID": customerID
    }
    response = requests.post (url, json=data)
    #print (response.status_code, response.text)
    #print (f"Sending rental request {data} to URL {url}")
    if response.status_code == HTTPStatus.CREATED:
        # print("Rental created successfully:", response)
        return response.json()
    else:
        print(f"Failed to create rental {data} for response: {response.status_code} - {response.text} at URL {response.url}")

    

def returnARental (rentalID, delay=0):
    url = rentalsUrl + "/" + str(rentalID)
    time.sleep (delay)
    # print ("Returning rental with ID:", rentalID, "using URL:", url)
    response = requests.delete(url)
    print (f"Response = {response.status_code}, {HTTPStatus.NOT_FOUND} {response.text}")
    if response.status_code == HTTPStatus.NOT_FOUND:
        # print("\tRental returned successfully:", response)
        return response
    else:
        print("Failed to return rental")
    return None

    
def deleteInventory ():
    url = inventoryUrl
    response = requests.delete(url)
    if response.status_code == HTTPStatus.NO_CONTENT:
        # print("Inventory deleted successfully")
        return response
    else:
        print(f"Failed to delete inventory: {response.status_code} - {response.text}")
    return None
