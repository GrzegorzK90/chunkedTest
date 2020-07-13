package pl.kulinski.marketo.marketo.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.kulinski.marketo.marketo.config.MarketoUrl;
import pl.kulinski.marketo.marketo.dto.ResponseOfIdentity;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MarketoClient {

    private final RestTemplate restTemplate;
    private String clientCredentials = "grant_type=client_credentials";
    private String clientId = "client_id=";
    private String clientSecret = "client_secret=";
    private String accessToken = "?access_token=";

    private String getToken() {
        String url = MarketoUrl.marketoBasicUrl
                + MarketoUrl.tokenUrl
                + "?" + clientCredentials
                + "&" + clientId
                + "&" + clientSecret;
        ResponseOfIdentity response = restTemplate.getForObject(url, ResponseOfIdentity.class);
        return response.getAccessToken();
    }

    public String uploadLead() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource("test.csv"));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String serverUrl = MarketoUrl.marketoBasicUrl
                + MarketoUrl.bulkLeadUrl
                + accessToken
                + getToken()
                + "&format=csv"
                + "&lookupField=smavaId";

        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
        return response.getBody();
    }

}
