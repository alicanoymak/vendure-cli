package cli;

import java.util.List;

public class ProductService {
    public List<Product> getProducts() {
        return List.of(
                new Product("Tablet",32900),
                new Product("Monitor", 19900)
        );
    }
}
