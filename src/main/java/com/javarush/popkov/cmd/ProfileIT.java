package com.javarush.popkov.cmd;

import com.javarush.popkov.BaseIT;
import com.javarush.popkov.config.Winter;
import com.javarush.popkov.util.Go;
import com.javarush.popkov.util.Key;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProfileIT extends BaseIT {

    private final Profile profile = Winter.find(Profile.class);

    @Test
    void whenClickEditInProfile_thenGoToEditUserPage() {
        Mockito.when(session.getAttribute(Key.USER)).thenReturn(testUser);
        String uri = profile.doPost(request);
        Assertions.assertEquals(Go.EDIT_USER + "?id=" + testUser.getId(), uri);
    }

    @Test
    void whenClickLogout_thenGoLogout() {
        Mockito.when(request.getParameter(Key.LOGOUT)).thenReturn("true");
        String uri = profile.doPost(request);
        Assertions.assertEquals(Go.LOGOUT, uri);
    }
}
