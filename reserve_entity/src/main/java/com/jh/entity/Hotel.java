package com.jh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName = "hotel_index",shards = 1,replicas = 0)
public class Hotel implements Serializable {


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

  @GeoPointField
  @TableField(exist = false)
  private Double[] myLocation = new Double[2];

  @Field(type = FieldType.Integer)
  private Integer star;
  @Field(type = FieldType.Text,analyzer = "ik_max_word")
  private String brand;
  @Field(type = FieldType.Text,analyzer = "ik_max_word")
  private String address;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Field(type = FieldType.Date,format = DateFormat.date)
  private Date openTime;

  @Transient
  private Integer cid;

  @Field(type = FieldType.Text, analyzer = "ik_max_word")
  private String district;
  @Transient
  private Date createTime = new Date();
  @Transient
  private Integer status =0;

  @TableField(exist = false)
  @Field(type = FieldType.Nested)
  private City city;

  @TableField(exist = false)
  @Field(type = FieldType.Nested)
  private List<Room> roomList = new ArrayList<>();

  public Hotel setLon(Double lon) {
    this.lon = lon;
    this.myLocation[0] = lon;
    return this;
  }

  public Hotel setLat(Double lat) {
    this.lat = lat;
    this.myLocation[1]=lat;
    return this;
  }
}
