package com.miniprojecttwo.orderservice.util;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    public static Integer getLoggedInUserId(){

        var obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("===============    "+ obj.toString());
        return 1;
    }
}
