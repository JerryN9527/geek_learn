package com.example.spring.generate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface GGoodsDao extends BaseMapper<GGoods> {
    int deleteByPrimaryKey(Integer id);

    int insert(GGoods record);

    int insertSelective(GGoods record);

    GGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GGoods record);

    int updateByPrimaryKey(GGoods record);

    List<GGoods> findAll();
}