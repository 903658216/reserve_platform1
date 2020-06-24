package com.jh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Hotel implements Serializable {

  private static final long serialVersionUID = 1235408809149439232L;

  @TableId(type = IdType.AUTO)
  private Integer id;

  private String hotelName;

  private String hotelImage;

  private Integer type;

  private String hotelInfo;

  private String keyword;

  private Double lon;

  private Double lat;

  private Integer star;

  private String brand;

  private String address;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date openTime;

  private Integer cid;

  private String district;

  private Date createTime = new Date();
  private Integer status;

}
