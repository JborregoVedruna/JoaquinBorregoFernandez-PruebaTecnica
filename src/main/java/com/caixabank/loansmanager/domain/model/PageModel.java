package com.caixabank.loansmanager.domain.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo genérico para representar una página de resultados con metadatos de paginación.
 *
 * @param <T> El tipo de contenido de la página.
 *     <p>{@code @Data}: Métodos boilerplate. {@code @NoArgsConstructor}: Constructor vacío.
 *     {@code @AllArgsConstructor}: Constructor completo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageModel<T> implements Serializable {
  private static final long serialVersionUID = 1L;

  /** El listado de elementos contenidos en la página actual. */
  private List<T> content;

  /** El número total de elementos existentes en todas las páginas. */
  private long totalElements;

  /** El número total de páginas generadas según el tamaño solicitado. */
  private int totalPages;

  /** El número de elementos presentes específicamente en esta página. */
  private int numberOfElements;

  /** El tamaño máximo de elementos solicitado por página. */
  private int size;

  /** El índice de la página actual (empezando por cero). */
  private int number;
}
