package pl.dw.organizer.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.dw.organizer.entities.UserEntity;
import pl.dw.organizer.repositories.UserRepository;

import javax.inject.Inject;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;

    @Inject
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> configurer = auth.inMemoryAuthentication();

        Spliterator<UserEntity> usersSpliterator = userRepository
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
            .antMatchers("/public").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private boolean validateUserCanLogin(UserEntity user) {
        return user.getName() != null
            && user.getUserRole() != null;
    }

    private void registerUser(UserEntity user,
                              InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> configurer) {
        String encodedPassword = passwordEncoder()
            .encode("test");

        String userName = user
            .getUserRole()
            .name();

        configurer
            .withUser(user.getName())
            .password(encodedPassword)
            .authorities(userName);
    }
}
