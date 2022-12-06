package com.jimontoyag.dto;

import com.opencsv.bean.CsvBindByPosition;

public class StockCSV {

  @CsvBindByPosition(position = 0)
  private Long sizeId;

  @CsvBindByPosition(position = 1)
  private Integer quantity;

  public Long getSizeId() {
    return sizeId;
  }

  public void setSizeId(Long sizeId) {
    this.sizeId = sizeId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
