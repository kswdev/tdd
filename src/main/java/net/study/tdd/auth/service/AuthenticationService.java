package net.study.tdd.auth.service;

public interface AuthenticationService {
    boolean isValidLogin(String username, String password);
}
