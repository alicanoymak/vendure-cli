package cli.vendure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cli.Product;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class ProductDetailQueryTest {

  @Test
  void shouldBuildProductDetailQuery() {

    ProductDetailQuery query = new ProductDetailQuery("123");

    String graphQl = query.toGraphQl();

    assertTrue(graphQl.contains("product"));
    assertTrue(graphQl.contains("id: \"123\""));
    assertTrue(graphQl.contains("name"));
    assertTrue(graphQl.contains("variants"));
    assertTrue(graphQl.contains("price"));
  }

  @Test
  void shouldParseProductDetailResponse() throws Exception {
    String json =
        """
               {
                 "product": {
                   "name": "Laptop",
                   "variants": [
                     {
                       "price": 129900
                     }
                   ]
                 }
               }
               """;

    ObjectMapper mapper = new ObjectMapper();
    JsonNode data = mapper.readTree(json);

    ProductDetailQuery query = new ProductDetailQuery("123");
    Product product = query.parse(data);

    assertEquals("Laptop", product.getName());
    assertEquals(129900, product.getPrice());
  }
}
