package model.domain;

import java.util.Arrays;
import java.util.List;

public class Authorization {
    private String username;
    private List<String> roles;

    public static Authorization ofUsername(String username) {
        List<String> roles;
        switch (username) {
            case "user":
                roles = Arrays.asList("USER");
                break;
            case "admin":
                roles = Arrays.asList("USER", "ADMIN");
                break;
            default:
                roles = Arrays.asList();
                break;
        }
        var authorization = new Authorization();
        authorization.setUsername(username);
        authorization.setRoles(roles);
        return authorization;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
