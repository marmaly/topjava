package ru.javawebinar.topjava;

public class AuthorizedUser {
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return (this.id ==null);
    }
}
