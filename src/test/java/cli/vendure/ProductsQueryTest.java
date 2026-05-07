package cli.vendure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cli.Product;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ProductsQueryTest {

  @Test
  void shouldBuildProductsQuery() {
    ProductsQuery productsQuery = new ProductsQuery();

    String graphQl = productsQuery.toGraphQl();

    assertTrue(graphQl.contains("products"));
    assertTrue(graphQl.contains("items"));
    assertTrue(graphQl.contains("name"));
    assertTrue(graphQl.contains("variants"));
    assertTrue(graphQl.contains("price"));
  }

  @Test
  void shouldParseProductsResponse() throws Exception {
    String json =
        """
        {
          "products": {
            "items": [
              {
                "name": "Laptop",
                "variants": [
                  {
                    "price": 129900
                  }
                ]
              }
            ]
          }
        }
        """;

    ObjectMapper mapper = new ObjectMapper();
    JsonNode data = mapper.readTree(json);

    ProductsQuery query = new ProductsQuery();
    List<Product> products = query.parse(data);

    assertEquals(1, products.size());
    assertEquals("Laptop", products.get(0).getName());
    assertEquals(129900, products.get(0).getPrice());
  }
}
