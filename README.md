# client-example-spring
FINT Example Client using Spring

Example client for accessing FINT APIs, using Spring Framework for OAuth, HTTP, and JSON HAL HATEOAS parsing.

## OAuth Configuration

The properties used for authorization are stored in `application.yml`, 
under the key `security.oauth2.client`. The configuration is specified by
the `@Bean` `restTemplate` in `no.fint.labs.Config` and autowired into `Application`.

## HATEOAS JSON handling

This is done by a Spring HATEOAS library, enabled by the `@EnableHypermediaSupport(type=HAL)`
annotation in `Application`.  It also uses the FINT generated model classes from [FINTmodels](//github.com/FINTmodels).

To get HATEOAS resources with links, the model class is wrapped in `Resources<Resource<?>>`.
This is all wrapped in `ParameterizedTypeResource<?>` to instruct the Spring RestTemplate and Jackson to
deserialize into the correct object.
