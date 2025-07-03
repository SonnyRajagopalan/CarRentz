# Epoch management in CarRentz, a Car Rental simulation

## Work in progress document!

## See carRentzSim.py for rentals done using the discussion in this markdown.

## Introduction

We won't be running our simulation for days. Maybe we will, but if we do, it is for simulating _years_ of car rental in CarRentz. So time flies faster in our simulation, which is expected.

But how do we model it? The real world's cadence results in particular patterns in rentals, and it would be useful to map our simulation as close to it as possible.

## Design 
### One second = 1 day
We map 1 hour in the real world to 1 second of simulation. If we assume rentals are only happening at the car rental center -- there is one big office -- what does this assumption mean? 

1. Waiting for a rep to talk to us--typically 5 min ~= (equivalent to) 5/(60*24)sec in simulation time:  `time.sleep(0.0034)` in python. However, some customers don't wait at all. This can be modeled randomly.
2. The rep has spotted us and now is working with us to get us a car. This usually takes 20 minutes on average to do as it involves adding you to the system if you are a new customer, and then checking what you will need against their inventory. This is `time.sleep(0.014)` in python.
3. Reps are people and people need breaks. Assume every rep takes a break every 2 hours _or so_. For 10 minutes in the morning, 30 min for lunch, and then 10 min in the afternoon, and the last 2 hours later, they go home. So, break every `(0.0833)` seconds of simulation and  `time.sleep(0.0068)` simulation seconds.
4. Most reps work 8-hour days. And possibly work the weekend. The rest of the day, they are doing things humans do--eat, sleep, meet friends etc. So, a simulation should do useful stuff only `1/3` of a simulation second. So each simulation second, there is a `time.sleep(0.66)`.
5. Car rentals are always by the number of days. In our simulation, this is a second. A `time.sleep (duration)` works well to simulate this in seconds.

#### A full day, in simulation looks like this (approx) :
````
a. Do five times: see a customer for a time that is in random.uniform (0.014, 0.17)
b. break for time.sleep (0.0068) seconds
c. Do five times: see a cusomer for a time that is in random.uniform (0.014, 0.17)
d. break for time.sleep (0.021) seconds
e. Do five times: see a customer for a time that is in random.uniform (0.014, 0.017)
f. Do 4 times: see a customer for a time that is in random.uniform (0.014, 0.017)
g. Do nothing in that simulation second until the next day (epoch)
````

##### Notes:
1. We assume there are always customers renting, this is not the case.
2. We assume that there are no holidays for the customers, this is not the case.
3. We assume that the rep station will be staffed with only one person, this is not the case.
4. We've subsumed and amortized the customer wait time into the time the rep spends with them.
5. We assumed there is only one branch in the simulation, which won't be the case.
6. We assumed there is no online presence, which is also not usually the case.
7. In reality, some customers don't spend more than 5 min at the car rental company's location, but others spend several hours. This is not modeled.

### Algorithm

From the information above, model the day as 4-2 hour periods, each of which look like this: morning, mid-morning, afternoon, last

* Morning sessoin is 110 minutes, with a 10-min break (5 customers)
* Mid-morning session is 80 minutes, with a 40-min break (4 customers)
* Afternoon session is 110 min, with a 10-min break (6 customers)
* Late afternoon session is 110 min (see lunch break above) (5 customers)

````

def seeNCustomers (n):
    do the thing!

for d in range(totalNumberOfDaysToSimulate):
    // Morning
    seeNCustomers (5)
    // Mid-morning
    seeNCustomers (4)
    // Afternoon
    seeNCustomers (6)
    // Late afternoon
    seeNCustomers (4)

    time.sleep (0.67) // seconds, which is really the rest of the day
    
````