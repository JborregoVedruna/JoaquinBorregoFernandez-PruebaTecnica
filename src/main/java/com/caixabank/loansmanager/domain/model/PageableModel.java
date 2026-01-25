package com.caixabank.loansmanager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo que encapsula los parámetros de una petición de paginación.
 *
 * <p>{@code @Data}: Métodos de acceso automáticos. {@code @NoArgsConstructor}: Constructor
 * predeterminado. {@code @AllArgsConstructor}: Constructor con todos los campos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageableModel {
  /** El número de página solicitado (empezando por 0). */
  private int page;

  /** La cantidad de elementos por página deseada. */
  private int size;
}
