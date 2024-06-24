package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.model.Order;
import domain.model.Product;
import domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderServiceTest {

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService = new OrderService();
    }

    @Test
    public void testNormalizeUsers() {
        List<Product> products = Arrays.asList(
                new Product(111, 918.37),
                new Product(123, 918.37)
        );
        List<Order> orders = Arrays.asList(
                new Order(003, "20210308", products, 1836.74)
        );
        User user = new User(70, "Palmer Prosacco", orders);

        User normalizedUser = orderService.normalizeUser(user);

        assertEquals(1836.74, normalizedUser.getOrders().get(0).getTotal());
        assertEquals(918.37, normalizedUser.getOrders().get(0).getProducts().get(0).getValue());
        assertEquals(918.37, normalizedUser.getOrders().get(0).getProducts().get(1).getValue());
    }

    @Test
    public void testGenerateOutput() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(123, 512.24),
                new Product(111, 512.24)
        );
        List<Order> orders = Arrays.asList(
                new Order(123, "2021-12-01", products, 1024.48)
        );
        User user = new User(1, "Zarelli", orders);

        List<User> users = Arrays.asList(user);

        String jsonOutput = orderService.generateOutput(users);

        assertNotNull(jsonOutput);

        ObjectMapper objectMapper = new ObjectMapper();
        List<User> result = Arrays.asList(objectMapper.readValue(jsonOutput, User[].class));

        assertEquals(1, result.size());
        assertEquals("Zarelli", result.get(0).getName());
        assertEquals(1, result.get(0).getOrders().size());
        assertEquals("2021-12-01", result.get(0).getOrders().get(0).getDate());
    }
}
