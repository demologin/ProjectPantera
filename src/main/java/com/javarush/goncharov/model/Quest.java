package com.javarush.goncharov.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Quest{
    Long id;
    String name;
    String authorName;
    String text;
    Long developerId;
    Long startQuestionId;
    final Collection<Question> questions = new ArrayList<>();
}
