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
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Telefonliste {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final URI endpoint;

    public Telefonliste(RestTemplate restTemplate, ObjectMapper objectMapper, URI endpoint) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.endpoint = endpoint;
    }

    @GetMapping("/telefonliste")
    public ResponseEntity getTelefonliste() {
        ResponseEntity<PersonResources> result = restTemplate.getForEntity(endpoint, PersonResources.class);

        List<ObjectNode> phones = result
                .getBody()
                .getContent()
                .stream()
                .map(this::kontakt)
                .collect(Collectors.toList());
        return ResponseEntity.ok(phones);
    }

    public ObjectNode kontakt(PersonResource p) {
        ObjectNode result = objectMapper.createObjectNode();
        result.put("navn", PersonUtil.getNavn(p.getNavn()));
        result.put("mobil", p.getKontaktinformasjon().getMobiltelefonnummer());
        return result;
    }

}
