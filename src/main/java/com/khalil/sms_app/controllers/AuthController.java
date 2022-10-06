package com.khalil.sms_app.controllers;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.khalil.sms_app.models.ERole;
import com.khalil.sms_app.models.Role;
import com.khalil.sms_app.models.User;
import com.khalil.sms_app.payload.request.SignInRequest;
import com.khalil.sms_app.payload.request.SignUpRequest;
import com.khalil.sms_app.payload.response.MessageResponse;
import com.khalil.sms_app.payload.response.UserInfoResponse;
import com.khalil.sms_app.repositories.RoleRepository;
import com.khalil.sms_app.repositories.UserRepository;
import com.khalil.sms_app.security.jwt.JwtUtils;
import com.khalil.sms_app.security.service.UserDetailsImpl;
import com.khalil.sms_app.services.UserService;

@CrossOrigin(origins = "http://10.1.11.145:4200", maxAge = 360, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public AuthController(UserService userService, RoleRepository roleRepository, UserRepository userRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        }catch (Exception e){
           return ResponseEntity.badRequest().body(new MessageResponse("Incorrect UserName or Password"));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles,
                        jwtCookie));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> userRegister(@Valid @RequestBody SignUpRequest userRequest) {

        if (userRepository.existsByUserName(userRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("UserName already exists"));
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email already exists"));
        }
        // Create new user's account
        User user = new User(userRequest.getUserName(),
                userRequest.getEmail(),
                encoder.encode(userRequest.getPassword()));

        try {
            user.setEmployee(userRequest.getEmployee());
        } catch (Exception e) {
            System.out.println("employee is null");
        }

        String strRole = userRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRole == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {

            if ("admin".equals(strRole)) {
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
            } else {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
        }
        user.setRoles(roles);
        userService.userRegister(user);

        return ResponseEntity.ok().body(new MessageResponse("User Successfully created!!"));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@RequestBody String password, @PathVariable(value = "id") Integer id) {
        User user = userRepository.findById(id).get();
        user.setPassword(encoder.encode(password));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User updated password successfully!"));

    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }


    /**
     * @param password
     * @return
     */
    @PostMapping("/password")
    public boolean checkPassword(@RequestBody String password, Principal principal){
        User user = userService.getUserByUserName(principal.getName());
        Authentication  authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), password));
        return authentication.isAuthenticated();

    }

}
