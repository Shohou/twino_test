package shohov.integrations.geoip;

import java.util.concurrent.CompletableFuture;

public interface CountryService {

    CompletableFuture<String> getCountryBy(String ip);
}
