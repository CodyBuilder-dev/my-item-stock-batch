package com.example.myitemstockbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class MyItemStockBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyItemStockBatchApplication.class, args);
	}

}
