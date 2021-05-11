package com.example.myitemstockbatch.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class DanawaServiceTest {
    DanawaService danawaService;
    @Test
    void saveDanawaMinPrice() {
        Assert.assertEquals(danawaService.saveDanawaMinPrice(665238),true);
    }
}