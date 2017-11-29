package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;


public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryMealRepositoryImpl();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ?
                null
                :
                Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        repository.save(meal, AuthorizedUser.id());
        response.sendRedirect("meals");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            log.info("getAll");
            // request.setAttribute("meals", MealsUtil.getWithExceeded(MealsUtil.MEALS, MealsUtil.DEFAULT_CALORIES_PER_DAY));

            request.setAttribute("meals", MealsUtil.getWithExceeded(repository.getAll(AuthorizedUser.id()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }

        else if (action.equals("delete")) {
            int id = getId(request);
            log.info("Delete {}", id);
            repository.delete(id, AuthorizedUser.id());
            response.sendRedirect("meals");
        }

        else {
            final Meal meal = action.equals("create") ? new Meal(LocalDateTime.now(), "", 1000)
                    : repository.get(getId(request), AuthorizedUser.id());
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("add&change.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

}
