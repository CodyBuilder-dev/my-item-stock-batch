package com.example.myitemstockbatch.springbatch.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DatetimeRecordMapper {
    void recordDatetimeNow();
}
