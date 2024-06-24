package repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.OrderService;

public class OrderFileRepositoryTest {

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService = new OrderService();
    }

    @Test
    public void testWriteJsonToFile() {
        String jsonString = "{\"name\": \"John Doe\", \"age\": 30}";
        String initialPath = "C:/git-ritch/";
        String filePath = initialPath + "test.json";

        orderService.writeJsonToFile(jsonString, filePath);

        assertTrue(Files.exists(Paths.get(filePath)));
    }

}
