package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.entity.Role;
import com.javarush.vasileva.entity.User;

import java.util.List;

public final class TestData {
    private TestData() {}

    public static final Long VALID_QUEST_ID = 1L;
    public static final Long NON_EXISTENT_QUEST_ID = 999L;
    public static final String NON_EXISTENT_QUEST_ID_STR = "999";
    public static final String NULL_QUEST_ID_STR = null;
    public static final String EMPTY_QUEST_ID_STR = "";
    public static final Long NON_EXISTENT_USER_ID = 999L;
    public static final String VALID_USER_LOGIN = "testUser";
    public static final String VALID_USER_EMAIL = "test@email.com";
    public static final String VALID_USER_PASSWORD = "testPassword";
    public static final String INVALID_USER_EMAIL = "invalid@email.com";
    public static final String INVALID_USER_PASSWORD = "invalidPassword";
    public static final String EMPTY_USER_ID_STR = "";
    public static final String NULL_USER_ID_STR = null;

    public static User createValidUser() {
        return User.builder()
                .id(1L)
                .login("testuser")
                .email("test@gmail.com")
                .password("password123")
                .role(Role.USER)
                .build();
    }

    public static Quest createValidQuest() {
        return Quest.builder()
                .id(VALID_QUEST_ID)
                .title("Test Quest Title")
                .description("Test Quest Description")
                .build();
    }

    public static Quest createQuestWithId(Long id) {
        return Quest.builder()
                .id(id)
                .title("Quest #" + id)
                .build();
    }

    public static List<Quest> createMultipleQuests() {
        return List.of(
                createQuestWithId(1L),
                createQuestWithId(2L),
                createQuestWithId(3L)
        );
    }
}
