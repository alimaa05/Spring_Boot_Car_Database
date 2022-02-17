package com.amigoscode.car;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

// BUSINESS LOGIC CLASS WILL CONSIST OF THROWING EXCEPTIONS
// WE WANT TO CHECK WHETHER WE CAN RUN THE METHOD BEING CALLED FROM THE IMPLEMENTATION CLASS
// IF IT CAN'T RUN THEN WE WANT TO THROW AN EXCEPTION

//auto-wire or inject class service which then allows you to make other dependencies
//this is a more specific annotation for the Service Class
@Service
public class CarService {

    // calling the interface (so if we want to use a specific implementation we can change it more easily)
    // can switch out how our application stores data
    private CarDAO carDAO;

    // THIS IS A CONSTRUCTOR
    // @Qualifier - so we know which implementation of the interface we want to use
    // dependency injection - this class needs the 'CarDAO' class, so it now has dependency on the carDAO
    // within parameters we put the name of implementation class...
    // which  is found within the heap, and we want to create a dependency on it
    public CarService(@Qualifier("postgres") CarDAO carDAO) {
        // this is referring to the CarDAO in the heap
        this.carDAO = carDAO;
    }


    // INTERNAL PRIVATE METHOD FOR US TO REUSE TO CHECK IF CAR EXISTS OR NOT
    // if it doesn't exist then we will throw an exception
    private Car getCarOrThrow(Integer id) {
        Car car = carDAO.selectCarById(id);
        if (car == null) {
            throw new IllegalStateException("Error. Car does not exist in database");
        }
        return car;
    }


    // METHOD TO ADD A NEW CAR
    public void registerNewCar(Car car) {
        // business logic. check if reg number is valid and not take
        // Checking whether the price of the car is less the 0 if it is, it will throw an exception
        if (car.getPrice() <= 0) {
            throw new IllegalStateException("Car price cannot be 0 or less");
        }
        // creating a new variable called 'result' which is calling on the 'carDAO' class
        // then the method 'insertCar' and the car object
        int result = carDAO.insertCar(car);

        // if it's not equal to 1 then it couldn't be saved
        if (result != 1) {
            throw new IllegalStateException("Could not save car...");
        }
    }

    // METHOD FOR RETURNING A LIST OF ALL THE CARS
    public List<Car> getCars() {
        return carDAO.selectAllCars();
    }


    // METHOD FOR GETTING CAR BY ID

    // return the Car object - method name - Integer is what we are accepting
    public Car selectCarById(Integer carId) {

        // Checking whether a car already exists - if it doesn't exist it will throw an exception
        Car car = getCarOrThrow(carId);

        // calling cars object - calling the 'carDAO' class, then the 'selectCarById' method
        Car cars = carDAO.selectCarById(carId);

// this was done before creating the internal method that checks whether car exists which we just called on, on line 72
//        if (car == null) {
//            throw new IllegalStateException(" This car does not exist");
//        }

        // calling on the carDAO, then the method within the implementation class, parenthesis calling on the placeholder
        return cars;
    }


    // METHOD FOR DELETING A CAR

    // void because we don't need to return anything
    // Integer because we want to be able to take an int
    public void deleteCar(Integer delete) {

        // this method is checking whether the car exists
        Car car = getCarOrThrow(delete);

        // this method is saying we can't delete a car if it doesn't exist i.e. if it's not equal to 1
        // if the id is equal to null then car doesnt exist and we can't delete it
        if (carDAO.deleteCar(delete) != 1) {
            // if that's the case then it will throw this exception
            throw new IllegalStateException("Error. couldn't delete the car");
        }

        carDAO.deleteCar(delete);

    }

    public void updateCar(Integer id, Car update) {
        // check if the car exists first - if it doesn't we want to throw an error
        Car car = getCarOrThrow(id);

        // if it does exist then we can update the car
        // want to create a new variable i.e. 'result' and that's equal to calling the 'carDAO' class and the 'updateCar' method
        int result = carDAO.updateCar(id, update);
        // we want to then use that variable to see whether it's not equal to 1 - if it's not equal to 1 then it couldn't be updated
        if (result != 1) {
            throw new IllegalStateException("Sorry, car could not be updated, please check car id");
        }

    }


}
