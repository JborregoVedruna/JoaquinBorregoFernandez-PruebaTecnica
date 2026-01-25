package com.caixabank.loansmanager.infrastructure.adapters.outbound.converters;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.LoanApplicationEntity;

@Mapper(componentModel = "spring")
public interface OutboundConverter {

    public LoanApplicationModel toModel(LoanApplicationEntity entity);

    public LoanApplicationEntity toEntity(LoanApplicationModel model);

    public default Pageable toPageable(
            PageableModel pageableModel) {
        return PageRequest.of(pageableModel.getPage(), pageableModel.getSize());
    }

    public default PageModel<LoanApplicationModel> toLoanApplicationModelPage(
            Page<LoanApplicationEntity> page) {
        return new PageModel<>(
                page.getContent().stream().map(this::toModel).toList(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.getSize(),
                page.getNumber());
    }
}
