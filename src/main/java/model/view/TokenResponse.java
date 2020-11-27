package model.view;

import model.domain.Authentication;
import model.domain.Authorization;

public class TokenResponse {
    private Authorization authorization;
    private Authentication authentication;

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
