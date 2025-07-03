package com.knowy.server.application.service.usecase.login;

import com.knowy.server.application.domain.PrivateUser;

import java.util.Objects;

public final class LoginResult {
    private final PrivateUser privateUser;
    private final String generatedToken;

    public LoginResult(PrivateUser privateUser, String generatedToken) {
        this.privateUser = privateUser;
        this.generatedToken = generatedToken;
    }

    public PrivateUser privateUser() {
        return privateUser;
    }

    public String generatedToken() {
        return generatedToken;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (LoginResult) obj;
        return Objects.equals(this.privateUser, that.privateUser) &&
                Objects.equals(this.generatedToken, that.generatedToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(privateUser, generatedToken);
    }

    @Override
    public String toString() {
        return "LoginResult[" +
                "privateUser=" + privateUser + ", " +
                "generatedToken=" + generatedToken + ']';
    }

}
