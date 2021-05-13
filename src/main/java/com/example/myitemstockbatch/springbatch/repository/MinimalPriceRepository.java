package com.example.myitemstockbatch.springbatch.repository;

import com.example.myitemstockbatch.springbatch.entity.MinimalPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinimalPriceRepository extends JpaRepository<MinimalPrice,Long> {
}
