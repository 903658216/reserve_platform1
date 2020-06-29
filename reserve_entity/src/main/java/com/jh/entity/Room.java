package com.jh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Room implements Serializable {

  private static final long serialVersionUID = 1861381504265518080L;

  @TableId(type = IdType.AUTO)
  private Integer id;

  private Integer hid;

  private String title;

  private String area;

  private Integer num;

  private String image;

  private String info;

  private String bed;

  private Integer number;

  private BigDecimal price;

  private BigDecimal defaultPrice;

  private Integer status;

}
