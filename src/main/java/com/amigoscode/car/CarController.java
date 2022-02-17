package com.amigoscode.car;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {

    // MAKING AN INSTANCE OF THE 'CARSERVICE' CLASS
    private CarService carService;

    // CONSTRUCTOR FOR CAR CONTROLLER CLASS
    public CarController(CarService carService) {
        this.carService = carService;
    }

    // METHOD FOR A POST REQUEST - TO ADD AN NEW CAR TO THE CAR LIST

    //@PostMapping because we want to be able to carry out a post request
    // path is what comes after localhost:8080 --> localhost:8080/cars
    @PostMapping(path = "cars")
    // 'addCar' method
    // @RequestBody converts incoming JSON to a domain object, from the post body because we've included this annotation
    public void addCar(@RequestBody Car car) {
        // calling on the carService class which consists of the registerNewCar method
        // and adding the car object by putting the placeholder in ()
        carService.registerNewCar(car);
    }

    // METHOD FOR GET REQUEST - TO GET A LIST OF THE CARS

    //@GetMapping is the annotation for this
    @GetMapping(path = "cars")
    // we want to get all the cars so we will implement a List here
    public List<Car> getCars() {
        // this is returning all the cars by calling on the 'carService' '.getcars' method
        return carService.getCars();
    }

    // METHOD FOR GET REQUEST - TO GET A CAR BY THE ID SPECIFICALLY

    // path is slightly different --> localhost:8080/cars/id - because we want to retrieve the cars by the specific id of said car
    @GetMapping(path = "cars/{id}")
    // calling on the Car object
    //@PathVariable is bringing the values from the URL into our method's argument
    // we want to get that
    public Car getCarById(@PathVariable("id") Integer carId) {
        // returning the selected car by calling on the 'carService' class and the 'selectCarById' method for the 'carId' entered
        return carService.selectCarById(carId);
    }


    // METHOD THAT ENABLES THE DELETE REQUEST - TO DELETE A CAR

    // @DeleteMapping is required
    // different path
    @DeleteMapping(path = "cars/{id}")
    // method called 'deleteCarById
    // @PathVariable getting the values from URL
    // Integer carId - Integer is what we are accepting - 'carId' is the placeholder
    public void deleteCarById(@PathVariable("id") Integer carId) {

        // want to call on the 'carService' call (don't need add 'return' because we're not looking to get anything from the 'database')
        // .deleteCar because that's the method
        // carId because we want to delete a specific car based on the id inputed
        carService.deleteCar(carId);
    }

    // METHOD THAT RESPONDS TO PUT REQUEST - TO PUT NEW DETAILS TO CAR OBJECT I.E UPDATING IT

    //@PutMapping is needed
    @PutMapping(path = "cars/{id}")
    public void updateCar(@PathVariable("id") Integer carId, @RequestBody Car update) {
        // this is going to replace an object with the given id, new object details
        carService.updateCar(carId, update);

    }
}
