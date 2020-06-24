package com.jh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.dao.CityMapper;
import com.jh.entity.City;
import com.jh.service.ICityService;
import com.jh.util.PinyinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements ICityService {

    @Autowired
    private CityMapper cityMapper;

    /**
     * 重写保存方法
     * @param entity
     * @return
     */
    @Override
    public boolean save(City entity) {

        //设置城市的拼音
        String pinyin = PinyinUtil.str2Pinyin(entity.getCityName());
        entity.setCityPinyin(pinyin);

        return super.save(entity);
    }


    /**
     * 根据城市编号来修改城市所拥有的酒店数量
     * @param cId
     * @param number
     * @return
     */
    @Override
    public boolean updateHotelNumById(Integer cId, Integer number) {

        return cityMapper.updateHotelNumById(cId,number);
    }
}
