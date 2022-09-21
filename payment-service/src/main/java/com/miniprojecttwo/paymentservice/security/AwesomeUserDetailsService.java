package com.miniprojecttwo.paymentservice.security;

import com.miniprojecttwo.paymentservice.dto.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service("userDetailsService")
@Transactional
public class AwesomeUserDetailsService implements UserDetailsService {

  private String token = "";

  private Account account;

  public Account getAccount() {
    return account;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


      var userDetails = new AwesomeUserDetails();
      return userDetails;
  }

}
