package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by grh on 11/20/17.
 */
public class InMemoryMealRepositoryImpl implements MealRepository {

  private   Map<Integer, Meal> mealsDao = new ConcurrentHashMap<>();
   private AtomicInteger counter = new AtomicInteger(0);

    {
        createAddSave(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        createAddSave(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        createAddSave(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        createAddSave(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        createAddSave(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        createAddSave(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public Meal createAddSave(Meal meal) {

        if(meal.isNew()) {meal.setId(counter.incrementAndGet());}

        return mealsDao.put(meal.getId(), meal);
    }

    @Override
    public Meal read(int id) {

        return mealsDao.get(id);
    }

    @Override
    public void delete(int id) {
        mealsDao.remove(id);
    }

    @Override
    public Collection<Meal> getAllMealsList() {

        return mealsDao.values();
    }
}
