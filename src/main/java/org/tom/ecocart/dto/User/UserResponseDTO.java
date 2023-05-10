package org.tom.ecocart.dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private boolean isSucceed;
    private String errorMessage;
    public UserResponseDTO(){

    }

    public UserResponseDTO(boolean isSucceed){
        this.isSucceed = isSucceed;
    }
    private UserResponseDTO(String errorMessage,boolean isSucceed){
        this.errorMessage = errorMessage;
        this.isSucceed = isSucceed;
    }

    public static UserResponseDTO buildForError(String errorMessage,boolean isSucceed){
        return new UserResponseDTO(errorMessage,isSucceed);
    }
}
