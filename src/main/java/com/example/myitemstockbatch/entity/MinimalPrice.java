package com.example.myitemstockbatch.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class MinimalPrice {
    @Id
    @GeneratedValue
    private long id;

    private long danawaId;

    private long minPrice;

    private LocalDateTime crawlDatetime;

}
