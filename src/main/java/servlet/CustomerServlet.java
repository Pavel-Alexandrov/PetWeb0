package servlet;

import com.google.gson.Gson;
import exceptions.DBException;
import model.Car;
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
        try {
            Gson gson = new Gson();
            String json = gson.toJson(CarService.getInstance().getAllCars());

            resp.getWriter().write(json);
            resp.setStatus(200);
        } catch (DBException dbe) {
            throw new ServletException();
        }
    }

    // покупает машину, передав параметры марки машины, названия и госномера
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CarService carService = CarService.getInstance();
            DailyReportService dailyReportService = DailyReportService.getInstance();

            String brand = req.getParameter("brand");
            String model = req.getParameter("model");
            String licensePlate = req.getParameter("licensePlate");

            Car car = carService.getCarIfExist(brand, model, licensePlate);
            if (car != null) {
                carService.removeCar(brand, model, licensePlate);
                dailyReportService.addSoldCar(car.getPrice());
                resp.setStatus(200);
            } else {
                resp.setStatus(403);
            }
        } catch (DBException dbe) {
            throw new ServletException();
        }
    }
}
