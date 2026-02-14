package com.javarush.goncharov.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message{
    Long id;
    String name;
    String email;
    Topic topic;
    String message;
    Boolean Completed;
}
