package com.unitechApi.websecurity;

import com.unitechApi.exception.ExceptionService.UserNotFound;
import com.unitechApi.user.Repository.UserRepository;
import com.unitechApi.user.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String phoneNo) throws UsernameNotFoundException {
        User user = userRepository
                .findByPhoneno(phoneNo)
                .orElseThrow(() -> new UserNotFound("User Not Found with username: " + phoneNo));
        return UserDetailsImpl.build(user);
    }

}
