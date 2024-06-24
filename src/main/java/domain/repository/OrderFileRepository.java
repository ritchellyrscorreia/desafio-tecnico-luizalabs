package domain.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.model.Order;
import domain.model.Product;
import domain.model.User;

import java.io.*;
import java.util.*;

public class OrderFileRepository implements OrderRepository {

    private static final int USER_ID_LENGTH = 10;
    private static final int NAME_LENGTH = 45;
    private static final int ORDER_ID_LENGTH = 10;
    private static final int PRODUCT_ID_LENGTH = 10;
    private static final int VALUE_LENGTH = 12;
    private static final int DATE_LENGTH = 8;

    @Override
    public Map<String, User> readFile(String filePath) {
        Map<String, User> users = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() >= USER_ID_LENGTH + NAME_LENGTH + ORDER_ID_LENGTH + PRODUCT_ID_LENGTH + VALUE_LENGTH + DATE_LENGTH) {
                    int userId = Integer.parseInt(line.substring(0, USER_ID_LENGTH).trim());
                    String name = line.substring(USER_ID_LENGTH, USER_ID_LENGTH + NAME_LENGTH).trim();
                    int orderId = Integer.parseInt(line.substring(USER_ID_LENGTH + NAME_LENGTH, USER_ID_LENGTH + NAME_LENGTH + ORDER_ID_LENGTH).trim());
                    int productId = Integer.parseInt(line.substring(USER_ID_LENGTH + NAME_LENGTH + ORDER_ID_LENGTH, USER_ID_LENGTH + NAME_LENGTH + ORDER_ID_LENGTH + PRODUCT_ID_LENGTH).trim());
                    double productValue = Double.parseDouble(line.substring(USER_ID_LENGTH + NAME_LENGTH + ORDER_ID_LENGTH + PRODUCT_ID_LENGTH, USER_ID_LENGTH + NAME_LENGTH + ORDER_ID_LENGTH + PRODUCT_ID_LENGTH + VALUE_LENGTH).trim());
                    String dateString = line.substring(USER_ID_LENGTH + NAME_LENGTH + ORDER_ID_LENGTH + PRODUCT_ID_LENGTH + VALUE_LENGTH, USER_ID_LENGTH + NAME_LENGTH + ORDER_ID_LENGTH + PRODUCT_ID_LENGTH + VALUE_LENGTH + DATE_LENGTH).trim();
                    String formattedDate = dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-" + dateString.substring(6, 8);

                    Product product = new Product(productId, productValue);
                    Order order = new Order(orderId, formattedDate, new ArrayList<>(List.of(product)), productValue);

                    String userIdString = String.valueOf(userId);
                    User user = users.get(userIdString);
                    if (user == null) {
                        user = new User(userId, name, new ArrayList<>(List.of(order)));
                        users.put(userIdString, user);
                    } else {
                        user.getOrders().add(order);
                    }
                } else {
                    System.err.println("Linha inválida: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void writeToJson(Map<String, User> users, String outputFilePath) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File(outputFilePath), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
