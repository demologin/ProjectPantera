package com.javarush.lesson09;

import com.javarush.khmelov.config.SessionCreator;
import com.javarush.khmelov.entity.Role;
import com.javarush.khmelov.entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserRepoTest {

    private SessionCreator sessionCreator;
    private UserRepo userRepo;

    @BeforeEach
    void setUp() {
        sessionCreator = new SessionCreator();
        userRepo = new UserRepo(sessionCreator);
    }

    @AfterEach
    void tearDown() {
        sessionCreator.close();
    }

    public static Stream<Arguments> getSamplePatternForSearch() {
        //several users with different nullable fields (need skipped)
        return Stream.of(
                Arguments.of(User.builder().login("Carl").password("admin").build(), 1),
                Arguments.of(User.builder().login("Carl").password("badpass").build(), 0),
                Arguments.of(User.builder().login("Carl").build(), 1),

                Arguments.of(User.builder().login("Bob").build(), 1),
                Arguments.of(User.builder().password("123").build(), 1),
                Arguments.of(User.builder().role(Role.GUEST).build(), 1),

                Arguments.of(User.builder().login("Alisa").password("qwerty").build(), 1),
                Arguments.of(User.builder().login("Alisa").password("qwerty").role(Role.USER).build(), 1),

                Arguments.of(User.builder().build(), 3),
                Arguments.of(User.builder().id(0L).build(), 0)
        );
    }


    @Test
    @DisplayName("When find by id then get user id=1 role=ADMIN")
    void get() {
        User user = userRepo.get(1L);
        assertEquals(1L, user.getId());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    @DisplayName("When create+update+delete tempUser then no Exception")
    void createUpdateDelete() {
        User tempUser = User.builder()
                .login("login_test")
                .password("password_test")
                .role(Role.GUEST)
                .build();
        userRepo.create(tempUser);
        System.out.println("CREATE " + tempUser);

        tempUser.setPassword("password_test_update");
        userRepo.update(tempUser);
        System.out.println("UPDATE " + tempUser);

        userRepo.delete(tempUser);
        System.out.println("DELETE " + tempUser);
        assertTrue(tempUser.getId() > 0);
    }

}