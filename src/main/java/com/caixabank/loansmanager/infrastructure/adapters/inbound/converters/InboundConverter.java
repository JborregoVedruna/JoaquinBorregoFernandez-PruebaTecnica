package com.caixabank.loansmanager.infrastructure.adapters.inbound.converters;

import java.time.LocalDateTime;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.LoanStatus;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input.LoanApplicationInput;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;

@Mapper(componentModel = "spring")
public interface InboundConverter {

    LoanApplicationOutput toLoanApplicationOutput(LoanApplicationModel loanApplication);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    LoanApplicationModel toLoanApplicationModel(LoanApplicationInput loanApplicationInput);

    @AfterMapping
    public default void initializeDefaultValues(@MappingTarget LoanApplicationModel entity) {
        entity.setCreatedDate(LocalDateTime.now());
        entity.setStatus(LoanStatus.PENDING);
    }

    public default PageableModel toPageableModel(Pageable pageable) {
        return new PageableModel(pageable.getPageNumber(), pageable.getPageSize());
    }

    public default Page<LoanApplicationOutput> toLoanApplicationOutputPage(
            PageModel<LoanApplicationModel> pageModel) {
        return new PageImpl<>(
                pageModel.getContent().stream()
                        .map(this::toLoanApplicationOutput)
                        .toList(),
                PageRequest.of(pageModel.getNumber(), pageModel.getSize()),
                pageModel.getTotalElements());
    }

}
