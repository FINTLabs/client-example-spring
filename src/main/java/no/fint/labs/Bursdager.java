package no.fint.labs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import no.fint.model.resource.felles.PersonResource;
import no.fint.model.resource.felles.PersonResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Bursdager {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final URI endpoint;

    public Bursdager(RestTemplate restTemplate, ObjectMapper objectMapper, URI endpoint) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.endpoint = endpoint;
    }

    @GetMapping("/jubilanter")
    public ResponseEntity jubilanter() {

        ResponseEntity<PersonResources> result = restTemplate.getForEntity(endpoint, PersonResources.class);

        List<ObjectNode> jubilanter = result
                .getBody()
                .getContent()
                .stream()
                .filter(person -> (getYears(person) + 1) % 10 == 0)
                .map(this::jubilant)
                .collect(Collectors.toList());

        return ResponseEntity.ok(jubilanter);
    }

    private int getYears(PersonResource p) {
        return Period.between(
                LocalDateTime.ofInstant(p.getFodselsdato().toInstant(), ZoneId.systemDefault()).toLocalDate(),
                LocalDateTime.now().toLocalDate()
        ).getYears();
    }

    private ObjectNode jubilant(PersonResource p) {
        ObjectNode result = objectMapper.createObjectNode();
        result.put("navn", PersonUtil.getNavn(p.getNavn()));
        result.put("alder", getYears(p) + 1);
        result.put("dato", String.format("%Td %<Tb", p.getFodselsdato()));
        return result;
    }

    @GetMapping("/bursdager")
    public ResponseEntity bursdager() {

        ResponseEntity<PersonResources> result = restTemplate.getForEntity(endpoint, PersonResources.class);
        final Date today = new Date();

        List<ObjectNode> bursdager = result
                .getBody()
                .getContent()
                .stream()
                .filter(p -> p.getFodselsdato().getDate() == today.getDate() && p.getFodselsdato().getMonth() == today.getMonth())
                .map(this::jubilant)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bursdager);
    }

}
