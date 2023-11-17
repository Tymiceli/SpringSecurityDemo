package com.coderscampus.security.demo.domain;

import java.math.BigDecimal;

public record Product(
		Integer id,
		String name,
		BigDecimal price) {}
