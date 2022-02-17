package com.amigoscode.car;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("fake") // this is the annotation used for anything that wants to interact with the database
// () give it a name - use the name when making an instance of this implementation class in the Service layer
public class FakeCarDataAccessService implements CarDAO {

    private List<Car> db; // making a list of cars

    public FakeCarDataAccessService() {
        this.db = new ArrayList<>();
    }

    // METHOD TO SELECT CAR BY ID
    @Override
    // Car = car objects within out list
    // selectCarById = method name
    // Integer = we want to take in an Integer
    public Car selectCarById(Integer id) {
        for (Car carById : db) {
            if (id == carById.getId()) {
                return carById;
            }

        }
        return null;
    }

    // METHOD TO SELECT ALL CARS
    @Override
    public List<Car> selectAllCars() {
        return db;
    }


    //METHOD TO INSERT A CAR
    @Override
    public int insertCar(Car car) {
        db.add(car);
        return 1;
    }


    // METHOD TO DELETE A CAR
    @Override
    public int deleteCar(Integer id) {
        // getting the List of cars i.e. db
        //  implementing .remove function
        // implementing the 'selectCarById' method and getting an id which is in parenthesis
        // specifying which car by calling on the id
        db.remove(selectCarById(id));
        // return 1 - to indicate it's done
        return 1;
    }

    // METHOD TO UPDATE CAR
    @Override
    public int updateCar(Integer id, Car update) {

        // want to get the car only once
        // calling on the Car object and we want to get that specific car by its id
        Car car = selectCarById(id);

        // set the price for original car with the price of the updated car
        // update first then set the new price
        car.setPrice(update.getPrice());
        // set the Brand name for original car with the Brand name of the updated car
        car.setBrand(update.getBrand());
        // set the RegNumber for original car with the RegNumber of the updated car
        car.setRegNumber(update.getRegNumber());

        // then we will return 1 to indicate that this was completed
        return 1;
    }
}
