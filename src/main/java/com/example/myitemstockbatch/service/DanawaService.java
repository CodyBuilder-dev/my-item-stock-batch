package com.example.myitemstockbatch.service;

import com.example.myitemstockbatch.entity.MinimalPrice;
import com.example.myitemstockbatch.repository.DanawaCrawlRepository;

import com.example.myitemstockbatch.repository.MinimalPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DanawaService {
    private final DanawaCrawlRepository danawaCrawlRepository;
    private final MinimalPriceRepository minimalPriceRepository;

    public boolean saveDanawaMinPrice(long danawaId) {
        try {
            long minPrice = danawaCrawlRepository.crawlDanawaMinPrice(danawaId);
            MinimalPrice minimalPrice = new MinimalPrice();
            minimalPrice.setDanawaId(danawaId);
            minimalPrice.setMinPrice(minPrice);
            minimalPrice.setCrawlDatetime(LocalDateTime.now());

            minimalPriceRepository.save(minimalPrice);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
