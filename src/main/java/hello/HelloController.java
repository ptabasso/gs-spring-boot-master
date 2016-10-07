package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private String infos;

    @RequestMapping("/")
    public String index() {

        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        log.info(quote.toString());
        System.out.println(quote.toString());

        return quote.toString();
    }
    
}
