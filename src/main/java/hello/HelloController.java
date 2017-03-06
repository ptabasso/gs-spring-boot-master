package hello;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private String infos;



    @RequestMapping("/value")
    private static void createEmployee()
    {
        final String uri = "http://localhost:8082/value";

        Value newValue = new Value(12L, "Adam");

        RestTemplate restTemplate = new RestTemplate();
        Value value = restTemplate.postForObject(uri, newValue, Value.class);

        System.out.println(newValue);
    }


    @RequestMapping("/")
    public String index(HttpServletRequest request) {

        RestTemplate restTemplate = new RestTemplate();

        //Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        Quote quote = restTemplate.getForObject("http://localhost:8082/greeting", Quote.class);
        log.info(quote.toString());
        System.out.println(quote.toString());

        //HttpHeaders httpHeaders = restTemplate.headForHeaders("http://localhost:8082/greeting");
        //System.out.println("Tentative"+httpHeaders.toString());


        return quote.toString();
    }


    @RequestMapping("/bis")
    public String bis(HttpServletRequest req) {

//        String url = "http://localhost:8082/greeting";

        final String uri = "http://localhost:8082/greeting";


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        //ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        System.out.println(result);


        String resultString = result.getBody();
        HttpHeaders head = result.getHeaders();

        System.out.println("resultString"+resultString);
        System.out.println("headers"+head);

        return result.toString();
    }

    @RequestMapping("/divers")
    public String hi() {
        log.info("Using another route");
        System.out.println("Using another route");
        return "Using another route";
    }


    @RequestMapping("/listen")
    public String listen() throws ExecutionException, InterruptedException {
        AsyncRestTemplate asycTemp = new AsyncRestTemplate();
		/*String url1 ="http://google.com";
        String url2 ="http://www.yahoo.fr";*/
        String url3 = "http://localhost:8082/greeting";

        HttpMethod method = HttpMethod.GET;
        Class<String> responseType = String.class;

        //create request entity using HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> requestEntity = new HttpEntity<String>("params", headers);
		/*ListenableFuture<ResponseEntity<String>> future1 = asycTemp.exchange(url1, method, requestEntity, responseType);
        ListenableFuture<ResponseEntity<String>> future2 = asycTemp.exchange(url2, method, requestEntity, responseType);*/
        ListenableFuture<ResponseEntity<String>> future3 = asycTemp.exchange(url3, method, requestEntity, responseType);

        //Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        //Checker difference entre getForEntity, getForObject et exchange


        //waits for the result
			/*ResponseEntity<String> entity1 = future1.get();
            ResponseEntity<String> entity2 = future2.get();*/
        ResponseEntity<String> entity3 = future3.get();

        //prints body source code for the given URL
			/*System.out.println(entity1.getBody());
            System.out.println(entity2.getBody());*/
        System.out.println(entity3.getBody());

        log.info("Using listen");
        System.out.println("Using listen");

        return entity3.getBody().toString();
    }

    @RequestMapping("/getforobj")
    public String getforobj() throws ExecutionException, InterruptedException {

        AsyncRestTemplate asTemp = new AsyncRestTemplate();
        String url0 = "http://localhost:8082/greeting";
        HttpMethod method = HttpMethod.GET;
        Class<String> responseType = String.class;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> requestEntity = new HttpEntity<String>("params", headers);
        //ListenableFuture<ResponseEntity<String>> future0 = asTemp.exchange(url0, method, requestEntity, responseType);
        ListenableFuture<ResponseEntity<String>> future0 = asTemp.getForEntity(url0, responseType);
        //waits for the result

        ResponseEntity<String> entity0 = future0.get();

        //prints body source code for the given URL

        System.out.println(entity0.getBody());

        log.info("Using listen");
        System.out.println("Using listen");

        return entity0.getBody().toString();
    }

    @RequestMapping("/future")
    public String future() throws ExecutionException, InterruptedException {
        AsyncRestTemplate restTemplate = new AsyncRestTemplate();
        String url0 = "http://localhost:8082/greeting";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        String cookie = "Anything";
        requestHeaders.add("Cookie", cookie);

        System.out.println(requestHeaders);

        HttpEntity<String> entity = new HttpEntity<String>("parameters", requestHeaders);

        /*Future<ResponseEntity<String>> futureEntity = restTemplate.exchange(
                baseUrl + "/rest/user/{key}", HttpMethod.GET, entity,
                String.class, "0");*/

        Future<ResponseEntity<String>> futureEntity = restTemplate.exchange(url0, HttpMethod.GET, entity, String.class, "0");

        log.info("Doing other async stuff...");

        log.info("Blocking to receive response...");
        ResponseEntity<String> result = null;

        result = futureEntity.get();
        log.info("Response received");
        System.out.println(result.getBody());

        return result.getBody().toString();
    }


    @RequestMapping("/artpost")
    public String artpost() throws ExecutionException, InterruptedException {

        AsyncRestTemplate asTemp = new AsyncRestTemplate();
        String url0 = "http://localhost:8082/value";
        HttpMethod method = HttpMethod.POST;
//        Class<String> responseType = String.class;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        Value val = new Value(46L, "Pej");

        HttpEntity<Value> httpRequest = new HttpEntity<>(val);
        //ListenableFuture<ResponseEntity<String>> future0 = asTemp.exchange(url0, method, requestEntity, responseType);
//        ListenableFuture<ResponseEntity<String>> future0 = asTemp.getForEntity(url0, responseType);

        ListenableFuture<ResponseEntity<Value>> httpResponse = asTemp.postForEntity(url0, httpRequest, Value.class);
        //waits for the result

        ResponseEntity<Value> entity0 = httpResponse.get();


        //prints body source code for the given URL

        Thread.sleep(2000);

        System.out.println(httpResponse.get().getBody());

        //System.out.println(entity0.getBody());

        log.info("Using listen");
        System.out.println("Using listen");

        return entity0.getBody().toString();
    }

}