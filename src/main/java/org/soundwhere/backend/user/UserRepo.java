package org.soundwhere.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query(
        "select u from User u where " +
            "u.username=:username or " +
            "u.email=:username"
    )
    Optional<User> findInfo(String username);
    
    boolean existsByUsername(String username);
}
