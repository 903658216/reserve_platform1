package com.jh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.dao.CityMapper;
import com.jh.entity.City;
import com.jh.service.ICityService;
import com.jh.util.PinyinUtil;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements ICityService {


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


}
