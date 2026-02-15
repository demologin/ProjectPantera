package com.javarush.goncharov.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Statistic {
    String login;
    long win;
    long lost;
    long play;
    long total;
}
