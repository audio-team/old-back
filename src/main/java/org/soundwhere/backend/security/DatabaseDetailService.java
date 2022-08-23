package org.soundwhere.backend.security;

import org.soundwhere.backend.user.UserRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseDetailService implements UserDetailsService {
    private final UserRepo repo;

    public DatabaseDetailService(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var info = repo.findInfo(username);
        if (info.isEmpty()) throw new UsernameNotFoundException("Username " + username + " does not exist");
        else {
            var user = info.get();
            return new User(user.getUsername(), user.getPassword(), List.of());
        }
    }
}
