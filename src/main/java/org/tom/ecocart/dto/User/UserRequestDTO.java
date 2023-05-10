package org.tom.ecocart.dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
