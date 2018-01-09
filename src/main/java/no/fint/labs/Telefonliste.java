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

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Telefonliste {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/telefonliste")
    public ResponseEntity getTelefonliste() {
        ResponseEntity<Resources<Person>> result = restTemplate.exchange("https://beta.felleskomponent.no/administrasjon/personal/person", HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Person>>(){});
        List<ObjectNode> phones = result.getBody().getContent().stream().map(this::kontakt).collect(Collectors.toList());
        return ResponseEntity.ok(phones);
    }

    public ObjectNode kontakt(Person p) {
        ObjectNode result = objectMapper.createObjectNode();
        result.put("navn", PersonUtil.getNavn(p.getNavn()));
        result.put("mobil", p.getKontaktinformasjon().getMobiltelefonnummer());
        return result;
    }

}
