package cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.List;

@Command(name = "list")
public class ListCommand implements Runnable {

    @Option(names = "--format", defaultValue = "table")
    String format;

    private final ProductService service;

    public ListCommand() {
        this(new ProductService());
    }

    public ListCommand(ProductService service) {
        this.service = service;
    }

    @Override
    public void run() {
        List<Product> products = service.getProducts();

        if ("json".equals(format)) {
            System.out.println("[");
            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                System.out.print("  {\"name\": \"" + p.getName() + "\", \"price\": " + p.getPrice() + "}");
                if (i < products.size() - 1) {
                    System.out.println(",");
                } else {
                    System.out.println();
                }
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