package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.configuration.UserPrinciple;
import edu.fondue.electronicdocuments.models.User;
import edu.fondue.electronicdocuments.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = repository.findByUsername(username);
        return UserPrinciple.build(user);
    }
}
