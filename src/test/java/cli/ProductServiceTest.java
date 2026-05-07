package cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cli.graphql.GraphQlClient;
import cli.graphql.GraphQlQuery;
import cli.vendure.ProductDetailQuery;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ProductServiceTest {

  private ProductService serviceWithProducts(List<Product> products) {
    return new ProductService(new FakeGraphQlClient(products, null));
  }

  private ProductService serviceWithProduct(Product product) {
    return new ProductService(new FakeGraphQlClient(List.of(), product));
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

  @Test
  void shouldReturnProductDetail() {
    ProductService service = serviceWithProduct(new Product("Laptop", 129900));

    Product product = service.getProduct("123");

    assertEquals("Laptop", product.getName());
    assertEquals(129900, product.getPrice());
  }

  private static class FakeGraphQlClient extends GraphQlClient {
    private final List<Product> products;
    private final Product product;

    FakeGraphQlClient(List<Product> products, Product product) {
      super("http://example.com");
      this.products = products;
      this.product = product;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T execute(GraphQlQuery<T> query) {
      if (query instanceof ProductDetailQuery) {
        return (T) product;
      }

      return (T) products;
    }
  }
}
