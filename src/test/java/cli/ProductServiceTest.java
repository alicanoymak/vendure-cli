package cli;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    @Test
    void shouldReturnTwoProducts() {
        ProductService service = new ProductService();

        List<Product> products = service.getProducts();

        assertEquals(2, products.size());
    }

    @Test
    void shouldReturnFirstProductWithName() {
        ProductService service = new ProductService();

        List<Product> products = service.getProducts();

        assertEquals("Tablet", products.get(0).getName());
    }

    @Test
    void shouldReturnFirstProductWithPrice() {
        ProductService service = new ProductService();

        List<Product> products = service.getProducts();

        assertEquals(32900, products.get(0).getPrice());
    }
}