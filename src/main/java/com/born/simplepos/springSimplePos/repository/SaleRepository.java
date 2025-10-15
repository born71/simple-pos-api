package com.born.simplepos.springSimplePos.repository;

import com.born.simplepos.springSimplePos.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT SUM(s.totalAmount) FROM Sale s")
    Double getTotalRevenue();

    // ✅ Total number of sales
    @Query("SELECT COUNT(s) FROM Sale s")
    Long getTotalSalesCount();

    // ✅ Daily sales summary
    @Query("SELECT new map(FUNCTION('DATE', s.saleDate) as date, SUM(s.totalAmount) as dailyTotal) " +
            "FROM Sale s GROUP BY FUNCTION('DATE', s.saleDate) ORDER BY FUNCTION('DATE', s.saleDate)")
    List<Map<String, Object>> getDailySales();
}
