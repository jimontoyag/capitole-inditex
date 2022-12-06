package com.jimontoyag.controller.impl;

import com.jimontoyag.controller.InventoryController;
import com.jimontoyag.model.Product;
import com.jimontoyag.service.InventoryService;

import javax.inject.Inject;
import java.util.stream.Collectors;

public class InventoryControllerImpl implements InventoryController {

  private final InventoryService inventoryService;

  @Inject
  public InventoryControllerImpl(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  @Override
  public String showValidOrderedProductsIds() {
    return inventoryService
        .filterVisibleProductsOrderBySequence()
        .map(Product::id)
        .map(String::valueOf)
        .collect(Collectors.joining(","));
  }
}
