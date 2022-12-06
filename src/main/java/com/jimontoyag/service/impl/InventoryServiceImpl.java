package com.jimontoyag.service.impl;

import com.jimontoyag.model.Product;
import com.jimontoyag.model.Size;
import com.jimontoyag.repository.InventoryRepository;
import com.jimontoyag.service.InventoryService;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.stream.Stream;

public class InventoryServiceImpl implements InventoryService {

  private final InventoryRepository inventoryRepository;

  @Inject
  public InventoryServiceImpl(InventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
  }

  @Override
  public Stream<Product> filterVisibleProductsOrderBySequence() {
    return inventoryRepository.loadInventory().products().values().stream()
        .filter(this::isVisible)
        .sorted(Comparator.comparing(Product::sequence));
  }

  private boolean isVisible(Product product) {
    var report =
        product.sizes().values().stream()
            .reduce(new SizeReport(), SizeReport::addToReport, SizeReport::merge);

    if (report.specials == 0 && report.stockOrBackSoon > 0) {
      return true;
    } else if (report.specials > 0
        && report.specialsWithStockOrBackSoon > 0
        && report.nonSpecialWithStockOrBackSoon > 0) {
      return true;
    }
    return false;
  }

  private class SizeReport {
    int sizes;
    int stockOrBackSoon;
    int specialsWithStockOrBackSoon;
    int nonSpecialWithStockOrBackSoon;
    int specials;

    SizeReport addToReport(Size size) {
      sizes++;
      var hasStockOrBackSoon = size.quantity() > 0 || size.backSoon();
      if (hasStockOrBackSoon) {
        stockOrBackSoon++;
      }
      if (hasStockOrBackSoon && size.special()) {
        specialsWithStockOrBackSoon++;
      }
      if (hasStockOrBackSoon && !size.special()) {
        nonSpecialWithStockOrBackSoon++;
      }
      if (size.special()) {
        specials++;
      }
      return this;
    }

    static SizeReport merge(SizeReport a, SizeReport b) {
      a.sizes += b.sizes;
      a.stockOrBackSoon += b.stockOrBackSoon;
      a.specialsWithStockOrBackSoon += b.specialsWithStockOrBackSoon;
      a.nonSpecialWithStockOrBackSoon += b.nonSpecialWithStockOrBackSoon;
      a.specials += b.specials;
      return a;
    }
  }
}
