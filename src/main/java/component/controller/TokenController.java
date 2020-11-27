package component.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import component.configuration.UnauthorizedException;
import iface.service.MvcUsersService;
import model.view.TokenRequest;
import model.view.TokenResponse;

@RestController
@RequestMapping("/token")
public class TokenController {
    @Autowired
    private MvcUsersService mvcUserService;

    @PostMapping
    public ResponseEntity<TokenResponse> postLogin(@Valid @RequestBody TokenRequest request) {
        return this.mvcUserService.readMvcUserByUsernameAndPassword(request.getUsername(), request.getPassword())
                .map(request::createResponse)//
                .map(ResponseEntity::ok)//
                .orElseThrow(UnauthorizedException::new);
    }
}
