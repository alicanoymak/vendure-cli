package cli;

import cli.graphql.GraphQlClient;
import java.util.List;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Spec;

@Command(name = "list")
public class ListCommand implements Runnable {

  @ParentCommand Main parent;

  @Spec CommandSpec spec;

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
      String url = parent == null ? null : parent.url;

      if (url == null || url.isBlank()) {
        throw new CommandLine.ParameterException(
            spec.commandLine(),
            "Missing Vendure URL. Use --url or set the URL environment variable.");
      }

      productService = new ProductService(new GraphQlClient(url));
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
