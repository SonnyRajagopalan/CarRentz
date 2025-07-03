import asyncio
import random
import sys
import CarRentzRestAPI as CarRentzRestAPI

numberOfCustomers      = int (sys.argv[1])
numberOfDaysToSimulate = int (sys.argv[2])
# Obviously, this is a simulation, so we are not going to be using real data
# And the following values are just for simulation purposes, and can be changed
# either by command line arguments or by changing the code directly
minDaysForRental    = 1
maxDaysForRental    = 25
breakTime = random.randint(1, 50) # After processing this many customers, take a break

print (f"Simulating {numberOfCustomers} customers for {numberOfDaysToSimulate} days")

async def rentalReturn (rID, dur):
    await asyncio.sleep(dur)
    response = CarRentzRestAPI.returnARental(rID)    
    print (f"Rental {rID} returned after {dur} days. Response: {response}")

async def main():
    tasks = []
    for eventEpoch in range(numberOfDaysToSimulate):
        # A customer walks in for a rental request highly opinionated on the car type they
        # want, and the duration of the rental
        global breakTime       
        breakTime -= 1 # Decrement the break time
        thisAsyncIterationOfLoop = asyncio.get_running_loop()

        customerID = random.randint(1, numberOfCustomers)
        carType = random.choice(['Sedan', 'SUV', 'Van'])
        rentalDuration = random.randint(minDaysForRental, maxDaysForRental)

        # Are cars available of the requested type?
        availableCar = await thisAsyncIterationOfLoop.run_in_executor(None, CarRentzRestAPI.findAnAvailableCar, carType)
        if availableCar is None:
            print(f"No available cars of type {carType} for customer {customerID}.")
            continue
        # Excellent--car available--now rent it
        carID = availableCar['carID']
        response = await thisAsyncIterationOfLoop.run_in_executor(None, CarRentzRestAPI.rentACar, carType, carID, rentalDuration, customerID)
        if response is None:
            print(f"Failed to rent car of type {carType} for customer {customerID}.")
            continue
        # Rental successful, now the customer has the car for the specified duration
        task = asyncio.create_task(rentalReturn(response['rentalID'], rentalDuration))
        tasks.append(task)
        print(f"Customer {customerID} is renting {carType} for a duration of {rentalDuration} days")

        if breakTime == 0: 
            print(f"---Taking a short break...")
            breakTime = random.randint(1, 50) # Reset the break time
            await asyncio.sleep(random.randint (1, 50))

    await asyncio.gather(*tasks) # Wait for all rental return tasks to complete

if __name__ == "__main__":
    asyncio.run(main())
