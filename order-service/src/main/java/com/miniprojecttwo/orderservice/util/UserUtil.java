package com.miniprojecttwo.orderservice.util;

import com.miniprojecttwo.orderservice.model.dto.Account;
import com.miniprojecttwo.orderservice.security.AwesomeUserDetailsService;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    @Autowired
    AwesomeUserDetailsService awesomeUserDetailsService;
    public static Integer getLoggedInUserId(){

        var obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("===============    "+ obj.toString());
        return 1;
    }

    public Account getLoggedInUser(){
        return awesomeUserDetailsService.getAccount();
    }
}
