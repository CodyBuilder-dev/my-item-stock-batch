package com.example.myitemstockbatch;

import com.zaxxer.hikari.HikariDataSource;

import org.apache.juli.logging.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Logger;


@SpringBootTest
class MyItemStockBatchApplicationTests {
	@Autowired
	Logger log;
	@Autowired
	ApplicationContext apxContext;

	@Test
	void contextLoads() {
	}

	/**
	 * datasource bean이 잘 생성되고, 설정값은 잘 들어갔는지 확인하는 코드
	 */
	//todo: Log와 Logger의 차이가 뭘까?
	//todo: 같은 Log도 juli, common등 다양한데 차이가 뭘까?
	@Test
	void dataSourceBeanSet() {
////		DefaultListableBeanFactory listableBeanFactory = new DefaultListableBeanFactory();
//		String[] listedBean = apxContext.getBeanDefinitionNames();
//		for(String name: listedBean){
////			System.out.println("BEAN NAME="+name);
//			log.info(name);
//		}

	}
}
