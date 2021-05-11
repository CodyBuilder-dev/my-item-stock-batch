package com.example.myitemstockbatch.repository;

import com.example.myitemstockbatch.entity.MinimalPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinimalPriceRepository extends JpaRepository<MinimalPrice,Long> {
}
