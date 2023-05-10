package org.tom.ecocart.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.tom.ecocart.Entities.User;
import org.tom.ecocart.dto.User.UserDTO;
import org.tom.ecocart.dto.User.UserRequestDTO;
import org.tom.ecocart.dto.User.UserResponseDTO;
import org.tom.ecocart.repository.IUserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(userRequestDTO.getEmail());
        if(optionalUser.isPresent()){
            return UserResponseDTO.buildForError("This email is using",false);
        }
        User user = new ModelMapper().map(userRequestDTO,User.class);
        userRepository.save(user);
        return new UserResponseDTO(true);
    }


    public UserDTO getUserById(String id) throws Exception {
        User optionalUser = userRepository.findById(id).orElseThrow(() ->  new Exception("Not Found"));
        return new ModelMapper().map(optionalUser,UserDTO.class);
    }
}
