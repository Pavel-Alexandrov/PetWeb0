package DAO;

import model.Car;
import org.hibernate.Session;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public List<Car> getAllCars() {

    }

    public void removeCar(String brand, String model, String licensePlate) {

    }

    public Long getCarPrice() {

    }

    public Car getCar() {

    }

    public boolean checkCarExistence(String brand, String model, String licensePlate) {

    }

    public List<Car> getCarsSameBrand(String brand) {

    }

    public void addCar() {

    }

    public void clean() {

    }
}
