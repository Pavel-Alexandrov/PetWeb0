package servlet;

import com.google.gson.Gson;
import exceptions.DBException;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerServlet extends HttpServlet {
    // возвращает список имеющихся  машин
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json = gson.toJson(CarService.getInstance().getAllCars());

        resp.getWriter().write(json);
        resp.setStatus(200);
    }

    // покупает машину, передав параметры марки машины, названия и госномера
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarService carService = CarService.getInstance();
        DailyReportService dailyReportService = DailyReportService.getInstance();

        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");

        if (carService.removeCar(brand, model, licensePlate) && dailyReportService.addSoldCar()) {
            resp.setStatus(200);
        } else {
            resp.setStatus(403);
        }
    }
}
