package com.miniprojecttwo.accountservice.service;

import com.miniprojecttwo.accountservice.entity.Account;
import com.miniprojecttwo.accountservice.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Account user = userRepository.getUserByUsername(username).get();
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new MyUserDetails(user);
    }

}
