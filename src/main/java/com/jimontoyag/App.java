package com.jimontoyag;

import com.google.inject.Guice;
import com.jimontoyag.controller.InventoryController;
import com.jimontoyag.module.InventoryModule;

/** Hello world! */
public class App {
  public static void main(String[] args) {
    if (args != null && args.length != 3) {
      throw new IllegalArgumentException(
          "Three arguments are required: productCsvURL sizeCsvURL stockCsvURL");
    }
    var injector = Guice.createInjector(new InventoryModule(args[0], args[1], args[2]));

    var inventoryController = injector.getInstance(InventoryController.class);

    var rel = inventoryController.showValidOrderedProductsIds();

    System.out.println(rel);
  }
}
