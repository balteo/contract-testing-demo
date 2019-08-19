package org.example.contract_demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class User {

    @Id
    private Integer id;
    @Size(min = 2)
    private String firstName;
    @Size(min = 2)
    private String lastName;
    @Email
    private String email;
}
