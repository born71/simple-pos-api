package com.born.simplepos.springSimplePos.controller;

import com.born.simplepos.springSimplePos.repository.SaleItemRepository;
import com.born.simplepos.springSimplePos.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class SaleAnalyticsController {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleItemRepository saleItemRepository;

    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        Double totalRevenue = saleRepository.getTotalRevenue();
        Long totalSales = saleRepository.getTotalSalesCount();
        return Map.of("total_sales", totalSales, "total_revenue", totalRevenue);
    }

    @GetMapping("/top-products")
    public List<Map<String, Object>> getTopProducts() {
        return saleItemRepository.findTopProducts();
    }

    @GetMapping("/daily")
    public List<Map<String, Object>> getDailySales() {
        return saleRepository.getDailySales();
    }
}
