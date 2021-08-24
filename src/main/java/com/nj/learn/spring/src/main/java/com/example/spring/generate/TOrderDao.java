package com.example.spring.generate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import generate.TOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TOrderDao extends BaseMapper<TOrder> {
    int deleteByPrimaryKey(Long orderId);

    int insert(TOrder record);

    int insertSelective(TOrder record);

    TOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(TOrder record);

    int updateByPrimaryKey(TOrder record);

    @Select("select * from t_order")
    List<TOrder> findAll();

    @Delete("delete from t_order")
    void deleteAll();
}