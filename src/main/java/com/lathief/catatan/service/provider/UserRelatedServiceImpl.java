package com.lathief.catatan.service.provider;

import com.lathief.catatan.model.entities.user.User;
import com.lathief.catatan.model.exception.NotFoundException;
import com.lathief.catatan.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;


public abstract class UserRelatedServiceImpl {
    @Autowired
    private AuthenticationProviderImpl authenticationProvider;

    @Autowired
    private UserRepository userRepository;

    protected User getUserByUsername(){
        Authentication authentication = authenticationProvider.getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findOneByUsername(username);
        if(user == null){
            throw new NotFoundException("Invalid User, User Not Found");
        }
        return user;
    }
}
