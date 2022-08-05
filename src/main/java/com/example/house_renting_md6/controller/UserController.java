package com.example.house_renting_md6.controller;

import com.example.house_renting_md6.CustomException;
import com.example.house_renting_md6.model.*;
import com.example.house_renting_md6.model.ResponseBody;
import com.example.house_renting_md6.service.RoleService;
import com.example.house_renting_md6.service.UserService;
import com.example.house_renting_md6.service.impl.JwtService;
import com.example.house_renting_md6.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private Environment env;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> showAllUser() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<Iterable<User>> showAllUserByAdmin() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseBody> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(new ResponseBody("0001", "Invalid input parameter"), HttpStatus.OK);
        }
        try {
            return new ResponseEntity<>(new ResponseBody("0000", "Sign Up Success", userService.save(user)), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(new ResponseBody("9999", e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), currentUser.getAvatar(),userDetails.getAuthorities()));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) {
        Optional<User> userOptional = this.userService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/users/update-profile/{id}")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = this.userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(userOptional.get().getId());
        user.setUsername(userOptional.get().getUsername());
        user.setEnabled(userOptional.get().isEnabled());
        user.setRoles(userOptional.get().getRoles());
        user.setPassword(userOptional.get().getPassword());
        user.setConfirmPassword(userOptional.get().getConfirmPassword());
        userServiceImpl.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/update-password/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userOptional.get().setPassword(passwordEncoder.encode(user.getPassword()));
        userOptional.get().setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
        userServiceImpl.update(userOptional.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
//        List<String> errors = new ArrayList<String>();
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
//            errors.add(violation.getRootBeanClass().getName() + " " +
//                    violation.getPropertyPath() + ": " + violation.getMessage());
            String path = String.valueOf(violation.getPropertyPath());
            errors.put(path.replace("createUser.user.", ""), violation.getMessage());
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
