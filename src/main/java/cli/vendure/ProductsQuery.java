package cli.vendure;

import cli.Product;
import cli.graphql.GraphQlQuery;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

public class ProductsQuery implements GraphQlQuery<List<Product>> {

  @Override
  public String toGraphQl() {
    return """
           query {
             products {
               items {
                 name
                 variants {
                   price
                 }
               }
             }
           }
           """;
  }

  @Override
  public List<Product> parse(JsonNode data) {
    List<Product> products = new ArrayList<>();

    JsonNode items = data.path("products").path("items");

    for (JsonNode item : items) {
      String name = item.path("name").asText();
      // 0 par defaut au cas ou le produit n'a pas encore de variante avec un prix
      int price = 0;

      JsonNode variants = item.path("variants");
      if (variants.isArray() && variants.size() > 0) {
        price = variants.get(0).path("price").asInt();
      }

      products.add(new Product(name, price));
    }

    return products;
  }
}
