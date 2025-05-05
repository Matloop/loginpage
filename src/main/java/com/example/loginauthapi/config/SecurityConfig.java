    package com.example.loginauthapi.config;

    import com.example.loginauthapi.domain.user.User;
    import com.example.loginauthapi.infra.security.CustomUserDetailsService;
    import com.example.loginauthapi.infra.security.SecurityFilter;
    import com.example.loginauthapi.infra.security.TokenService;
    import com.example.loginauthapi.repositories.UserRepository;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.oauth2.core.user.OAuth2User;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.CorsConfigurationSource;
    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

    import java.io.IOException;
    import java.util.Arrays;
    import java.util.List;

    import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        @Autowired
        private CustomUserDetailsService userDetailsService;
        @Autowired
        SecurityFilter securityFilter;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private TokenService tokenService;
        @Value("${app.oauth2.frontend-redirect-uri}") // Injeta a URL do frontend
        private String frontendRedirectUri;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
            http
                    //configuração do cors
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .headers(headers -> headers
                            // Permite que o H2 console seja exibido em um frame
                            .frameOptions(frameOptions -> frameOptions.sameOrigin())
                    )
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers(toH2Console()).permitAll()
                            //permitir endpoints do OAuth2
                            .requestMatchers("/","/webjars/**", "/error").permitAll()
                            .requestMatchers("/oauth2/**","/login/oauth2/**").permitAll()
                            .requestMatchers("/auth/login").permitAll()
                            .requestMatchers("/auth/register").permitAll()
                            .requestMatchers("/auth/admin").hasRole("ADMIN")
                            .requestMatchers("/owner/**").permitAll()
                            .requestMatchers("address/**").permitAll()
                            .anyRequest().authenticated()
                    )

                    //habilitar e configurar login OAuth2
                    .oauth2Login(oauth2 -> oauth2.successHandler(oauth2AuthenticationSuccessHandler(passwordEncoder())))
                    .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }

        // --- Bean do Handler de Sucesso OAuth2 ---
        @Bean
        public AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler(PasswordEncoder passwordEncoder) {
            return new AuthenticationSuccessHandler() {
                @Override
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                    org.springframework.security.core.Authentication authentication)
                        throws IOException, ServletException {

                    OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
                    String email = oauthUser.getAttribute("email");
                    String name = oauthUser.getAttribute("name"); // Pode ser null dependendo dos escopos/provedor
                    // String googleId = oauthUser.getName(); // ID único do Google, pode ser útil

                    System.out.println("OAuth2 Login bem-sucedido para: " + email);

                    // 1. Buscar ou Criar Usuário no seu Banco de Dados
                    //    Adapte com base na sua entidade 'User' e lógica de negócios
                    User user = userRepository.findByEmail(email)
                            .orElseGet(() -> {
                                System.out.println("Usuário não encontrado, criando novo usuário para: " + email);
                                User newUser = new User();
                                newUser.setEmail(email);
                                newUser.setName(name != null ? name : "Usuário Google"); // Defina um nome padrão se necessário
                                // Se sua entidade User exige senha não nula, gere uma segura aleatória (não será usada para login)
                                newUser.setPassword(passwordEncoder.encode(java.util.UUID.randomUUID().toString()));
                                // Você pode querer adicionar um campo 'provider' (ex: "GOOGLE", "LOCAL") na sua entidade User
                                // newUser.setProvider("GOOGLE");
                                // Defina roles padrão para novos usuários OAuth2
                                // newUser.setRoles(...);
                                return userRepository.save(newUser);
                            });

                    // 2. Gerar o SEU token JWT para este usuário
                    //    Use o mesmo serviço/método que você usa após o login com senha
                    String jwtToken = tokenService.generateToken(user); // Chame seu método de geração de token

                    // 3. Redirecionar de volta para o Frontend Angular com o token
                    String targetUrl = frontendRedirectUri + "?token=" + jwtToken;

                    // Limpa atributos de autenticação que podem ter sido salvos na sessão
                    // request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION); // Exemplo

                    System.out.println("Redirecionando para: " + targetUrl);
                    response.sendRedirect(targetUrl);
                }
            };
        }
        //configurar integração como front-end cors
        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Permite seu frontend Angular
            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")); // Métodos necessários
            configuration.setAllowedHeaders(List.of("*")); // Permite todos os cabeçalhos (incluindo Authorization para JWT)
            // configuration.setAllowCredentials(true); // Se precisar enviar/receber cookies ou usar autenticação baseada em sessão junto
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration); // Aplica a todos os paths
            return source;
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
