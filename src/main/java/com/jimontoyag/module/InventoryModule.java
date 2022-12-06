package com.jimontoyag.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.jimontoyag.controller.InventoryController;
import com.jimontoyag.controller.impl.InventoryControllerImpl;
import com.jimontoyag.repository.InventoryRepository;
import com.jimontoyag.repository.impl.InventoryCSVRepository;
import com.jimontoyag.service.InventoryService;
import com.jimontoyag.service.impl.InventoryServiceImpl;

import java.io.File;

public class InventoryModule extends AbstractModule {

  private final String productCsvURL;
  private final String sizeCsvURL;
  private final String stockCsvURL;

  public InventoryModule(String productCsvURL, String sizeCsvURL, String stockCsvURL) {
    this.productCsvURL = productCsvURL;
    this.sizeCsvURL = sizeCsvURL;
    this.stockCsvURL = stockCsvURL;
  }

  @Override
  protected void configure() {
    bind(InventoryRepository.class).to(InventoryCSVRepository.class).asEagerSingleton();
    bind(InventoryService.class).to(InventoryServiceImpl.class).asEagerSingleton();
    bind(InventoryController.class).to(InventoryControllerImpl.class).asEagerSingleton();
  }

  @Provides
  @Named("productCSVFile")
  File productCSVFile() {
    return new File(productCsvURL);
  }

  @Provides
  @Named("sizeCSVFile")
  File sizeCSVFile() {
    return new File(sizeCsvURL);
  }

  @Provides
  @Named("stockCSVFile")
  File stockCSVFile() {
    return new File(stockCsvURL);
  }
}
