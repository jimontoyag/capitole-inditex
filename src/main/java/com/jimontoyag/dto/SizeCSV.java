package com.jimontoyag.dto;

import com.opencsv.bean.CsvBindByPosition;

public class SizeCSV {

  @CsvBindByPosition(position = 0)
  private Long id;

  @CsvBindByPosition(position = 1)
  private Long productId;

  @CsvBindByPosition(position = 2)
  private Boolean backSoon;

  @CsvBindByPosition(position = 3)
  private Boolean special;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Boolean getBackSoon() {
    return backSoon;
  }

  public void setBackSoon(Boolean backSoon) {
    this.backSoon = backSoon;
  }

  public Boolean getSpecial() {
    return special;
  }

  public void setSpecial(Boolean special) {
    this.special = special;
  }
}
