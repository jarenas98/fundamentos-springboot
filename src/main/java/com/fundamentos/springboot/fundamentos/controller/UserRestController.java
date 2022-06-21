package com.fundamentos.springboot.fundamentos.controller;

import com.fundamentos.springboot.fundamentos.caseuse.CreateUser;
import com.fundamentos.springboot.fundamentos.caseuse.DeleteUser;
import com.fundamentos.springboot.fundamentos.caseuse.GetUser;
import com.fundamentos.springboot.fundamentos.caseuse.UpdateUser;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    //Create
    //Obtain
    //Delete
    //update

    private GetUser getUser;
    private CreateUser createUser;
    private DeleteUser deleteUser;
    private UpdateUser updateUser;
    private UserRepository userRepository;

    public UserRestController(GetUser getUser,
                              CreateUser createUser,
                              DeleteUser deleteUser,
                              UpdateUser updateUser,
                              UserRepository userRepository) {
        this.getUser = getUser;
        this.createUser = createUser;
        this.deleteUser = deleteUser;
        this.updateUser = updateUser;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    private List<User> get() {
        return this.getUser.getAll();
    }

    @PostMapping("/")
    private ResponseEntity<User> newUser(@RequestBody User user) {
        return new ResponseEntity<User>(this.createUser.save(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deleteUser(@PathVariable Long id) {
        this.deleteUser.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    ResponseEntity<User> replaceUser(@RequestBody User user, @PathVariable Long id) {
        return new ResponseEntity<User>(this.updateUser.update(user, id), HttpStatus.OK);
    }

    @GetMapping("/pageable")
    List<User> getUserPageable(@RequestParam int page, @RequestParam int size) {
        return this.userRepository.findAll(PageRequest.of(page, size)).getContent();
    }
}
