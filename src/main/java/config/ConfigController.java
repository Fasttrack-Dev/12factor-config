package config;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.TranscoderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class ConfigController {

    Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Value("#{environment.DB_URL}")
    private String dbUrl;

    @Autowired
    private MemcachedClient memcachedClient;


    @GetMapping("/data")
    @ResponseStatus(HttpStatus.OK)
    public String getData() {
        String body = "url is " + dbUrl + "\n";
        return "environment variable: " + dbUrl;
    }

    @GetMapping("/get")
    public String getValue(@RequestParam String key) {
        logger.info("Getting key {}", key);
        return (String) memcachedClient.get(key);
    }

    @PutMapping("/set")
    public String setValue(@RequestParam String key, @RequestParam String value) {
        logger.info("Setting key {} to value {}", key, value);
        memcachedClient.set(key, 0, value);
        return value;
    }

}
