package DAO;

import model.DailyReport;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Query query = session.createQuery("FROM DailyReport");
        return query.getResultList();
    }

    public List<DailyReport> getLastReport() {
        Query query = session.createQuery("FROM DailyReport ORDER BY id DESC");
        query.setMaxResults(1);
        return query.getResultList();
    }

    public void addReport(DailyReport dailyReport) {
        Query query = session.createQuery("INSERT INTO DailyReport (earning, soldCars) SELECT dailyReport.getEarning(), dailyReport.getSoldCars()");
        query.executeUpdate();
    }

    public void clean() {
        Query query = session.createQuery("DELETE FROM DailyReport");
        query.executeUpdate();
    }
}
