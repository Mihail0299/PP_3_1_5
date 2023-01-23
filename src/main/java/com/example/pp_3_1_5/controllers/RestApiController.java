package com.example.pp_3_1_5.controllers;

import com.example.pp_3_1_5.Exception.ExceptionMessage;
import com.example.pp_3_1_5.Exception.UserUsernameExistException;
import com.example.pp_3_1_5.model.User;
import com.example.pp_3_1_5.service.RoleService;
import com.example.pp_3_1_5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RestApiController {

    private final UserService userService;

    @Autowired
    public RestApiController(RoleService roleService, UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<ExceptionMessage> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new ExceptionMessage(error), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserUsernameExistException u) {
            throw new UserUsernameExistException("User with username exist");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ExceptionMessage> pageDelete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(new ExceptionMessage("User deleted"), HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUser (@PathVariable("id") long id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserByUsername (Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ExceptionMessage> pageEdit(@PathVariable("id") long id,
                                                     @Valid @RequestBody User user,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new ExceptionMessage(error), HttpStatus.BAD_REQUEST);
        }
        try {

            userService.update(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (UserUsernameExistException u) {
            throw new UserUsernameExistException("User with username exist");
        }
    }

    private String getErrorsFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
    }
}
