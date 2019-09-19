package DAO;

import model.DailyReport;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Criteria criteria = session.createCriteria(DailyReport.class);
        return criteria.list();
    }

    public DailyReport getLastReport() {
        Criteria criteria = session.createCriteria(DailyReport.class);
        criteria.addOrder(Order.desc("id"));
        criteria.setMaxResults(1);
        return (DailyReport) criteria.uniqueResult();
    }

    public void addReport(DailyReport dailyReport) {
        Transaction transaction = session.beginTransaction();
        session.save(dailyReport);
        transaction.commit();
    }

    public void clean() {
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(DailyReport.class);
        session.delete(criteria);
        transaction.commit();
    }
}
