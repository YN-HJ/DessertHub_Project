package com.desserthub.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // user_idでUserを検索するメソッド
    Optional<User> findByUserId(String userId);
    
    boolean existsByUserId(String userId);

    User findByUserNnAndUserEm(String userNn, String userEm);
    User findByUserIdAndUserEm(String userId, String userEm);
}