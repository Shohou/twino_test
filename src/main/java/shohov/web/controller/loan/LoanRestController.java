package shohov.web.controller.loan;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import shohov.domain.model.loan.Loan;
import shohov.domain.model.loan.LoanService;
import shohov.util.HttpHelper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/loan")
public class LoanRestController {

    private static final String DEFAULT_PAGE_NUM = "0";
    private static final String DEFAULT_PAGE_SIZE = "20";

    private final LoanService loanService;

    public LoanRestController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PutMapping(consumes = {"application/json"})
    //TODO: implement validation
    public void applyForLoan(@RequestBody @Valid Loan loan, HttpServletRequest request) {
        String ip = HttpHelper.getClientIpAddress(request);

        loanService.save(loan, ip);
    }

    @GetMapping(produces = {"application/json"})
    public Page<Loan> getAll(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUM) int page,
                             @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) int size) {
        return loanService.getAll(page, size);
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public Page<Loan> getByUserPersonalId(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUM) int page,
                                @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
                                String personalId) {
        return loanService.getByUserPersonalId(personalId);
    }
}
