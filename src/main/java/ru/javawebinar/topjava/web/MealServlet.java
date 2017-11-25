package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by grh on 11/20/17.
 */
public class MealServlet extends HttpServlet {

    private MealsDao mealsDaoServlet = new MealsDao();

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null :
                Integer.valueOf(id),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.valueOf(req.getParameter("calories")));
        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealsDaoServlet.createAddSave(meal);
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");


        if (action == null) {
            log.info("getAll");
            // request.setAttribute("meals", MealsUtil.getWithExceeded(MealsUtil.MEALS, MealsUtil.DEFAULT_CALORIES_PER_DAY));

            request.setAttribute("meals", MealsUtil.getWithExceeded(mealsDaoServlet.getAllMealsList(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (action.equals("delete")) {

            log.info("Delete {}", getId(request));
            mealsDaoServlet.read(getId(request));
            response.sendRedirect("meals");
        } else {
            final Meal meal = action.equals("create") ? new Meal(LocalDateTime.now(), "", 1000) : mealsDaoServlet.read(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("add&change.jsp").forward(request, response);
        }


   /*     log.info("getAll");
        request.setAttribute("meals", MealsUtil.getWithExceeded(MealsUtil.MEALS, MealsUtil.DEFAULT_CALORIES_PER_DAY));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }*/
    }
    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
