package pl.dw.organizer.services;

import pl.dw.organizer.assemblers.UserAssembler;
import pl.dw.organizer.entities.UserEntity;
import pl.dw.organizer.models.User;
import pl.dw.organizer.repositories.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Named
public class UserService {
    private final UserRepository userRepository;
    private final UserAssembler userAssembler;

    @Inject
    public UserService(UserRepository userRepository, UserAssembler userAssembler) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
    }

    public Stream<User> getAllUsers() {
        Spliterator<UserEntity> usersSpliterator = userRepository
            .findAll()
            .spliterator();

        return StreamSupport
            .stream(usersSpliterator, false)
            .map(userAssembler::mapToUser);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository
            .findById(id)
            .map(userAssembler::mapToUser);
    }

    public void deleteUser(User user) {
        userRepository
            .findById(user.getId())
            .ifPresent(userRepository::delete);
    }

    public void putUser(User user) {
        UserEntity userEntity = userAssembler.mapToUserEntity(user);
        userRepository.save(userEntity);
    }
}
