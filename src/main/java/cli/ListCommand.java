package cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.List;

@Command(name = "list")
public class ListCommand implements Runnable {

    @Option(names = "--format", defaultValue = "table")
    String format;

    @Override
    public void run() {
        ProductService service = new ProductService();
        List<Product> products = service.getProducts();

        if (format.equals("json")) {
            System.out.println("[");
            for (Product p : products) {
                System.out.println("  {\"name\": \"" + p.getName() + "\", \"price\": " + p.getPrice() + "}");
            }
            System.out.println("]");
        } else {
            System.out.println("Name      Price");
            for (Product p : products) {
                System.out.println(p.getName() + "   " + p.getPrice());
            }
        }
    }
}