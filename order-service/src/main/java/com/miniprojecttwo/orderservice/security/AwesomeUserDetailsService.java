package com.miniprojecttwo.orderservice.security;

import com.miniprojecttwo.orderservice.model.dto.Account;
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

  private Account account;

  public Account getAccount() {
    return account;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Account user = getUserDetails(token, username);

    if(user!= null){
      this.account = user;
      System.out.println("user==================");
      var userDetails = new AwesomeUserDetails();
      userDetails.setEmail(user.getEmail());
      userDetails.setUsername(user.getUsername());
      userDetails.setFirstname(user.getFirstname());
      userDetails.setLastname(user.getLastname());
      userDetails.setPreferredPaymentMethod(user.getPreferredPaymentMethod());
      userDetails.setShippingAddress(user.getShippingAddress());
      return userDetails;

    }

    return null;


  }

  private Account getUserDetails(String token, String username){
    try {
      // create headers
      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "Bearer " + token);

      // create request
      HttpEntity request = new HttpEntity(headers);

      // make a request
      ResponseEntity<Account> response = new RestTemplate().exchange(accountUrl+"/accounts/"+username, HttpMethod.GET, request, Account.class);


      // get JSON response
      Account json = response.getBody();

      System.out.println("======= json "+ json.getFirstname());

      return json;

    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

}
