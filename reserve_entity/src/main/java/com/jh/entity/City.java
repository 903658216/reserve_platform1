package com.jh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class City implements Serializable {

    @TableId(type = IdType.AUTO)
    @Field(type = FieldType.Integer)
    private Integer id;
    @Field(type = FieldType.Keyword)
    private String cityName;
    @Transient
    private String cityPinyin;
    @Transient
    private Integer hotelNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Transient
    private Date createTime;
    @Transient
    private Integer status;

}
