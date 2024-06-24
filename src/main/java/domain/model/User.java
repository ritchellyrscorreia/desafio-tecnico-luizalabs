package domain.model;

import java.util.List;

public class User {

    private int userId;
    private String name;
    private List<Order> orders;

    public User() {
    }

    public User(int userId, String name, List<Order> orders) {
        this.userId = userId;
        this.name = name;
        this.orders = orders;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
