package com.harshit.pharmacy.security.service;

public interface PasswordService {

    String encode(String password);

    boolean matches(String rawPassword, String encodedPassword);

}
