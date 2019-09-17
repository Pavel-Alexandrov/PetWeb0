package servlet;

import exceptions.DBException;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    //принимает машину с параметрами класса Car
    // возвращает 200 статус, если принята, и 403, если нет
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CarService carService = CarService.getInstance();

            String brand = req.getParameter("brand");
            String model = req.getParameter("model");
            String licensePlate = req.getParameter("licensePlate");
            Long price = Long.valueOf(req.getParameter("price"));
            if (carService.addCar(brand, model, licensePlate, price)) {
                resp.setStatus(200);
            } else {
                resp.setStatus(403);
            }
        } catch (DBException dbe) {
            throw new ServletException();
        }
    }
}
