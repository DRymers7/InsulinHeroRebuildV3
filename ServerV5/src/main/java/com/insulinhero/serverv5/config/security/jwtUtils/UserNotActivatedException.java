package com.insulinhero.serverv5.config.security.jwtUtils;

import javax.naming.AuthenticationException;

public class UserNotActivatedException extends AuthenticationException {

    private static final long serialVersionUID = -1126699074574529145L;

    public UserNotActivatedException(String message) {
        super(message);
    }
}
