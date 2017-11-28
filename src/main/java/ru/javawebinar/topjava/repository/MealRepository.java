package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

/**
 * Created by grh on 11/20/17.
 */
public interface MealRepository {

    public Meal createAddSave(Meal meal);

    public void delete(int id);

    public Meal read(int id);

    public Collection<Meal> getAllMealsList();
}
