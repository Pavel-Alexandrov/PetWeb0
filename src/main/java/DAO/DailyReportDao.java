package DAO;

import model.DailyReport;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Query;
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
        Query query = session.createQuery("delete from DailyReport");
        query.executeUpdate();
        transaction.commit();
    }
}
