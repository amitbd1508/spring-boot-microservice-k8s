package com.miniprojecttwo.productservice.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miniprojecttwo.productservice.dto.Address;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Embedded;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AwesomeUserDetails implements UserDetails {

    private Long id;
    private String email;

    @JsonIgnore
    private String password;

    private List<String> roles;

    private String firstname;
    private String lastname;
    private String username;

    @Embedded
    private Address shippingAddress;

    private String preferredPaymentMethod;

    public AwesomeUserDetails() {
        this.email = "";
        this.password = "";
        this.id =-1L;
        this.roles = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
