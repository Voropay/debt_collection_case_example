package com.intrum.homework.debt.rest;

import com.intrum.homework.debt.domain.user.JwtUser;
import com.intrum.homework.debt.domain.user.User;
import com.intrum.homework.debt.domain.user.UserRequest;
import com.intrum.homework.debt.domain.user.UserResponse;
import com.intrum.homework.debt.security.JwtTokenUtil;
import com.intrum.homework.debt.service.UserRegistrationException;
import com.intrum.homework.debt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class UsersRestController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("/status")
    public String status(@RequestParam(value="name", defaultValue="Riga") String name) {
        return "{\"status\": \"Ok\"}";
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody JwtAuthRequest authRequest) throws AuthenticationException {
        authenticate(authRequest.getUsername(), authRequest.getPassword());
        final String token = jwtTokenUtil.generateToken(authRequest.getUsername());
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> registerUserAccount(@RequestBody final UserRequest userResource, final HttpServletRequest request) {
        try {
            userService.registerNewUserAccount(userResource);
        } catch(UserRegistrationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("registered");
    }

    @RequestMapping("/user/account")
    public ResponseEntity<?> account(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtUser user = (JwtUser) auth.getPrincipal();
        UserResponse userRO = new UserResponse();
        userRO.setId(user.getId());
        userRO.setUsername(user.getUsername());
        userRO.setEmail(user.getEmail());
        return ResponseEntity.ok(userRO);
    }
}
