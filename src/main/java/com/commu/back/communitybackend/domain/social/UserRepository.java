package com.commu.back.communitybackend.domain.social;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserPrincipal(String userPrincipal);
}
