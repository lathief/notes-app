package com.lathief.catatan.service;

import com.lathief.catatan.model.entities.user.User;
import com.lathief.catatan.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String str) throws UsernameNotFoundException {
        User user;
        if (validate(str)) {
            user = userRepository.findOneByEmail(str);
            if(user==null){
                throw new UsernameNotFoundException(String.format("Email %s is not found", str));
            }
        } else {
            user = userRepository.findOneByUsername(str);
            if (null == user) {
                throw new UsernameNotFoundException(String.format("Username %s is not found", str));
            }
        }

        return user;
    }
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate (String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
