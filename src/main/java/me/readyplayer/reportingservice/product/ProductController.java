package me.readyplayer.reportingservice.product;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> generateReport() {
        List<Product> products = productService.fetchProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
