package pl.dw.organizer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.dw.organizer.entities.UserEntity;
import pl.dw.organizer.repositories.UserRepository;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
	    SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner testUser(UserRepository userRepository) {
        return (args) -> {
            UserEntity user = new UserEntity();
            user.setName("test");

            userRepository.save(user);

            System.out.println(userRepository.findAll());
        };
    }
}