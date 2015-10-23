package nl.mtin.demo.rest.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.client.RestTemplate;

import  nl.mtin.demo.rest.shared.Greeting;

@SpringBootApplication
@Controller
@ResponseBody
public class Application {
    @RequestMapping("/")
    String home() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new SpringServerErrorResponseHandler());
        Greeting greeting = restTemplate.getForObject("http://localhost:8090/greeting?name=World!", Greeting.class);
        return greeting.toString();
    }

    @RequestMapping("/notfound")
    String notfound() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new SpringServerErrorResponseHandler());
        Greeting greeting = restTemplate.getForObject("http://localhost:8090/notfound", Greeting.class);
        return greeting.toString();
    }

    @RequestMapping("/toshort")
    String toshort() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new SpringServerErrorResponseHandler());
        Greeting greeting = restTemplate.getForObject("http://localhost:8090/greeting?name=shr", Greeting.class);
        return greeting.toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
