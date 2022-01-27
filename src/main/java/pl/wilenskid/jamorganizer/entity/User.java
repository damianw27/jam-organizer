package pl.wilenskid.jamorganizer.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.wilenskid.jamorganizer.enums.UserRole;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity(name = "USER_DATA")
public class User extends AbstractPersistable<Long> implements UserDetails {
    private String name;
    private String displayName;
    private String email;
    private String phoneNumber;
    private String password;
    private UserRole userRole;
    private Calendar jobStart;
    private Calendar created;
    private Calendar suspendExpiration;
    private String avatarUrl;

    @OneToMany(mappedBy = "event")
    private Set<Member> membership;

    @ManyToMany(mappedBy = "members")
    private Set<Project> projects;

    @OneToMany
    private Set<Comment> comments;

    @OneToMany
    private Set<File> files;

    @OneToMany
    private Set<Endorsement> endorsements;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + getUserRole().name()));
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return suspendExpiration == null || suspendExpiration.after(Calendar.getInstance());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
