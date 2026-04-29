package cli;

import java.util.List;

public class ProductService {
    public List<Product> getProducts() {
        return List.of(
                new Product(),
                new Product()
        );
    }
}
