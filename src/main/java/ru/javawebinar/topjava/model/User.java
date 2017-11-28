package ru.javawebinar.topjava.model;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Created by grh on 11/28/17.
 */
public class User extends AbstractNamedEntity {

    private String emale;

    private String password;

    private boolean enabled = true;

    private Date registered = new Date();

    private Set<Role> authorities;

    public User() {
    }

    public User(String name, String emale, String password, Role role, Role...roles) {
        super(name);
        this.emale = emale;
        this.password = password;
        this.enabled = true;
        this.authorities = authorities;
    }

    public String getEmale() {
        return emale;
    }

    public void setEmale(String emale) {
        this.emale = emale;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Collection<Role> getAuthorities() {
        return authorities;
    }
}
