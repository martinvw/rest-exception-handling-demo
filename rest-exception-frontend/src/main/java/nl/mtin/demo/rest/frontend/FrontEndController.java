package nl.mtin.demo.rest.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nl.mtin.demo.rest.shared.Greeting;

@Controller
@ResponseBody
public class FrontEndController {
	@Autowired
	RestTemplate restTemplate;

	@RequestMapping("/")
	String home() {	        
		Greeting greeting = restTemplate.getForObject("http://localhost:8090/greeting?name=World!", Greeting.class);
		return greeting.toString();
	}

	@RequestMapping("/hello-{name}")
	String welcome(@PathVariable(required = false) String name) {
		Greeting greeting = restTemplate.getForObject("http://localhost:8090/greeting?name=" + name, Greeting.class);
		return greeting.toString();
	}


	@RequestMapping("/missing")
	String missing() {
		Greeting greeting = restTemplate.getForObject("http://localhost:8090/greeting", Greeting.class);
		return greeting.toString();
	}

	@RequestMapping("/notfound")
	String notfound() {
		Greeting greeting = restTemplate.getForObject("http://localhost:8090/notfound", Greeting.class);
		return greeting.toString();
	}

	@RequestMapping("/tooshort")
	String toshort() {
		Greeting greeting = restTemplate.getForObject("http://localhost:8090/greeting?name=shr", Greeting.class);
		return greeting.toString();
	}
}
