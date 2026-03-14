import com.ptpro.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@Bean
public SecurityFilterChain filterChain(HttpSecurity http, JwtService jwtService, UserService userService)throws Exception{
    http
            .httpBasic(hp->hp.disable())
            .authorizeHttpRequests(auth->auth
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/inventory").permitAll()
                    .requestMatchers("/albums/**").hasAnyRole("user")
                    .requestMatchers("/users**").hasAnyRole("admin")
                    .anyRequest().denyAll())
            .csrf(csrf->csrf.disable())
            .cors(cors->{})
            .sessionManagement(session->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
}