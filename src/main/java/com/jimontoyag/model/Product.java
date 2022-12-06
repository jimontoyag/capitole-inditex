package com.jimontoyag.model;

import java.util.Map;

public record Product(Long id, int sequence, Map<Long, Size> sizes) {}
