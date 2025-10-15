package com.born.simplepos.springSimplePos.service;

import com.born.simplepos.springSimplePos.entity.Sale;
import com.born.simplepos.springSimplePos.entity.SaleItem;
import com.born.simplepos.springSimplePos.repository.ProductRepository;
import com.born.simplepos.springSimplePos.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Sale createSale(Sale sale) {
        // Set sale date
        sale.setSaleDate(new Date());

        // Compute total
        double total = sale.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
        sale.setTotalAmount(total);

        // Update product stock
        sale.getItems().forEach(item -> {
            productRepository.findById(item.getProductId()).ifPresent(product -> {
                product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
                productRepository.save(product);
            });
            item.setSale(sale); // link sale reference
        });

        return saleRepository.save(sale);
    }

    @Override
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @Override
    public Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found with id " + id));
    }
}
