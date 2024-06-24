package controller;

import domain.model.User;
import domain.repository.OrderRepository;
import service.OrderService;

import java.util.List;
import java.util.Map;

public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    public List<User> processOrders(String filePath) {
        Map<String, User> users = orderRepository.readFile(filePath);
        return orderService.normalizeUsers(users);
    }

    public String generateOutput(List<User> users) {
        return orderService.generateOutput(users);
    }

}
