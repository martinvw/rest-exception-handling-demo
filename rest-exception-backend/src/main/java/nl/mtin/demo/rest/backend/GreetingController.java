package nl.mtin.demo.rest.backend;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import  nl.mtin.demo.rest.shared.Greeting;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name") String name) {
    	if (name.length() < 4) throw new IllegalArgumentException("Name should be longer than 4 characters.");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
