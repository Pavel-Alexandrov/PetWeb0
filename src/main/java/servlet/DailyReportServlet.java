package servlet;

import com.google.gson.Gson;
import exceptions.DBException;
import model.DailyReport;
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
        Gson gson = new Gson();
        String json = null;
        try {
            if (req.getPathInfo().contains("all")) {
                json = gson.toJson(dailyReportService.getAllDailyReports());
            } else if (req.getPathInfo().contains("last")) {
                json = gson.toJson(dailyReportService.getLastReport());
            }

        } catch (DBException dbe) {
            throw new ServletException();
        }

        resp.getWriter().write(json);
        resp.setStatus(200);
    }

    //удаляет все данные об отчетах и машинах
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CarService carService = CarService.getInstance();
            DailyReportService dailyReportService = DailyReportService.getInstance();
            carService.delete();
            dailyReportService.delete();
        } catch (DBException dbe) {
            throw new ServletException();
        }
    }
}
