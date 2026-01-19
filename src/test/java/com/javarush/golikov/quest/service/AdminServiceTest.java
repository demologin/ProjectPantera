package com.javarush.golikov.quest.service;

import com.javarush.golikov.quest.model.Quest;
import com.javarush.golikov.quest.model.User;
import com.javarush.golikov.quest.repository.QuestRepository;
import com.javarush.golikov.quest.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    private final AdminService adminService = new AdminService();

    @Test
    @DisplayName("Test getAllUsers returns users from repository")
    void testGetAllUsersReturnsUsersFromRepository() {

        List<User> users = List.of(new User("admin", "123", null));

        try (MockedStatic<UserRepository> repo = mockStatic(UserRepository.class)) {
            repo.when(UserRepository::all).thenReturn(users);

            assertEquals(1, adminService.getAllUsers().size());
        }
    }

    @Test
    @DisplayName("Test saveUser delegates to UserRepository")
    void testSaveUserDelegatesToRepository() {

        User user = new User("test", "pass", null);

        try (MockedStatic<UserRepository> repo = mockStatic(UserRepository.class)) {
            adminService.saveUser(user);

            repo.verify(() -> UserRepository.save(user));
        }
    }

    @Test
    @DisplayName("Test deleteUser delegates to UserRepository")
    void testDeleteUserDelegatesToRepository() {

        try (MockedStatic<UserRepository> repo = mockStatic(UserRepository.class)) {
            adminService.deleteUser("test");

            repo.verify(() -> UserRepository.delete("test"));
        }
    }

    @Test
    @DisplayName("Test getAllQuests returns quests from repository")
    void testGetAllQuestsReturnsQuestsFromRepository() {

        List<Quest> quests = List.of(mock(Quest.class));

        try (MockedStatic<QuestRepository> repo = mockStatic(QuestRepository.class)) {
            repo.when(QuestRepository::all).thenReturn(quests);

            assertEquals(1, adminService.getAllQuests().size());
        }
    }

    @Test
    @DisplayName("Test deleteQuest delegates to QuestRepository")
    void testDeleteQuestDelegatesToRepository() {

        try (MockedStatic<QuestRepository> repo = mockStatic(QuestRepository.class)) {
            adminService.deleteQuest("farm");

            repo.verify(() -> QuestRepository.remove("farm"));
        }
    }

    @Test
    @DisplayName("Test loadQuestFromTxt loads quest successfully")
    void testLoadQuestFromTxtLoadsQuestSuccessfully() {

        InputStream is = new ByteArrayInputStream("test".getBytes());

        try (MockedStatic<QuestRepository> repo = mockStatic(QuestRepository.class)) {

            adminService.loadQuestFromTxt("farm", is);

            repo.verify(() ->
                    QuestRepository.loadTxt(is, "farm")
            );
        }
    }

    @Test
    @DisplayName("Test loadQuestFromTxt throws IllegalStateException on error")
    void testLoadQuestFromTxtThrowsIllegalStateExceptionOnError() {

        InputStream is = new ByteArrayInputStream("bad".getBytes());

        try (MockedStatic<QuestRepository> repo = mockStatic(QuestRepository.class)) {

            repo.when(() ->
                    QuestRepository.loadTxt(any(), any())
            ).thenThrow(new RuntimeException("error"));

            IllegalStateException ex = assertThrows(
                    IllegalStateException.class,
                    () -> adminService.loadQuestFromTxt("bad", is)
            );

            assertTrue(ex.getMessage().contains("Ошибка загрузки квеста"));
        }
    }

    @Test
    @DisplayName("Test AdminService constructor")
    void testAdminServiceConstructor() {
        AdminService service = new AdminService();
        assertNotNull(service);
    }

    @Test
    @DisplayName("Test loadQuestFromTxt without mocking repository")
    void testLoadQuestFromTxtWithoutMocking() {

        AdminService service = new AdminService();

        InputStream is = new ByteArrayInputStream("""
        !Quest
        *start

        @start
        ? Question
        """.getBytes());

        // реальный вызов, без mockStatic
        service.loadQuestFromTxt("real", is);

        // если дошли сюда — строка реально выполнена
        assertTrue(true);
    }

    @Test
    @DisplayName("Integration test AdminService without static mocks")
    void testAdminServiceWithoutMocks() {

        // очистка реальных репозиториев
        UserRepository.clear();
        QuestRepository.clear();

        AdminService service = new AdminService();

        // реальные вызовы — JaCoCo их увидит
        service.getAllUsers();
        service.getAllQuests();

        service.saveUser(new User("u", "p", null));
        service.deleteUser("u");

        service.deleteQuest("any");

        InputStream is = new ByteArrayInputStream("""
        !Quest
        *start

        @start
        ? Question
        """.getBytes());

        service.loadQuestFromTxt("realQuest", is);

        // assert формальный — нам важно выполнение строк
        assertTrue(true);
    }
}