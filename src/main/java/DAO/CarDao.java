package DAO;

import model.Car;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public List<Car> getAllCars() {
        Criteria criteria = session.createCriteria(Car.class);
        return criteria.list();
    }

    public void removeCar(String brand, String model, String licensePlate) {
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Car.class);
        Car car = (Car) criteria.add(Restrictions.eq("brand", brand))
                .add(Restrictions.eq("model", model))
                .add(Restrictions.eq("licensePlate", licensePlate))
                .uniqueResult();
        session.delete(car);
        transaction.commit();
    }

    public Car getCar(String brand, String model, String licensePlate) {
        Criteria criteria = session.createCriteria(Car.class);
        criteria.add(Restrictions.eq("brand", brand))
                .add(Restrictions.eq("model", model))
                .add(Restrictions.eq("licensePlate", licensePlate))
                .uniqueResult();
        return (Car)criteria.uniqueResult();
    }

    public List<Car> getCarsSameBrand(String brand) {
        Criteria criteria = session.createCriteria(Car.class);
        criteria.add(Restrictions.eq("brand", brand));
        return criteria.list();
    }

    public void addCar(Car car) {
        Transaction transaction = session.beginTransaction();
        session.save(car);
        transaction.commit();
    }

    public void clean() {
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Car.class);
        session.delete(criteria);
        transaction.commit();
    }
}
