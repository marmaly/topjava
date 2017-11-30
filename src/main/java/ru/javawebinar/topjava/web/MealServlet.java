package ru.javawebinar.topjava.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static ru.javawebinar.topjava.util.TimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.TimeUtil.parseLocalTime;


public class MealServlet extends HttpServlet {

    private ConfigurableApplicationContext springContext;
    private MealRestController mealController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealController = springContext.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            Meal meal = new Meal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

            if (request.getParameter("id").isEmpty()) {
                mealController.create(meal);
            } else {
                mealController.update(meal, getId(request));
            }
            response.sendRedirect("meals");

        } else if ("filter".equals(action)) {
            LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
            request.setAttribute("meals", mealController.getBetween(startDate, startTime, endDate, endTime));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("meals", mealController.getAll());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }

        else if (action.equals("delete")) {
            int id = getId(request);
            mealController.delete(id);
            response.sendRedirect("meals");
        }

        else {
            final Meal meal = action.equals("create") ? new Meal(LocalDateTime.now(), "", 1000)
                    : mealController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("add&change.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

}
