package com.jimontoyag.model;

import java.util.Map;

public record Inventory(Map<Long, Product> products) {
}
