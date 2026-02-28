package Expense.tracker.backend.Config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//  jab springsecurity mera run krega login ka logic it will find passwordencoder and it will run that passwordencoder make sure the login and registeration use the same type of password encoder

//    securityfilterchain this is a bean that returns all the rules that need to be applied when anyrequest is called this is like a chain! it is like the working koi bhi request phele securityfilterchain pe jaati hai
//    SecurityFilter is like a book And thats empty and you are filling it with a pen like HTTPSecurity
//    Cross-Site Request Forgery it means when one site is trying to hit api of another site using it credentials we are using it for development WE WILL KEEP uska check disable
// CORS IS FOR KISKO KYA ACCESS

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/register", "/login").permitAll().anyRequest().authenticated()
//                        this is ... array
//                        .requestMatchers("/patterns","/login")).permitAll().anyRequest().authenticted())
                )
                .formLogin(form -> form
                                .loginProcessingUrl("/login")
                                .usernameParameter("email")
//                        jisse login karna hai woh parameter pass karna hai
                                .successHandler((request, response, authentication) -> {
                                    response.setStatus(HttpServletResponse.SC_OK);
                                    response.setContentType("application/json");
                                    response.getWriter().write("{\"message\":\"Login Successful\",\"user\":\"" + authentication.getName() + "\"}");
                                })
                                .failureHandler((request, response, exception) -> {
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                    response.setContentType("appliction/json");
                                    response.getWriter().write("{\"error\":\"Invalid Credentils\"}");
//                            \ is for escaping double qoutes . is for chaining
                                })
//                        this is used when login karte time koi failure aya woh hai!

                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\":\"Please log in\"}");
                        })

                );
        return http.build();

    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:4200","http://localhost:3000"));

        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));

        configuration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type","Accept"));

        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);

        return source;
    }

}

