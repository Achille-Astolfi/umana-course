package model.view;

import javax.validation.constraints.NotBlank;

import model.domain.Authentication;
import model.domain.Authorization;

public class TokenRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public TokenResponse createResponse(Authentication authentication) {
        var authorization = Authorization.ofUsername(username);
        var tokenResponse = new TokenResponse();
        tokenResponse.setAuthentication(authentication);
        tokenResponse.setAuthorization(authorization);
        return tokenResponse;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
