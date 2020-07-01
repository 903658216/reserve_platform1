package com.jh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName = "hotel_index",shards = 1,replicas = 0)
public class Hotel implements Serializable {

  private static final long serialVersionUID = 1235408809149439232L;

  @TableId(type = IdType.AUTO)
  @Field(type = FieldType.Integer,index = false)
  private Integer id;

  @Field(type = FieldType.Text,analyzer = "ik_max_word")
  private String hotelName;
  @Field(type = FieldType.Keyword,index = false)
  private String hotelImage;
  @Field(type = FieldType.Integer)
  private Integer type;
  @Field(type = FieldType.Text,analyzer = "ik_max_word")
  private String hotelInfo;
  @Field(type = FieldType.Text,analyzer = "ik_max_word")
  private String keyword;
  @Transient
  private Double lon;
  @Transient
  private Double lat;
  @Field(type = FieldType.Integer)
  private Integer star;
  @Field(type = FieldType.Text,analyzer = "ik_max_word")
  private String brand;
  @Field(type = FieldType.Text,analyzer = "ik_max_word")
  private String address;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Field(type = FieldType.Date,pattern = "yyyy-MM-dd")
  private Date openTime;

  private Integer cid;
  @Field(type = FieldType.Text, analyzer = "ik_max_word")
  private String district;
  @Transient
  private Date createTime = new Date();
  @Transient
  private Integer status;

}
