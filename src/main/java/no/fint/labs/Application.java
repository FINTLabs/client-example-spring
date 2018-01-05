package no.fint.labs;

import no.fint.model.felles.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

@EnableHypermediaSupport(type=HAL)
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
//            String text = restTemplate.getForObject("https://beta.felleskomponent.no/administrasjon/personal/person", String.class);
//            System.out.println(text);
            //ResponseEntity<Resources<Person>> result = restTemplate.exchange("https://beta.felleskomponent.no/administrasjon/personal/person", HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Person>>(){});
            ResponseEntity<Resources<Resource<Person>>> result = restTemplate.exchange("https://beta.felleskomponent.no/administrasjon/personal/person", HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Resource<Person>>>(){});
            System.out.println("result.getStatusCode() = " + result.getStatusCode());
            System.out.println("result.getBody() = " + result.getBody());

        };
    }
}
