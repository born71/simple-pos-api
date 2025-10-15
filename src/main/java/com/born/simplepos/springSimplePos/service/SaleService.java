package com.born.simplepos.springSimplePos.service;

import com.born.simplepos.springSimplePos.entity.Sale;
import java.util.List;

public interface SaleService {
    Sale createSale(Sale sale);
    List<Sale> getAllSales();
    Sale getSaleById(Long id);
}
