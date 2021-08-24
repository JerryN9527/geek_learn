package com.example.spring.service;

import com.example.spring.generate.GGoods;
import com.example.spring.generate.GGoodsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService{
    @Autowired
    private GGoodsDao gGoodsDao;

    @Override
    public List<GGoods> findAll() {
        return gGoodsDao.findAll();
    }
}
