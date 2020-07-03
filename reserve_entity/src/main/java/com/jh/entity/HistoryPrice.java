package com.jh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class HistoryPrice implements Serializable {


  @TableId(type = IdType.AUTO)
  private Integer id;

  private Integer rid;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date date;

  private BigDecimal price;

  private Integer number;

  private Integer status;

}
