package com.caixabank.loansmanager.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class PageModelTest {
  @Test
  void testPageModel() {
    List<String> content = List.of("a", "b");
    PageModel<String> page = new PageModel<>(content, 10L, 2, 2, 5, 0);

    assertEquals(content, page.getContent());
    assertEquals(10L, page.getTotalElements());
    assertEquals(2, page.getTotalPages());
    assertEquals(2, page.getNumberOfElements());
    assertEquals(5, page.getSize());
    assertEquals(0, page.getNumber());
  }
}
