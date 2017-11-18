package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );

        //было getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000); - просто вызов метода, мы сделали extract variable (извлечение переменной) Ctrl + Alt + V - что дало нам переменную и можно увидеть, например, ее параметры (тип (класса) возврещаемого значения)
        List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        filteredWithExceeded.forEach(System.out::println);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesSumByDay = mealList.stream().collect(Collectors.groupingBy((um -> um.getDateTime().toLocalDate()), Collectors.summingInt(UserMeal::getCalories)));

//метод groupingBy группирует по ключу = первая переменная, в нашем случае дата, возвращает вторую переменную, в нашем случае сумму калориев за эту дату = um->um.getCalories() = (после рефакторинга идеей) UserMeal::getCalories).

        return mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(), caloriesSumByDay.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }


    public static List<UserMealWithExceed>  getFilteredWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> calloriesInDay = new HashMap<>();

        for (UserMeal userMeal : mealList) {

            calloriesInDay.put(userMeal.getDateTime().toLocalDate(), calloriesInDay.getOrDefault(userMeal.getDateTime().toLocalDate(), 0) + userMeal.getCalories());

            //put - заполняем HashMap данными ключ - userMeal.getDateTime().toLocalDate() - это дата;
            // значение - calloriesInDay.getOrDefault(userMeal.getDateTime().toLocalDate(), 0)+userMeal.getCalories()) - это сумма калориев за дату-ключ,
            // используем метод getOrDefault (т.е. надо взять наш заполняемый список HashMap - и вызвать у этого списка метод getOrDefault - calloriesInDay.getOrDefault - т.е. взять значение или значение по умолчанию (в нашем случае 0) - (userMeal.getDateTime().toLocalDate(), 0) + userMeal.getCalories()) - т.е. если токого ключа-даты нет - то ставим значение по умолчанию 0 + userMeal.getCalories() - кол-во каллрий за один прием пищи данной даты. А если такая дата-ключ уже есть, то суммируем кол-во каллорий за один прием пищи.

//мое изначальное "детское" решение:
            /*if (!calloriesInDay.containsKey(userMeal.getDateTime().toLocalDate()))
            calloriesInDay.put(userMeal.getDateTime().toLocalDate(), userMeal.getCalories());
            else calloriesInDay.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), (v,nv) -> v + nv);*/
        }


        List<UserMealWithExceed> mealExceeded = new ArrayList<>();

        for (UserMeal userMeal : mealList) {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealExceeded.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), calloriesInDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay));
            }

        }
        return mealExceeded;
    }
}
