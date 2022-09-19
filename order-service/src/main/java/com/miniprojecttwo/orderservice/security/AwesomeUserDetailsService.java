package com.miniprojecttwo.orderservice.security;

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

  @Value("${account.url}")
  private String accountUrl;
  private String token = "";

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    System.out.println("======token "+ token);
    String user = getUserDetails(token);
    System.out.println("======user "+ user);

    var userDetails = new AwesomeUserDetails();
    return userDetails;
  }

  private String getUserDetails(String token){
    try {
      // create headers
      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "Bearer " + token);

      // create request
      HttpEntity request = new HttpEntity(headers);

      // make a request
      ResponseEntity<String> response = new RestTemplate().exchange(accountUrl, HttpMethod.GET, request, String.class);

      // get JSON response
      String json = response.getBody();
      return json;

    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

}
