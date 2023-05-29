package org.storageart.storageart.exceptions;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(String str) {
        super(str);
    }

}
