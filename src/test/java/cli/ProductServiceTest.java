package cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cli.graphql.GraphQlClient;
import cli.graphql.GraphQlQuery;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ProductServiceTest {

  private ProductService serviceWithProducts(List<Product> products) {
    return new ProductService(new FakeGraphQlClient(products));
  }

  @Test
  void shouldReturnTwoProducts() {
    ProductService service =
        serviceWithProducts(List.of(new Product("Tablet", 32900), new Product("Monitor", 19900)));

    List<Product> products = service.getProducts();

    assertEquals(2, products.size());
  }

  @Test
  void shouldReturnFirstProductWithName() {
    ProductService service =
        serviceWithProducts(List.of(new Product("Tablet", 32900), new Product("Monitor", 19900)));

    List<Product> products = service.getProducts();

    assertEquals("Tablet", products.get(0).getName());
  }

  @Test
  void shouldReturnFirstProductWithPrice() {
    ProductService service =
        serviceWithProducts(List.of(new Product("Tablet", 32900), new Product("Monitor", 19900)));

    List<Product> products = service.getProducts();

    assertEquals(32900, products.get(0).getPrice());
  }

  private static class FakeGraphQlClient extends GraphQlClient {
    private final List<Product> products;

    FakeGraphQlClient(List<Product> products) {
      super("http://example.com");
      this.products = products;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T execute(GraphQlQuery<T> query) {
      return (T) products;
    }
  }
}
