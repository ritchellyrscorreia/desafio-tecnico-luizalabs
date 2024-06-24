package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.model.Order;
import domain.model.Product;
import domain.model.User;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class OrderService {

    public List<User> normalizeUsers(Map<String, User> users) {
        return users.values().stream()
                .map(this::normalizeUser)
                .collect(Collectors.toList());
    }

    User normalizeUser(User user) {
        List<Order> orders = user.getOrders();
        for (Order order : orders) {
            double roundedTotal = roundToTwoDecimals(order.getTotal());
            order.setTotal(roundedTotal);

            List<Product> products = order.getProducts();
            for (Product product : products) {
                double roundedValue = roundToTwoDecimals(product.getValue());
                product.setValue(roundedValue);
            }
        }
        return user;
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public String generateOutput(List<User> users) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            return objectMapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeJsonToFile(String jsonString, String filePath) {
        try {
            Files.write(Paths.get(filePath), jsonString.getBytes(), StandardOpenOption.CREATE);
            System.out.println("JSON escrito com sucesso em " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Falha ao escrever JSON em " + filePath);
        }
    }

}
