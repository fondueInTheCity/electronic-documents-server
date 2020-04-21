package edu.fondue.electronicdocuments.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.fondue.electronicdocuments.enums.RoleName;
import edu.fondue.electronicdocuments.models.Role;
import edu.fondue.electronicdocuments.models.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
@Builder
public class UserPrinciple implements UserDetails {

    private static final long serialVersionUid = 1L;

    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrinciple build(final User user) {
        final List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(Role::getRoleName)
                .map(RoleName::name)
                .map(SimpleGrantedAuthority::new)
                .collect(toList());

        return UserPrinciple.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities).build();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
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
