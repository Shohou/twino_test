package shohov.integrations.geoip;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestOperations;

import java.util.concurrent.CompletableFuture;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.springframework.http.HttpStatus.OK;
import static shohov.util.CompletableFutureHelper.listenableToCompletable;

@Service
public class CountryServiceImpl implements CountryService {

    private static final String DEFAULT_COUNTRY = "LV";

    private final String geoIpServiceUrlTemplate;
    private final AsyncRestOperations restTemplate;

    public CountryServiceImpl(@Value("${country.service.geoip.service.url.template}") String geoIpServiceUrlTemplate,
            AsyncRestOperations restTemplate) {
        this.geoIpServiceUrlTemplate = geoIpServiceUrlTemplate;
        this.restTemplate = restTemplate;
    }

    @Override
    public CompletableFuture<String> getCountryBy(String ip) {
        ListenableFuture<ResponseEntity<FreeGeoIpNetResponse>> future =
                restTemplate.getForEntity(geoIpServiceUrlTemplate, FreeGeoIpNetResponse.class, ip);
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
