package cli;

import cli.graphql.GraphQlClient;
import cli.vendure.ProductsQuery;
import java.util.List;

public class ProductService {
  private final GraphQlClient client;

  public ProductService(GraphQlClient client) {
    this.client = client;
  }

  public List<Product> getProducts() {
    return client.execute(new ProductsQuery());
  }
}
