// User.java
package org.example.model;

public class User {
    private final int id;
    private final String username;
    private final String roles;
    private final String status;

    public User(int id, String username, String roles, String status) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.status = status;
    }

    public int id() {
        return id;
    }

    public String username() {
        return username;
    }

    public String roles() {
        return roles;
    }

    public String status() {
        return status;
    }
}