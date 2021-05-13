package com.example.myitemstockbatch.repository;

import com.example.myitemstockbatch.service.DanawaService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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