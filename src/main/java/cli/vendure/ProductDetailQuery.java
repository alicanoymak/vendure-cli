package cli.vendure;

import cli.Product;
import cli.graphql.GraphQlQuery;
import com.fasterxml.jackson.databind.JsonNode;

public class ProductDetailQuery implements GraphQlQuery<Product> {

  private final String id;

  public ProductDetailQuery(String id) {
    this.id = id;
  }

  @Override
  public String toGraphQl() {
    return """
          query {
            product(id: "%s") {
              name
              variants {
                price
              }
            }
          }
          """
        .formatted(id);
  }

  @Override
  public Product parse(JsonNode data) {
    JsonNode product = data.path("product");

    String name = product.path("name").asText();
    int price = 0;

    JsonNode variants = product.path("variants");
    if (variants.isArray() && variants.size() > 0) {
      price = variants.get(0).path("price").asInt();
    }

    return new Product(name, price);
  }
}
