package com.jimontoyag.repository.impl;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InventoryCSVRepositoryTest {

  private InventoryCSVRepository inventoryCSVRepository;

  @Test
  public void loadInventory() throws FileNotFoundException {
    inventoryCSVRepository =
        new InventoryCSVRepository(
            loadResourceFile("product.csv"),
            loadResourceFile("size.csv"),
            loadResourceFile("stock.csv"));
    var rel = inventoryCSVRepository.loadInventory();

    assertNotNull(rel);
    assertEquals(5, rel.products().size());
    assertEquals(10, rel.products().get(3l).sizes().get(31l).quantity());
    assertEquals(10, rel.products().get(5l).sizes().get(53l).quantity());
  }

  private File loadResourceFile(String file) {
    return new File(getClass().getClassLoader().getResource(file).getFile());
  }
}
