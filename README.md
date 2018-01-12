# client-example-spring
FINT Example Client using Spring

Example web application for creating cascading web APIs using FINT APIs.
It uses Spring Framework for OAuth, HTTP, JSON HAL HATEOAS parsing, and web container.

The purpose of this application is to demonstrate how to interact with FINT APIs,
and provide some basic ideas of services that can be built.

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

## Web Container

The Web container has two REST controllers configured, `Bursdager` and `Telefonliste`. They serve the following REST endpoints:

- `/bursdager` - Provides a list of people with birthday today
- `/jubilanter` - Provides a list of people with an anniversary birthday in the near future
- `/telefonliste` - Provides a list of people's mobile phone numbers.