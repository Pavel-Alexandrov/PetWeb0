package service;

import DAO.DailyReportDao;
import exceptions.DBException;
import model.DailyReport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private Long soldCars = 0L;

    private Long earnings = 0L;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            DailyReportDao dailyReportDao = new DailyReportDao(session);
            List<DailyReport> dailyReportList = dailyReportDao.getAllDailyReport();
            session.close();
            return dailyReportList;
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }

    public DailyReport getLastReport() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            DailyReportDao dailyReportDao = new DailyReportDao(session);
            DailyReport dailyReport = dailyReportDao.getLastReport();
            session.close();
            return dailyReport;
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }

    public void addSoldCar(Long carPrice) {
            this.soldCars += 1;
            this.earnings += carPrice;
    }

    public void drawUpReport() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            DailyReportDao dailyReportDao = new DailyReportDao(session);
            DailyReport dailyReport = new DailyReport(earnings, soldCars);
            dailyReportDao.addReport(dailyReport);
            this.earnings = 0L;
            this.soldCars = 0L;
            session.close();
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }

    public void delete() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            DailyReportDao dailyReportDao = new DailyReportDao(session);
            dailyReportDao.clean();
            session.close();
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }
}
