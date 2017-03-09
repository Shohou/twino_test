package shohov.integrations.geoip;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.concurrent.CompletableFuture;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.springframework.http.HttpStatus.OK;
import static shohov.util.CompletableFutureHelper.listenableToCompletable;

@Service
public class CountryServiceImpl implements CountryService {

    private static final String GEOIP_SERVICE_URL = "http://freegeoip.net/json/{ip}";
    private static final String DEFAULT_COUNTRY = "LV";

    private final AsyncRestTemplate restTemplate = new AsyncRestTemplate();

    @Override
    public CompletableFuture<String> getCountryBy(String ip) {
        ListenableFuture<ResponseEntity<FreeGeoIpNetResponse>> future =
                restTemplate.getForEntity(GEOIP_SERVICE_URL, FreeGeoIpNetResponse.class, ip);
        return listenableToCompletable(future, responseEntity -> {
            if (responseEntity.getStatusCode() == OK) {
                String countryCode = responseEntity.getBody().getCountryCode();
                if (!isNullOrEmpty(countryCode)) {
                    return countryCode;
                }
            }
            return DEFAULT_COUNTRY;
        });
    }
}
