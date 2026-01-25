package com.javarush.goncharov.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User  implements AbstractModel{
    private Long id;
    private String login;
    private String password;
}
