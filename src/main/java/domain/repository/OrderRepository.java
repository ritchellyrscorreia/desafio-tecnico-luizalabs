package domain.repository;

import domain.model.User;

import java.util.Map;

public interface OrderRepository {
    Map<String, User> readFile(String filePath);
}
