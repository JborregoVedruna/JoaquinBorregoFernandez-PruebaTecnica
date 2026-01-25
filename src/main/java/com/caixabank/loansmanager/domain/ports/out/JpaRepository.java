package com.caixabank.loansmanager.domain.ports.out;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import java.util.UUID;

public interface JpaRepository {
  LoanApplicationModel save(LoanApplicationModel loanApplication);

  PageModel<LoanApplicationModel> findAll(PageableModel pageableModel);

  LoanApplicationModel findById(UUID uuid);

  LoanApplicationModel update(LoanApplicationModel loanApplication);
}
