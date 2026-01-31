package com.javarush.goncharov.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message{
    private Long id;
    private String name;
    private String email;
    private Topic topic;
    private String message;
    private Boolean Completed;
}
