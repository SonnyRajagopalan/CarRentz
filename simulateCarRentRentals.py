import asyncio
import random
import sys
import requests
import concurrent.futures
import CarRentzRestAPI as carRentzAPI

numberOfCustomers      = int (sys.argv[1])
numberOfDaysToSimulate = int (sys.argv[2])
# Obviously, this is a simulation, so we are not going to be using real data
# And the following values are just for simulation purposes, and can be changed
# either by command line arguments or by changing the code directly
minDaysForRental    = 1
maxDaysForRental    = 15
# This is now the SQL db "CarRentz"
# totalSuvs = 5
# totalSedans = 2
# totalVans = 1
breakTime = random.randint(1, 50) # After processing this many customers, take a break

print (f"Simulating {numberOfCustomers} customers for {numberOfDaysToSimulate} days")

async def rentalReturn (rID):
    loop = asyncio.get_event_loop()
    await asyncio.sleep(dur)
    # Simulate the time taken to process the return
    #print(f"\tCustomer {cID} return processing....")
    await loop.run_in_executor(None, carRentzAPI.returnARental(rID))
    print(f"Customer {cID} has returned the {cT}. Total Sedans: {totalSedans}, SUVs: {totalSuvs}, Vans: {totalVans}")

async def main():
    tasks = []
    
    for eventEpoch in range(numberOfDaysToSimulate):
        global breakTime
        loop = asyncio.get_event_loop()
        # A customer walks in for a rental request
        breakTime -= 1 # Decrement the break time

        customerID = random.randint(1, numberOfCustomers)
        global totalSuvs, totalSedans, totalVans
        carType = random.choice(['Sedan', 'SUV', 'Van'])
        try:

            car = await loop.run_in_executor(None, carRentzAPI.findAnAvailableCar(carType))
            if car is None:
                print(f"No available cars of type {carType} for customer {customerID}, helping next customer...")
                continue
        except Exception as e:
            print(f"An error occurred while finding an available car: {e}")
            continue
        carID = car['carID']    
        print (f"Customer {customerID} has requested a {carType} with ID {carID}")
        rentalDuration = random.randint(minDaysForRental, maxDaysForRental)
        try:
            rental = await loop.run_in_executor(None, carRentzAPI.rentACar(carType, carID, rentalDuration, customerID))
            if rental is None:
                print(f"Failed to rent a car for customer {customerID}, helping next customer...")
                continue
        except Exception as e:
            print(f"An error occurred while renting a car: {e}")
            continue

        print (rental)
        task = asyncio.create_task(rentalReturn(rental['rentalID']))

        tasks.append(task)
        print(f"Customer {customerID} is renting {carType} for a duration of {rentalDuration} days")

        if breakTime == 0: 
            print(f"---Taking a short break...")
            breakTime = random.randint(1, 50) # Reset the break time
            await asyncio.sleep(random.randint (1, 50))

    await asyncio.gather(*tasks)

if __name__ == "__main__":
    asyncio.run(main())
