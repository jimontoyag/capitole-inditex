package com.jimontoyag.controller;

import com.jimontoyag.controller.impl.InventoryControllerImpl;
import com.jimontoyag.model.Product;
import com.jimontoyag.service.InventoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InventoryControllerImplTest {

  @Mock private InventoryService inventoryService;

  @InjectMocks private InventoryControllerImpl inventoryController;

  @Test
  public void showValidOrderedProductsIds_valid() {
    when(inventoryService.filterVisibleProductsOrderBySequence())
        .thenReturn(
            Stream.of(
                new Product(5l, 0, null), new Product(1l, 0, null), new Product(3l, 0, null)));

    var rel = inventoryController.showValidOrderedProductsIds();
    assertEquals("5,1,3", rel);
  }

  @Test
  public void showValidOrderedProductsIds_empty() {
    when(inventoryService.filterVisibleProductsOrderBySequence()).thenReturn(Stream.empty());

    var rel = inventoryController.showValidOrderedProductsIds();
    assertEquals("", rel);
  }

  @Test
  public void showValidOrderedProductsIds_one() {
    when(inventoryService.filterVisibleProductsOrderBySequence())
        .thenReturn(Stream.of(new Product(3l, 0, null)));

    var rel = inventoryController.showValidOrderedProductsIds();
    assertEquals("3", rel);
  }
}
