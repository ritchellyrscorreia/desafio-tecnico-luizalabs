import controller.OrderController;
import domain.model.User;
import domain.repository.OrderFileRepository;
import domain.repository.OrderRepository;
import service.OrderService;
import java.util.List;



public class Main {
    public static <Map> void main(String[] args) {

        System.out.println("\nInicializando Desafio Tecnico..");

        String initialPath = "C:/git-ritch/";
        String fileName = "data_1";

        String filePathInput = initialPath + "/desafio-tecnico-luizalabs/objetivo/input/" + fileName + ".txt";
        String filePathOutput = initialPath + "/desafio-tecnico-luizalabs/objetivo/output/" + fileName + ".json";


        OrderRepository orderRepository = new OrderFileRepository();
        OrderService orderService = new OrderService();
        OrderController orderController = new OrderController(orderRepository, orderService);

        List<User> users = orderController.processOrders(filePathInput);
        String jsonOutput = orderController.generateOutput(users);

        OrderService.writeJsonToFile(jsonOutput, filePathOutput);

        System.out.println("Desafio Concluido.");

    }
}
