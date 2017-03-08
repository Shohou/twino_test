package shohov.domain.model.loan;

import org.springframework.data.domain.Page;

public interface LoanService {

    void save(Loan loan);

    Page<Loan> getAll(int page, int size);

    Page<Loan> getByUserPersonalId(String personalId);
}
