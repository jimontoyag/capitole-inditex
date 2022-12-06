package com.jimontoyag.dto;

import com.opencsv.bean.CsvBindByPosition;

public class ProductCSV {

  @CsvBindByPosition(position = 0)
  private Long id;

  @CsvBindByPosition(position = 1)
  private Integer sequence;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getSequence() {
    return sequence;
  }

  public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }
}
