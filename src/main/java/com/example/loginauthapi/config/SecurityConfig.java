    package com.example.loginauthapi.config;

    import com.example.loginauthapi.infra.security.CustomUserDetailsService;
    import com.example.loginauthapi.infra.security.SecurityFilter;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        @Autowired
        private CustomUserDetailsService userDetailsService;
        @Autowired
        SecurityFilter securityFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .headers(headers -> headers
                            // Permite que o H2 console seja exibido em um frame
                            .frameOptions(frameOptions -> frameOptions.sameOrigin())
                    )
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers(toH2Console()).permitAll()
                            .requestMatchers("/auth/login").permitAll()
                            .requestMatchers("/auth/register").permitAll()
                            .requestMatchers("/auth/admin").hasRole("ADMIN")
                            .anyRequest().authenticated()
                    )
                    .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
            return configuration.getAuthenticationManager();
        }
    }
