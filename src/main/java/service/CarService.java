package service;

import DAO.CarDao;
import exceptions.DBException;
import model.Car;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
            Car car = carDao.getCar(brand, model, licensePlate);
            session.close();
            return car;
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }

    public void removeCar(String brand, String model, String licensePlate) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            CarDao carDao = new CarDao(session);
            carDao.removeCar(brand, model, licensePlate);
            session.close();
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }

    //зачем мне этот метод?
    public Car getCar(String brand, String model, String licensePlate) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            CarDao carDao = new CarDao(session);
            Car car = carDao.getCar(brand, model, licensePlate);
            session.close();
            return car;
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }

    public boolean addCar(String brand, String model, String licensePlate, Long price) throws DBException {
        try {
            Session session = sessionFactory.openSession();

            CarDao carDao = new CarDao(session);
            //проверяю, сколько машин внесено в бд, почему то ноль всегда (для дебага)
            int cars = getAllCars().size();

            int carsCount = carDao.getCarsSameBrand(brand).size();
            if (carsCount >= 10) {
                return false;
            }
            Car car = new Car(brand, model, licensePlate, price);
            carDao.addCar(car);
            session.close();
            return true;
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }

    public void delete() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            CarDao carDao = new CarDao(session);
            carDao.clean();
            session.close();
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }
}
