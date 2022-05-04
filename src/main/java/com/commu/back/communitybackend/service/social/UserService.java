package com.commu.back.communitybackend.service.social;

import com.commu.back.communitybackend.domain.social.User;
import com.commu.back.communitybackend.domain.social.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean isNotExistUser(String userPrincipal){
        return userRepository.findByUserPrincipal(userPrincipal) == null ? true : false;
    }


}
