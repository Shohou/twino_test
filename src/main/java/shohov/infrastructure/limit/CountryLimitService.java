package shohov.infrastructure.limit;

public interface CountryLimitService {

    boolean isLimitReached(String countryCode);
}
