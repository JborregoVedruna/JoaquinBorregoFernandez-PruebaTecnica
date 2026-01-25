package com.caixabank.loansmanager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageableModel {
    private int page;
    private int size;
}
