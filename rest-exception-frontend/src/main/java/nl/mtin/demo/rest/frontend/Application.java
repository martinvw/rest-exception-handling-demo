package nl.mtin.demo.rest.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {
	@Bean
	public RestTemplate restTemplate(){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new SpringServerErrorResponseHandler());
        return restTemplate;
	}

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
