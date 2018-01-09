package no.fint.labs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import no.fint.model.felles.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Bursdager {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/bursdager")
    public ResponseEntity bursdager() {
        ResponseEntity<Resources<Person>> result = restTemplate.exchange("https://beta.felleskomponent.no/administrasjon/personal/person", HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Person>>(){});
        List<ObjectNode> bursdager = result.getBody().getContent().stream().filter(p -> getYears(p) % 10 == 9).map(this::jubilant).collect(Collectors.toList());
        return ResponseEntity.ok(bursdager);
    }

    private int getYears(Person p) {
        return Period.between(LocalDateTime.ofInstant(p.getFodselsdato().toInstant(), ZoneId.systemDefault()).toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
    }

    private ObjectNode jubilant(Person p) {
        ObjectNode result = objectMapper.createObjectNode();
        result.put("navn", PersonUtil.getNavn(p.getNavn()));
        result.put("alder", getYears(p)+1);
        result.put("dato", String.format("%Td %<Tb", p.getFodselsdato()));
        return result;
    }

}
