package service;

import DAO.DailyReportDao;
import exceptions.DBException;
import model.DailyReport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private long soldCars = 0;

    private long earning = 0;

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
            return new DailyReportDao(sessionFactory.openSession()).getAllDailyReport();
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }

    public DailyReport getLastReport() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            DailyReportDao dailyReportDao = new DailyReportDao(session);
            DailyReport report = dailyReportDao.getLastReport();
            session.close();
            return report;
        } catch (HibernateException he) {
            throw new DBException(he);
        }
    }

    public void addSoldCar(Long carPrice) {
            this.soldCars++;
            this.earning += carPrice;
    }

    public void drawUpReport() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            DailyReportDao dailyReportDao = new DailyReportDao(session);
            dailyReportDao.addReport(soldCars, earning);
            this.soldCars = 0;
            this.earning = 0;
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
