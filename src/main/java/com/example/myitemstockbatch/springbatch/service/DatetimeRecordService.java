package com.example.myitemstockbatch.springbatch.service;

import com.example.myitemstockbatch.springbatch.mapper.DatetimeRecordMapper;
import org.springframework.stereotype.Service;

@Service
public class DatetimeRecordService {
    DatetimeRecordMapper datetimeRecordMapper;

    DatetimeRecordService(DatetimeRecordMapper datetimeRecordMapper){
        this.datetimeRecordMapper = datetimeRecordMapper;
    }

    public void recordDatetimeNow(){
        datetimeRecordMapper.recordDatetimeNow();
    }
}
