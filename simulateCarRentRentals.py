import asyncio
import random
import sys

numberOfCustomers      = int (sys.argv[1])
numberOfDaysToSimulate = int (sys.argv[2])
# Obviously, this is a simulation, so we are not going to be using real data
# And the following values are just for simulation purposes, and can be changed
# either by command line arguments or by changing the code directly
minDaysForRental    = 1
maxDaysForRental    = 15
totalSuvs = 5
totalSedans = 2
totalVans = 1
breakTime = random.randint(1, 50) # After processing this many customers, take a break

print (f"Simulating {numberOfCustomers} customers for {numberOfDaysToSimulate} days")

async def rentalReturn (cID, cT, dur):
    await asyncio.sleep(dur)
    # Simulate the time taken to process the return
    #print(f"\tCustomer {cID} return processing....")
    if cT == 'Sedan':
        global totalSedans
        totalSedans += 1
    elif cT == 'SUV':
        global totalSuvs
        totalSuvs += 1
    elif cT == 'Van':
        global totalVans
        totalVans += 1
    print(f"Customer {cID} has returned the {cT}. Total Sedans: {totalSedans}, SUVs: {totalSuvs}, Vans: {totalVans}")

async def main():
    tasks = []
    for eventEpoch in range(numberOfDaysToSimulate):
        global breakTime

        # A customer walks in for a rental request
        breakTime -= 1 # Decrement the break time

        customerID = random.randint(1, numberOfCustomers)
        global totalSuvs, totalSedans, totalVans
        carType = random.choice(['Sedan', 'SUV', 'Van'])
        if carType == 'Sedan':
            totalSedans -= 1
            if (totalSedans < 0):
                print(f"Ran out of Sedans for {customerID}, helping next customer...")
                totalSedans += 1  # Reset the count for next customer
                continue
        elif carType == 'SUV':
            totalSuvs -= 1
            if (totalSuvs < 0):
                print(f"Ran out of SUVs for {customerID}, helping next customer...")
                totalSuvs += 1  # Reset the count for next customer
                continue
        elif carType == 'Van':
            totalVans -= 1
            if (totalVans < 0):
                print(f"Ran out of Vans for {customerID}, helping next customer...")
                totalVans += 1  # Reset the count for next customer
                continue
            
        rentalDuration = random.randint(minDaysForRental, maxDaysForRental)
        task = asyncio.create_task(rentalReturn(customerID, carType, rentalDuration))
        tasks.append(task)
        print(f"Customer {customerID} is renting {carType} for a duration of {rentalDuration} days")

        if breakTime == 0: 
            print(f"---Taking a short break...")
            breakTime = random.randint(1, 50) # Reset the break time
            await asyncio.sleep(random.randint (1, 50))

    await asyncio.gather(*tasks)

if __name__ == "__main__":
    asyncio.run(main())
