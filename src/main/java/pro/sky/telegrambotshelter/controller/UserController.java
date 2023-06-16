package pro.sky.telegrambotshelter.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.service.UserService;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createPet(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping
    public ResponseEntity<User> updateShelter(@RequestBody User user) {
        return userService.updateUser(user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    ResponseEntity<List<User>> getAllPets(@RequestParam(required = false) String shelterChoice) {
        if (shelterChoice == null)
            return ResponseEntity.ok(userService.getAllUsers());
        return ResponseEntity.ok(userService.getAllUsersByShelterTypeChoice(shelterChoice));
    }

    @GetMapping("{id}")
    ResponseEntity<User> getPetById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShelter(@PathVariable Long id){
        userService.deleteUserById(id);
    }
}
