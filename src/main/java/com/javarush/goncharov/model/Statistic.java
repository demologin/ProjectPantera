package com.javarush.goncharov.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Statistic {
    String login;
    long win;
    long lost;
    long play;
    long total;
}
