package service;

import DAO.DailyReportDao;
import exceptions.DBException;
import model.DailyReport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private long soldCars = 0;

    private long earnings = 0;

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
            List<DailyReport> dailyReportList = dailyReportDao.getLastReport();
            DailyReport dailyReport;
            if (dailyReportList.size() == 1) {
                dailyReport = dailyReportList.get(0);
            } else {
                throw new DBException(new Exception());
            }
            session.close();
            return dailyReport;
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }

    public void addSoldCar(Long carPrice) {
            this.soldCars++;
            this.earnings += carPrice;
    }

    public void drawUpReport() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            DailyReportDao dailyReportDao = new DailyReportDao(session);
            DailyReport dailyReport = new DailyReport(earnings, soldCars);
            dailyReportDao.addReport(dailyReport);
            this.earnings = 0;
            this.soldCars = 0;
            transaction.commit();
            session.close();
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }

    public void delete() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            DailyReportDao dailyReportDao = new DailyReportDao(session);
            dailyReportDao.clean();
            transaction.commit();
            session.close();
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }
}
