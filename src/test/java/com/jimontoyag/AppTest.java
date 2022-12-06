package com.jimontoyag;

import com.google.inject.Guice;
import com.jimontoyag.controller.InventoryController;
import com.jimontoyag.module.InventoryModule;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/** Unit test for simple App. */
public class AppTest extends TestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public AppTest(String testName) {
    super(testName);
  }

  /** @return the suite of tests being tested */
  public static Test suite() {
    return new TestSuite(AppTest.class);
  }

  /** Rigourous Test :-) */
  public void testApp() {

    var injector =
        Guice.createInjector(
            new InventoryModule(fileURL("product.csv"), fileURL("size.csv"), fileURL("stock.csv")));

    var inventoryController = injector.getInstance(InventoryController.class);

    var rel = inventoryController.showValidOrderedProductsIds();

    assertEquals("5,1,3", rel);
  }

  private String fileURL(String fileName) {
    return getClass().getClassLoader().getResource(fileName).getFile();
  }
}
