package com.amigoscode.car;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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

    @Override
    public Car selectCarById(Integer id) {
        return null;
    }

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


    @Override
    public int deleteCar(Integer id) {
        String sql = "DELETE FROM car WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updateCar(Integer id, Car update) {
        return 0;
    }
}
