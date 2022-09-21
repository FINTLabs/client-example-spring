package no.fint.labs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import java.net.URI;

@Configuration
public class Config {

    public static final String ENDPOINT_ADMINISTRASJON_PERSONAL_PERSON = "{endpoint}/administrasjon/personal/person";

    public final Logger logger = LoggerFactory.getLogger("no.fint.labs.backend");

    @Bean("endpoint")
    @Value("${fint.felleskomponent.uri}")
    public URI endpoint(String uri) {
        return URI.create(ENDPOINT_ADMINISTRASJON_PERSONAL_PERSON.replace("{endpoint}", uri));
    };

    @Bean
    @ConfigurationProperties("security.oauth2.client")
    public OAuth2ProtectedResourceDetails resourceDetails() {
        return new ResourceOwnerPasswordResourceDetails();
    }

    @Bean
    public OAuth2RestTemplate restTemplate(OAuth2ClientContext clientContext) {
        OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails(), clientContext);
        AccessTokenProvider accessTokenProvider = new ResourceOwnerPasswordAccessTokenProvider();
        template.setAccessTokenProvider(accessTokenProvider);
        template.getInterceptors().add(((request, body, execution) -> {
            logger.info("Requesting {} ...", request.getURI());
            return execution.execute(request, body);
        }));
        return template;
    }

}
