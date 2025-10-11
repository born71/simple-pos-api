package com.born.simplepos.springSimplePos.controller;

import com.born.simplepos.springSimplePos.entity.Product;
import com.born.simplepos.springSimplePos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")  // allow Angular frontend access
public class ProductController {

    private final ProductService productService;

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<Product> createProductWithImage(
            @RequestParam String name,
            @RequestParam String category,
            @RequestParam double price,
            @RequestParam int stockQuantity,
            @RequestPart(required = false) MultipartFile imageFile) {

        try {
            String fileName = null;

            // ✅ Save image if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.write(filePath, imageFile.getBytes());
            }

            // ✅ Create product with image name
            Product product = Product.builder()
                    .name(name)
                    .category(category)
                    .price(price)
                    .stockQuantity(stockQuantity)
                    .imageUrl(fileName != null ? "/uploads/" + fileName : null)
                    .build();

            return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
