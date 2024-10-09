package OutSourcing.ENGO.global.config;


import OutSourcing.ENGO.global.jwt.filter.JwtAuthFilter;
import OutSourcing.ENGO.global.jwt.service.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;


    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/auth/user/details").hasAnyAuthority(ADMIN);


                    auth.requestMatchers(HttpMethod.GET,
                            "/first-board","first-board/*","first-board/*/first-board-comments",
                            "/second-board","second-board/*","second-board/*/second-board-comments",
                            "/third-board","third-board/*","third-board/*/third-board-comments",
                            "/gallery-board","gallery-board/*","gallery-board/*/gallery-board-comments").permitAll();

                    auth.requestMatchers(HttpMethod.GET,  "/first-board-like/*/likes-count",
                            "/second-board-like/*/likes-count",
                            "/third-board-like/*/likes-count",
                            "/gallery-board-like/*/likes-count").hasAnyAuthority(USER, ADMIN);
                    auth.requestMatchers(HttpMethod.POST,
                            "/first-board-like/*/unlike", "/first-board-like/*/like", "/first-board/create",
                            "/first-board/*/first-board-comments/create",
                            "/second-board-like/*/unlike", "/second-board-like/*/like", "/second-board/create",
                            "/second-board/*/second-board-comments/create",
                            "/third-board-like/*/unlike", "/third-board-like/*/like", "/third-board/create",
                            "/third-board/*/first-board-comments/create",
                            "/gallery-board-like/*/unlike", "/gallery-board-like/*/like", "/gallery-board/create",
                            "/gallery-board/*/gallery-board-comments/create").hasAnyAuthority(USER, ADMIN);

                    auth.requestMatchers(HttpMethod.PATCH,
                            "/first-board/*", "/first-board/*/first-board-comments/update/*",
                            "/second-board/*", "/second-board/*/second-board-comments/update/*",
                            "/third-board/*", "/third-board/*/third-board-comments/update/*",
                            "/gallery-board/*", "/gallery-board/*/gallery-board-comments/update/*").hasAnyAuthority(USER, ADMIN);

                    auth.requestMatchers(HttpMethod.DELETE,
                            "/first-board/*", "/first-board/*/first-board-comments/delete/*",
                            "/second-board/*", "/second-board/*/second-board-comments/delete/*",
                            "/third-board/*", "/third-board/*/first-board-comments/delete/*",
                            "/gallery-board/*", "/gallery-board/*/first-board-comments/delete/*").hasAnyAuthority(USER, ADMIN);

                    auth.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    auth.requestMatchers(HttpMethod.PATCH, "/auth/logout").permitAll();
                    auth.requestMatchers(HttpMethod.DELETE, "/auth/delete/*").permitAll();

                    auth.anyRequest().authenticated();

                })
                .addFilterBefore(new JwtAuthFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .logout(Customizer.withDefaults());

        return httpSecurity.build();

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}