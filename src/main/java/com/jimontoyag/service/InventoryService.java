package com.jimontoyag.service;

import com.jimontoyag.model.Product;

import java.util.stream.Stream;

public interface InventoryService {

  Stream<Product> filterVisibleProductsOrderBySequence();
}
