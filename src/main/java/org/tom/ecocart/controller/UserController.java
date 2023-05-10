package org.tom.ecocart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tom.ecocart.dto.User.UserDTO;
import org.tom.ecocart.dto.User.UserRequestDTO;
import org.tom.ecocart.dto.User.UserResponseDTO;
import org.tom.ecocart.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        try {
            return new ResponseEntity<>(userService.createUser(userRequestDTO), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(UserResponseDTO.buildForError(e.getMessage(),false),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable String id) throws Exception {
        UserDTO userDTO = userService.getUserById(id);
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }


}
