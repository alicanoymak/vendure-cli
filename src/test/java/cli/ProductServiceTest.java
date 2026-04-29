package cli;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    @Test
    void shouldReturnProducts() {
        ProductService service = new ProductService();

        List<Product> products = service.getProducts();

        assertEquals(2, products.size());
    }
}
