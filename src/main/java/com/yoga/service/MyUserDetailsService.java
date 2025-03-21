package com.yoga.service;

import com.yoga.component.MyUserDetails;
import com.yoga.datamodel.User;
import com.yoga.exception.UserNotFoundException;
import com.yoga.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

       private UserRepository userRepository;

       @Autowired
       public MyUserDetailsService(UserRepository userRepository){
           this.userRepository = userRepository;
       }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User is not found"));

        return new MyUserDetails(existingUser);
    }
}
