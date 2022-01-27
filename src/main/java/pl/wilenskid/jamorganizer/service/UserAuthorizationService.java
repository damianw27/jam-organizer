package pl.wilenskid.jamorganizer.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.wilenskid.jamorganizer.repository.ApplicationUserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

@Named
@Transactional
public class UserAuthorizationService implements UserDetailsService {
    private final ApplicationUserRepository applicationUserRepository;

    @Inject
    public UserAuthorizationService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserRepository
            .getByName(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
