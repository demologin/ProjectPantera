package com.javarush.popkov.cmd;

import com.javarush.popkov.BaseIT;
import com.javarush.popkov.config.Winter;
import com.javarush.popkov.util.Key;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ListUserIT extends BaseIT {

    ListUser listUser = Winter.find(ListUser.class);

    @Test
    void whenGetListUsers_thenReturnJspPage() {
        String jspPage = listUser.doGet(request);

        assertEquals("list-user", jspPage);
        verify(request).setAttribute(eq(Key.USERS), any(Collection.class));
    }
}
