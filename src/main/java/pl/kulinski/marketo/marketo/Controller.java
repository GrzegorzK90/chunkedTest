package pl.kulinski.marketo.marketo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kulinski.marketo.marketo.client.MarketoClient;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class Controller {

    private final MarketoClient marketoClient;

    @GetMapping("test")
    public String test() {
        try {
            return marketoClient.uploadLead();
        } catch (IOException e) {
            return "error " + e.getMessage();
        }
    }
}
