package com.caixabank.loansmanager.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class LoanApplicationModelTest {
  @Test
  void testLoanApplicationModelProperties() {
    LoanApplicationModel model = new LoanApplicationModel();
    UUID uuid = UUID.randomUUID();
    model.setUuid(uuid);
    model.setRequestedAmount(new BigDecimal("100.50"));

    assertEquals(uuid, model.getUuid());
    assertEquals(new BigDecimal("100.50"), model.getRequestedAmount());
  }

  @Test
  void equalsAndHashCode_ShouldBeConsistent() {
    LoanApplicationModel m1 = new LoanApplicationModel();
    m1.setUuid(UUID.randomUUID());
    LoanApplicationModel m2 = new LoanApplicationModel();
    m2.setUuid(m1.getUuid());
    LoanApplicationModel m3 = new LoanApplicationModel();
    m3.setUuid(UUID.randomUUID());

    assertEquals(m1, m2);
    assertNotEquals(m1, m3);
    assertEquals(m1.hashCode(), m2.hashCode());
    assertNotNull(m1.toString());
  }
}
