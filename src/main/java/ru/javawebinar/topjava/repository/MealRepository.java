package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;


public interface MealRepository {

    public Meal createAddSave(Meal meal);

    public void delete(int id);

    public Meal read(int id);

    public Collection<Meal> getAllMealsList();
}
