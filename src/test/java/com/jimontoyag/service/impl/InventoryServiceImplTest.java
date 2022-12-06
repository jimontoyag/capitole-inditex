package com.jimontoyag.service.impl;

import com.jimontoyag.model.Inventory;
import com.jimontoyag.model.Product;
import com.jimontoyag.model.Size;
import com.jimontoyag.repository.InventoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.AbstractMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InventoryServiceImplTest {

  @Mock private InventoryRepository inventoryRepository;

  @InjectMocks private InventoryServiceImpl inventoryService;

  @Test
  public void filterVisibleProductsOrderBySequence_validateOrder() {
    var products =
        Map.ofEntries(
            productVisible(1l, 10),
            productVisible(2l, 5),
            productVisible(3l, 20),
            productVisible(4l, 15));

    when(inventoryRepository.loadInventory()).thenReturn(new Inventory(products));

    var rel = inventoryService.filterVisibleProductsOrderBySequence().toList();

    assertNotNull(rel);
    assertEquals(4, rel.size());
    assertEquals(2l, rel.get(0).id().longValue());
    assertEquals(1l, rel.get(1).id().longValue());
    assertEquals(4l, rel.get(2).id().longValue());
    assertEquals(3l, rel.get(3).id().longValue());
  }

  @Test
  public void filterVisibleProductsOrderBySequence_noStock() {
    var products = Map.ofEntries(productSizeNoStock(2L, 5));

    when(inventoryRepository.loadInventory()).thenReturn(new Inventory(products));

    var rel = inventoryService.filterVisibleProductsOrderBySequence().toList();

    assertNotNull(rel);
    assertEquals(0, rel.size());
  }

  @Test
  public void filterVisibleProductsOrderBySequence_noStockAndStock() {
    var products = Map.ofEntries(productSizeNoStock(2L, 5), productSizeNoStockStock(3l, 10));

    when(inventoryRepository.loadInventory()).thenReturn(new Inventory(products));

    var rel = inventoryService.filterVisibleProductsOrderBySequence().toList();

    assertNotNull(rel);
    assertEquals(1, rel.size());
    assertEquals(3l, rel.get(0).id().longValue());
  }

  @Test
  public void filterVisibleProductsOrderBySequence_StockAndStockSpecial() {
    var products = Map.ofEntries(productSizeStockStockSpecial(11l, 20));

    when(inventoryRepository.loadInventory()).thenReturn(new Inventory(products));

    var rel = inventoryService.filterVisibleProductsOrderBySequence().toList();

    assertNotNull(rel);
    assertEquals(1, rel.size());
    assertEquals(11l, rel.get(0).id().longValue());
  }

  @Test
  public void filterVisibleProductsOrderBySequence_NoStockAndStockSpecial() {
    var products = Map.ofEntries(productSizeNoStockStockSpecial(15l, 20));

    when(inventoryRepository.loadInventory()).thenReturn(new Inventory(products));

    var rel = inventoryService.filterVisibleProductsOrderBySequence().toList();

    assertNotNull(rel);
    assertEquals(0, rel.size());
  }

  @Test
  public void filterVisibleProductsOrderBySequence_NoStockAndStockAndStockSpecial() {
    var products = Map.ofEntries(productSizeNoStockStockStockSpecial(16l, 20));

    when(inventoryRepository.loadInventory()).thenReturn(new Inventory(products));

    var rel = inventoryService.filterVisibleProductsOrderBySequence().toList();

    assertNotNull(rel);
    assertEquals(1, rel.size());
    assertEquals(16l, rel.get(0).id().longValue());
  }

  @Test
  public void filterVisibleProductsOrderBySequence_NoStockSoon() {
    var products = Map.ofEntries(productSizeNoStockSoon(17l, 20));

    when(inventoryRepository.loadInventory()).thenReturn(new Inventory(products));

    var rel = inventoryService.filterVisibleProductsOrderBySequence().toList();

    assertNotNull(rel);
    assertEquals(1, rel.size());
    assertEquals(17l, rel.get(0).id().longValue());
  }

  @Test
  public void filterVisibleProductsOrderBySequence_NoStockSoonAndSpecialNoStockSoon() {
    var products = Map.ofEntries(productSizeNoStockSoonNoStockSpecialSoon(18l, 20));

    when(inventoryRepository.loadInventory()).thenReturn(new Inventory(products));

    var rel = inventoryService.filterVisibleProductsOrderBySequence().toList();

    assertNotNull(rel);
    assertEquals(1, rel.size());
    assertEquals(18l, rel.get(0).id().longValue());
  }

  private Map.Entry<Long, Product> productSizeStockStockSpecial(long id, int sequence) {
    return new AbstractMap.SimpleEntry(
        id,
        new Product(
            id,
            sequence,
            Map.of(1l, new Size(1l, false, false, 1), 2l, new Size(2l, false, true, 1))));
  }

  private Map.Entry<Long, Product> productSizeNoStockSoonNoStockSpecialSoon(long id, int sequence) {
    return new AbstractMap.SimpleEntry(
        id,
        new Product(
            id,
            sequence,
            Map.of(1l, new Size(1l, true, false, 0), 2l, new Size(2l, true, true, 0))));
  }

  private Map.Entry<Long, Product> productSizeNoStockStockStockSpecial(long id, int sequence) {
    return new AbstractMap.SimpleEntry(
        id,
        new Product(
            id,
            sequence,
            Map.of(
                1l,
                new Size(1l, false, false, 0),
                2l,
                new Size(2l, false, true, 1),
                3l,
                new Size(1l, false, false, 30))));
  }

  private Map.Entry<Long, Product> productSizeNoStockStockSpecial(long id, int sequence) {
    return new AbstractMap.SimpleEntry(
        id,
        new Product(
            id,
            sequence,
            Map.of(1l, new Size(1l, false, false, 0), 2l, new Size(2l, false, true, 1))));
  }

  private Map.Entry<Long, Product> productSizeNoStockStock(long id, int sequence) {
    return new AbstractMap.SimpleEntry(
        id,
        new Product(
            id,
            sequence,
            Map.of(1l, new Size(1l, false, false, 0), 2l, new Size(2l, false, false, 1))));
  }

  private Map.Entry<Long, Product> productSizeNoStock(long id, int sequence) {
    return new AbstractMap.SimpleEntry(
        id, new Product(id, sequence, Map.of(1l, new Size(1l, false, false, 0))));
  }

  private Map.Entry<Long, Product> productSizeNoStockSoon(long id, int sequence) {
    return new AbstractMap.SimpleEntry(
        id, new Product(id, sequence, Map.of(1l, new Size(1l, true, false, 0))));
  }

  private Map.Entry<Long, Product> productVisible(long id, int sequence) {
    return new AbstractMap.SimpleEntry(
        id, new Product(id, sequence, Map.of(1l, new Size(1l, false, false, 1))));
  }
}
