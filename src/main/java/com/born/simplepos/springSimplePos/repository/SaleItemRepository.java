package com.born.simplepos.springSimplePos.repository;

import com.born.simplepos.springSimplePos.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {

    // ✅ Top-selling products
    @Query("SELECT new map(i.productName as product, SUM(i.quantity) as totalSold, " +
            "SUM(i.price * i.quantity) as totalRevenue) " +
            "FROM SaleItem i GROUP BY i.productName ORDER BY totalSold DESC")
    List<Map<String, Object>> findTopProducts();
}
