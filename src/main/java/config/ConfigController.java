package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class ConfigController {

    @Value("#{environment.DB_URL}")
    private String dbUrl;

    @GetMapping("/data")
    @ResponseStatus(HttpStatus.OK)
    public String getData() {
        return "environment variable: " + dbUrl;
    }

}
