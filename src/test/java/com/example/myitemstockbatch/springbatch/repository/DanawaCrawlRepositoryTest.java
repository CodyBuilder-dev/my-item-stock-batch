package com.example.myitemstockbatch.springbatch.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DanawaCrawlRepositoryTest {

    @Test
    void crawlDanawaMinPrice() {
        DanawaCrawlRepository danawaCrawlRepository = new DanawaCrawlRepository();

        try {
            Assertions.assertEquals(danawaCrawlRepository.crawlDanawaMinPrice(6562283), 224000);
        } catch(Exception e) {
            Assertions.fail("Exception 발생 : " +e);
        }
    }
}