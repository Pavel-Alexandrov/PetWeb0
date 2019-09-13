package DAO;

import model.Car;
import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public List<Car> getAllCars() {
        Query query = session.createQuery("FROM Car");
        List<Car> carList = query.getResultList();
        return carList;
    }

    public void removeCar(String brand, String model, String licensePlate) {
        Query query = session.createQuery("DELETE Car WHERE (brand =: br AND model =: md AND licensePlate =: lp)");
        query.setParameter("br", brand);
        query.setParameter("md", model);
        query.setParameter("pl", licensePlate);
        query.executeUpdate();
    }

    public List<Car> getCar(String brand, String model, String licensePlate) {
        Query query = session.createQuery("FROM Car WHERE (brand =: br AND model =: md AND licensePlate =: lp)");
        query.setParameter("br", brand);
        query.setParameter("md", model);
        query.setParameter("pl", licensePlate);
        return query.getResultList();
    }

    public List<Car> getCarsSameBrand(String brand) {
        Query query = session.createQuery("FROM Car WHERE brand =: br");
        query.setParameter("br", brand);
        return query.getResultList();
    }

    public void addCar(String brand, String model, String licensePlate, Long price) {
        Query query = session.createQuery("INSERT INTO Car ()");
    }

    public void clean() {

    }
}
