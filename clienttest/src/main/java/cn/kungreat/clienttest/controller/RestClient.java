package cn.kungreat.clienttest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/rest-client")
public class RestClient {

    private final RestTemplate restTemplate;

    @Autowired
    public RestClient(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public Map echo() {
        return restTemplate.getForObject("http://flybbs-base/api/index", Map.class);
    }
}
