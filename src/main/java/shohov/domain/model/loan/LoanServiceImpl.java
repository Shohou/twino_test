package shohov.domain.model.loan;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public void save(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public Page<Loan> getAll(int page, int size) {
        return null;
    }

    @Override
    public Page<Loan> getByUserPersonalId(String personalId) {
        return null;
    }
}
