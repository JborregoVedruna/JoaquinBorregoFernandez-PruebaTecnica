package com.caixabank.loansmanager.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageModel<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int numberOfElements;
    private int size;
    private int number;
}
