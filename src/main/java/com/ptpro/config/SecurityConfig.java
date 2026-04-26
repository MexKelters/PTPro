package com.ptpro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri:}")
    private String jwkSetUri;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @Value("${spring.security.oauth2.resourceserver.jwt.audiences}")
    private String audience;
    @Value("${client-id}")
    private String clientId;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        return http
                .httpBasic(hp -> hp.disable())
                .csrf(csrf->csrf.disable())
                .cors(cors -> {
                        }
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                                .decoder(jwtDecoder())
                        ))


                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/roles/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/roles/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/roles/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/roles/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAnyAuthority("USER", "TRAINER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAnyAuthority("USER", "TRAINER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/trainers/**").hasAnyAuthority("USER", "TRAINER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/trainers/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/trainers/**").hasAnyAuthority("TRAINER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/trainers/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/sessions/**").hasAnyAuthority("USER", "TRAINER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/sessions/**").hasAnyAuthority("TRAINER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/sessions/**").hasAnyAuthority("TRAINER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/bookings/**").hasAnyAuthority("USER", "TRAINER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/bookings/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/bookings/**").hasAnyAuthority("USER", "TRAINER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/training-schedules/user/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/training-schedules/download/**").hasAnyAuthority("USER", "TRAINER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/training-schedules/trainer/**").hasAnyAuthority("TRAINER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/training-schedules/**").hasAnyAuthority("TRAINER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/training-schedules/**").hasAnyAuthority("TRAINER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/training-schedules/**").hasAnyAuthority("TRAINER", "ADMIN")

                                .anyRequest().authenticated()
                )
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }


    public JwtDecoder jwtDecoder() {

        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder
                .withJwkSetUri(jwkSetUri != null ? jwkSetUri : issuer + "/protocol/openid-connect/certs")
                .build();
        OAuth2TokenValidator<Jwt> audienceValidator = new JwtAudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;

    }


    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new Converter<>() {
            @Override
            public Collection<GrantedAuthority> convert(Jwt source) {
                Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                for (String authority : getAuthorities(source)) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(authority));
                }
                return grantedAuthorities;
            }

            private List<String> getAuthorities(Jwt jwt) {
                Map<String, Object> resourceAcces = jwt.getClaim("resource_access");
                if (resourceAcces != null) {
                    if (resourceAcces.get(clientId) instanceof Map) {
                        Map<String, Object> client = (Map<String, Object>) resourceAcces.get(clientId);
                        if (client != null & client.containsKey("roles")) {
                            return (List<String>) client.get("roles");
                        }
                    }
                }
                return new ArrayList<>();
            }
        });
        return jwtAuthenticationConverter;
    }
}
