package com.imax.stock.bl.service;

import com.imax.stock.bl.domain.model.StockModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends JpaRepository<StockModel, Long> {

}
