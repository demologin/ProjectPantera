package com.javarush.popkov.cmd;

import com.javarush.popkov.BaseIT;
import com.javarush.popkov.config.Winter;
import com.javarush.popkov.util.Key;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.mockito.Mockito.*;

class HomeIT extends BaseIT {


    @Test
    void whenOpenPage_thenCommandReturnJspPage() {
        Home home = Winter.find(Home.class);
        String view = home.doGet(request);
        Assertions.assertEquals("home", view);
        verify(request).setAttribute(eq(Key.QUESTS), any(Collection.class));
    }
}
