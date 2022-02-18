package com.amigoscode.car;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;


// THIS CLASS ONLY CONTAINS SQL QUERIES
// THIS IS THE CLASS THAT IS CONNECTED TO OUR DATABASE
// IT'S AN IMPLEMENTATION OF THE INTERFACE CLASS 'CARDAO' AND WE ARE CREATING METHOD BODIES FOR IT

@Repository("postgres")
public class CarDataAccessService implements CarDAO {

    private JdbcTemplate jdbcTemplate; //

    public CarDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // METHOD TO SELECT CARS BY ID
    @Override
    public Car selectCarById(Integer id) {
        // SQL QUERY
        String sql = """
                SELECT id, regnumber, brand, price
                FROM car
                WHERE id = ?
                """;

        // for loop - looping through List for select all cars
        for (int i = 0; i < selectAllCars().size(); i++) {
            // at each index i.e. each car, check the id of that car against the id that we pass through
            if (selectAllCars().get(i).getId().equals(id)) {
                // then return the car at the specific index
                return selectAllCars().get(i);

            }

        }

        // if not return null
        return null;
    }


    // ALTERNATIVE METHOD TO GET CARS BY ID

//    @Override
//    public Car selectCarById(Integer id) {
//        // todo: implement this method to get car by id from database

    //SQL QUERY
//        String sql = """
//                SELECT id, regnumber, brand, price
//                FROM car WHERE id = ?
//                """;
//
    // ROWMAPPER TO GET ALL THE ROWS
//        RowMapper<Car> carRowMapper = (rs, rowNum) -> {
//            return new Car(
//                    rs.getInt("id"),
//                    rs.getString("regnumber"),
//                    Brand.valueOf(rs.getString("brand")),
//                    rs.getDouble("price")
//            );
//        };
    // WE WANT A LIST OF OUR CAR OBJECTS FROM THE DATABASE
//        List<Car> cars = jdbcTemplate.query(sql, carRowMapper, id);
//
    // WANT TO LOOP THROUGH THIS LIST OF CARS, IF ITS EMPTY RETURN NULL
//        if (cars.isEmpty()){
//            return null;
//        } else {
    // IF NOT NULL THEN GET THAT CAR AND RETURN IT
//            return cars.get(0);
//        }
//
//    }




    // ----------------------------------------------------------------------

    // METHOD FOR SELECTING ALL CARS
    @Override
    public List<Car> selectAllCars() {
        // sql command
        String sql = """
                SELECT id, regnumber, brand, price 
                FROM car
                """;

        // rs - takes result set - advance through each result and get the value you want
        // rowNumb - takes row number

        RowMapper<Car> carRowMapper = (rs, rowNum) -> {
            Car car = new Car(
                    // calling the whole row and then going through it to get the id
                    rs.getInt("id"),
                    rs.getString("regnumber"),
                    // this is an enum so we need to convert the enum to a string
                    // so it get be retrieved from the row set, bec in the database it's a string
                    Brand.valueOf(rs.getString("brand")),
                    rs.getDouble("price")
            );
            return car;
        };

        //jdbcTemplate.query is allowing us to add, this is the function that's being executed
        List<Car> cars = jdbcTemplate.query(sql, carRowMapper);
        // returning our list of cars
        return cars;

    }

    // ----------------------------------------------------------------------

    // METHOD TO INSERT A CAR
    @Override
    public int insertCar(Car car) {
        String sql = """
                INSERT INTO car(regnumber, brand, price)
                VALUES(?, ?, ?)
                """;
        int rowsAffected = jdbcTemplate.update(
                sql,
                car.getRegNumber(),
                car.getBrand().name(),
                car.getPrice()
        );
        return rowsAffected;
    }

    // ----------------------------------------------------------------------

    @Override
    public int deleteCar(Integer id) {
        String sql = "DELETE FROM car WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // ----------------------------------------------------------------------

    @Override
    public int updateCar(Integer id, Car update) {
        String sql = """
                UPDATE car
                SET (regnumber, brand, price) =  (?, ?, ?)
                WHERE id = ?
                """;

        // creating a variable called rowsUpdated - implementing jdbcTemplate and then .update method
        int rowsUpdated = jdbcTemplate.update(
                sql,
                // updating each property for specific row
                update.getRegNumber(),
                update.getBrand().name(),
                update.getPrice(),
                // calling on the specific id passed
                id
        );
        // returning the variable above
        return rowsUpdated;

    }
}
