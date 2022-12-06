package com.jimontoyag.repository.impl;

import com.google.inject.name.Named;
import com.jimontoyag.dto.ProductCSV;
import com.jimontoyag.dto.SizeCSV;
import com.jimontoyag.dto.StockCSV;
import com.jimontoyag.model.Inventory;
import com.jimontoyag.model.Product;
import com.jimontoyag.model.Size;
import com.jimontoyag.repository.InventoryRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InventoryCSVRepository implements InventoryRepository {

  private final CsvToBean<ProductCSV> productParser;
  private final CsvToBean<SizeCSV> sizeParser;
  private final CsvToBean<StockCSV> stockParser;

  @Inject
  public InventoryCSVRepository(
      @Named("productCSVFile") File productCSVFile,
      @Named("sizeCSVFile") File sizeCSVFile,
      @Named("stockCSVFile") File stockCSVFile)
      throws FileNotFoundException {

    this.productParser =
        new CsvToBeanBuilder(new FileReader(productCSVFile)).withType(ProductCSV.class).build();

    this.sizeParser =
        new CsvToBeanBuilder(new FileReader(sizeCSVFile)).withType(SizeCSV.class).build();

    this.stockParser =
        new CsvToBeanBuilder(new FileReader(stockCSVFile)).withType(StockCSV.class).build();
  }

  @Override
  public Inventory loadInventory() {
    var productsCsv = productParser.parse();
    var sizesCsv = sizeParser.parse();
    var stockCsv = stockParser.parse();

    var productsMap =
        productsCsv.stream()
            .map(this::mapProduct)
            .collect(Collectors.toMap(Product::id, Function.identity()));

    var stockMap =
        stockCsv.stream().collect(Collectors.toMap(StockCSV::getSizeId, StockCSV::getQuantity));

    return new Inventory(
        sizesCsv.stream()
            .reduce(
                productsMap,
                (pMap, size) -> insertSize(pMap, size, stockMap),
                (aMap, bMap) -> {
                  aMap.putAll(bMap);
                  return aMap;
                }));
  }

  private Map<Long, Product> insertSize(
      Map<Long, Product> productMap, SizeCSV sizeCSV, Map<Long, Integer> stockMap) {
    var product = productMap.get(sizeCSV.getProductId());
    product.sizes().put(sizeCSV.getId(), mapSize(sizeCSV, stockMap));
    return productMap;
  }

  private Size mapSize(SizeCSV sizeCSV, Map<Long, Integer> stockMap) {
    return new Size(
        sizeCSV.getId(),
        sizeCSV.getBackSoon(),
        sizeCSV.getSpecial(),
        Optional.ofNullable(stockMap.get(sizeCSV.getId())).orElse(0));
  }

  private Product mapProduct(ProductCSV productCSV) {
    return new Product(productCSV.getId(), productCSV.getSequence(), new HashMap<>());
  }
}
