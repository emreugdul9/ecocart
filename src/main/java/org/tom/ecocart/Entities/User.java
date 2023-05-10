package org.tom.ecocart.Entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank(message = "First name can not be blank.")
    private String firstName;
    @NotBlank(message = "Last name can not be blank.")
    private String lastName;
    @Email
    private String email;
    @Size(min = 5, max = 20)
    private String password;

}
