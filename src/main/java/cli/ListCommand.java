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

        ProductFormatter formatter;

        if ("json".equals(format)) {
            formatter = new JsonProductFormatter();
        } else {
            formatter = new TableProductFormatter();
        }

        System.out.println(formatter.format(products));
    }
}