package com.born.simplepos.springSimplePos.repository;

import com.born.simplepos.springSimplePos.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
