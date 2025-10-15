package com.born.simplepos.springSimplePos.repository;

import com.born.simplepos.springSimplePos.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
}
