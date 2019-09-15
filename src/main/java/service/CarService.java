package service;

import DAO.CarDao;
import exceptions.DBException;
import model.Car;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public List<Car> getAllCars() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            CarDao carDao = new CarDao(session);
            List<Car> carList = carDao.getAllCars();
            session.close();
            return carList;
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }


    public Car getCarIfExist(String brand, String model, String licensePlate) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            CarDao carDao = new CarDao(session);
            List<Car> carList = carDao.getCar(brand, model, licensePlate);
            Car car;
            switch (carList.size()) {
                case (0): car = null;
                break;
                case (1): car = carList.get(0);
                break;
                default: throw new DBException(new Exception());
            }
            session.close();
            return car;
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }

    public void removeCar(String brand, String model, String licensePlate) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            CarDao carDao = new CarDao(session);
            carDao.removeCar(brand, model, licensePlate);
            transaction.commit();
            session.close();
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }

    public Car getCar(String brand, String model, String licensePlate) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            CarDao carDao = new CarDao(session);
            List <Car> carList = carDao.getCar(brand, model, licensePlate);
            Car car;
            switch (carList.size()) {
                case (0): car = null;
                    break;
                case (1): car = carList.get(0);
                    break;
                default: throw new DBException(new Exception());
            }
            session.close();
            return car;
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }

    public boolean addCar(String brand, String model, String licensePlate, Long price) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            CarDao carDao = new CarDao(session);
            if (carDao.getCarsSameBrand(brand).size() == 10) {
                return false;
            }
            Car car = new Car(brand, model, licensePlate, price);
            carDao.addCar(car);
            transaction.commit();
            session.close();
            return true;
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }

    public void delete() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            CarDao carDao = new CarDao(session);
            carDao.clean();
            transaction.commit();
            session.close();
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }
}
