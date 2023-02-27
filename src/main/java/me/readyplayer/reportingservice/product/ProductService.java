package me.readyplayer.reportingservice.product;

import me.readyplayer.reportingservice.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    public List<Product> fetchProducts() {
        //Mocked products for the scope of the task, in real production environment, we would fetch products from DB
        return Arrays.asList(
                new Product(1, "Bike", 1000L),
                new Product(2, "E-bike", 3000L),
                new Product(3, "Mountain bike", 1500L),
                new Product(4, "Folding e-bike", 3500L)
        );
    }

    public Product findProductById(int productId) throws CustomException {
        Product product = fetchProducts().stream()
                .filter(c -> productId == c.getId())
                .findAny()
                .orElse(null);

        if (product == null) {
            throw new CustomException("Error! Product not found.");
        }

        return product;
    }
}
