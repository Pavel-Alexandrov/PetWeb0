package servlet;

import exceptions.DBException;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewDayServlet extends HttpServlet {

    //симулирует смену дня
    //после смены дня, салон обязан сформировать отчет, в котором содержится
    //суммарная выручка за день и количество проданных машин
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DailyReportService dailyReportService = DailyReportService.getInstance();

        try {
            dailyReportService.drawUpReport();
        } catch (DBException dbe) {
            throw new ServletException();
        }
    }
}
