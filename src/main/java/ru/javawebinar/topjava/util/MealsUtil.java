package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;


public class MealsUtil {
    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    //было getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000); - просто вызов метода, мы сделали extract variable (извлечение переменной) Ctrl + Alt + V - что дало нам переменную и можно увидеть, например, ее параметры (тип (класса) возврещаемого значения)
    List<MealWithExceed> filteredWithExceeded = getFilteredWithExceeded(MEALS, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);


    public static List<MealWithExceed> getWithExceeded(Collection<Meal> meals, int caloriesPerDay) {
        return getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public static List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesSumByDay = mealList.stream().collect(Collectors.groupingBy((um -> um.getDateTime().toLocalDate()), Collectors.summingInt(Meal::getCalories)));

//метод groupingBy группирует по ключу = первая переменная, в нашем случае дата, возвращает вторую переменную, в нашем случае сумму калориев за эту дату = um->um.getCalories() = (после рефакторинга идеей) Meal::getCalories).

        return mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> new MealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(), caloriesSumByDay.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }


    public static List<MealWithExceed> getFilteredWithExceededByCycle(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> calloriesInDay = new HashMap<>();

        for (Meal meal : mealList) {

            calloriesInDay.put(meal.getDateTime().toLocalDate(), calloriesInDay.getOrDefault(meal.getDateTime().toLocalDate(), 0) + meal.getCalories());

            //put - заполняем HashMap данными ключ - meal.getDateTime().toLocalDate() - это дата;
            // значение - calloriesInDay.getOrDefault(meal.getDateTime().toLocalDate(), 0)+meal.getCalories()) - это сумма калориев за дату-ключ,
            // используем метод getOrDefault (т.е. надо взять наш заполняемый список HashMap - и вызвать у этого списка метод getOrDefault - calloriesInDay.getOrDefault - т.е. взять значение или значение по умолчанию (в нашем случае 0) - (meal.getDateTime().toLocalDate(), 0) + meal.getCalories()) - т.е. если токого ключа-даты нет - то ставим значение по умолчанию 0 + meal.getCalories() - кол-во каллрий за один прием пищи данной даты. А если такая дата-ключ уже есть, то суммируем кол-во каллорий за один прием пищи.

//мое изначальное "детское" решение:
            /*if (!calloriesInDay.containsKey(meal.getDateTime().toLocalDate()))
            calloriesInDay.put(meal.getDateTime().toLocalDate(), meal.getCalories());
            else calloriesInDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), (v,nv) -> v + nv);*/

        }

        List<MealWithExceed> mealExceeded = new ArrayList<>();

        for (Meal meal : mealList) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealExceeded.add(new MealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), calloriesInDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }

        }
        return mealExceeded;
    }


}