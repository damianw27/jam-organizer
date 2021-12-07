package pl.wilenskid.jamorganizer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.wilenskid.jamorganizer.entity.ApplicationUser;
import pl.wilenskid.jamorganizer.repository.ApplicationUserRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final ApplicationUserRepository applicationUserRepository;

    @Inject
    public SecurityConfig(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> configurer = auth.inMemoryAuthentication();

        Spliterator<ApplicationUser> usersSpliterator = applicationUserRepository
            .findAll()
            .spliterator();

        StreamSupport
            .stream(usersSpliterator, false)
            .filter(this::validateUserCanLogin)
            .forEach(userEntity -> registerUser(userEntity, configurer));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/").permitAll();

        http.cors().and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private boolean validateUserCanLogin(ApplicationUser applicationUser) {
        return applicationUser.getName() != null
            && applicationUser.getApplicationUserRole() != null;
    }

    private void registerUser(ApplicationUser applicationUser, InMemoryUserDetailsManagerConfigurer<?> configurer) {
        String encodedPassword = passwordEncoder()
            .encode("test");

        String userName = applicationUser
            .getApplicationUserRole()
            .name();

        configurer
            .withUser(applicationUser.getName())
            .password(encodedPassword)
            .authorities(userName);
    }
}
