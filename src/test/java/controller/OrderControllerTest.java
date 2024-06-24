package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.model.User;
import domain.model.Product;
import domain.model.Order;

import domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.OrderService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderRepository orderRepository;
    private OrderService orderService;
    private OrderController orderController;

    @BeforeEach
    public void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService();
        orderController = new OrderController(orderRepository, orderService);
    }

    @Test
    public void testProcessOrders() {
        User user = new User(1, "Zarelli", Arrays.asList());
        when(orderRepository.readFile(Mockito.anyString())).thenReturn(Map.of("1", user));

        List<User> users = orderController.processOrders("fakePath");

        assertEquals(1, users.size());
        assertEquals("Zarelli", users.get(0).getName());
    }

    @Test
    public void testGenerateOutput() throws JsonProcessingException {
        List<Product> products = Arrays.asList(
                new Product(123, 512.24),
                new Product(123, 512.24)
        );
        List<Order> orders = Arrays.asList(
                new Order(123, "2021-12-01", products, 1024.48)
        );
        User user = new User(1, "Zarelli", orders);

        List<User> users = Arrays.asList(user);

        String jsonOutput = orderController.generateOutput(users);

        assertNotNull(jsonOutput);

        ObjectMapper objectMapper = new ObjectMapper();
        List<User> result = Arrays.asList(objectMapper.readValue(jsonOutput, User[].class));

        assertEquals(1, result.size());
        assertEquals("Zarelli", result.get(0).getName());
        assertEquals(1, result.get(0).getOrders().size());
        assertEquals("2021-12-01", result.get(0).getOrders().get(0).getDate());
    }
}
