package cli;

import cli.graphql.GraphQlClient;
import java.util.List;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

@Command(name = "list")
public class ListCommand implements Runnable {

  @ParentCommand Main parent;

  @Option(names = "--format", defaultValue = "table")
  String format;

  private ProductService service;

  public ListCommand() {}

  public ListCommand(ProductService service) {
    this.service = service;
  }

  @Override
  public void run() {
    ProductService productService = service;

    if (productService == null) {
      productService = new ProductService(new GraphQlClient(parent.url));
    }

    List<Product> products = productService.getProducts();

    ProductFormatter formatter;

    if ("json".equals(format)) {
      formatter = new JsonProductFormatter();
    } else {
      formatter = new TableProductFormatter();
    }
    System.out.println(formatter.format(products));
  }
}
