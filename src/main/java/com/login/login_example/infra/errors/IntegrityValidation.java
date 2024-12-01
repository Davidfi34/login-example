package com.login.login_example.infra.errors;

public class IntegrityValidation extends RuntimeException {
    public IntegrityValidation(String e) {
        super(e);
    }
}
