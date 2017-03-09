package shohov.web.controller.loan;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import shohov.domain.model.loan.Loan;
import shohov.domain.model.loan.LoanService;
import shohov.integrations.geoip.CountryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static shohov.util.CompletableFutureHelper.completableToDeferred;
import static shohov.util.HttpHelper.getClientIpAddress;

@RestController
@RequestMapping("/loan")
public class LoanRestController {

    private static final String DEFAULT_PAGE_NUM = "0";
    private static final String DEFAULT_PAGE_SIZE = "20";

    private static final long REQUEST_TIMEOUT = 10000;

    private final LoanService loanService;
    private final CountryService countryService;

    public LoanRestController(LoanService loanService, CountryService countryService) {
        this.loanService = loanService;
        this.countryService = countryService;
    }

    @PutMapping(consumes = {"application/json"})
    //TODO: implement validation
    public DeferredResult<Void> applyForLoan(@RequestBody @Valid Loan loan, HttpServletRequest request) {
        return completableToDeferred(countryService.getCountryBy(getClientIpAddress(request))
                .thenAccept(countryCode -> {
                    if (blacklistService.isBlacklisted(loan.getPersonalId())) {
                        throw new BLABLA;
                    }
                    if (countryLimit.isCountryLimitReached(countryCode)) {
                        throw new something;
                    }
                    loan.setCountryCode(countryCode);
                    loanService.save(loan);
                }));
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
