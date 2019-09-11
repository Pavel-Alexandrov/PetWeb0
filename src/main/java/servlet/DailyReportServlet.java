package servlet;

import exceptions.DBException;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DailyReportServlet extends HttpServlet {

    // */report/last* возвращает отчет за прошедший день
    // */report/all* возвращает все сформированные отчеты
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DailyReportService dailyReportService = DailyReportService.getInstance();

        if (req.getPathInfo().contains("all")) {
            dailyReportService.getAllDailyReports();
        } else if (req.getPathInfo().contains("last")) {
            dailyReportService.getLastReport();
        }
    }

    //удаляет все данные об отчетах и машинах
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarService carService = CarService.getInstance();
        DailyReportService dailyReportService = DailyReportService.getInstance();

        try {
            carService.delete();
            dailyReportService.delete();
        } catch (DBException dbe) {
            throw new ServletException();
        }
    }
}
