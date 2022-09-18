package com.miniprojecttwo.productservice.util;

import lombok.Getter;

@Getter
public class EntityUpdateFailedException extends RuntimeException {

    private String message;

    public EntityUpdateFailedException(String message){
        this.message = message;
    }

}
