package org.example.contracttestingdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
