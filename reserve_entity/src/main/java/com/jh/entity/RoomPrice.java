package com.jh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RoomPrice implements Serializable {


  @TableId(type = IdType.AUTO)
  @Field(type = FieldType.Integer)
  private Integer id;
  @Field(type = FieldType.Integer)
  private Integer rid;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Field(type = FieldType.Date,format = DateFormat.date)
  private Date date;
  @Field(type = FieldType.Double)
  private BigDecimal price;
  @Transient
  private Integer type;
  @Field(type = FieldType.Integer)
  private Integer number;
  @Field(type = FieldType.Integer)
  private Integer hasNumber;
  @Transient
  private Integer status;

}
