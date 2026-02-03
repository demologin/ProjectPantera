package com.javarush.chebotarev.cmd;

import com.javarush.chebotarev.BaseIT;
import com.javarush.chebotarev.component.Go;
import com.javarush.chebotarev.component.ObjectRepository;
import com.javarush.chebotarev.component.Utils;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.InputStream;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MainMenuIT extends BaseIT {

    @Test
    void whenPageIsOpened_thenCommandReturnsJspPage() {
        InputStream inputStream = createInputStream();
        when(servletContext.getResourcePaths(anyString()))
                .thenReturn(Set.of(""));
        when(servletContext.getResourceAsStream(anyString()))
                .thenReturn(inputStream);
        try (MockedStatic<Utils> utils = Mockito.mockStatic(Utils.class)) {
            when(Utils.tryExtractAttribute(eq(session), anyString(), any()))
                    .thenReturn(null);

            MainMenu mainMenu = ObjectRepository.find(MainMenu.class);
            String view = mainMenu.doGet(req, servlet);

            assertEquals(Go.MAIN_MENU, view);
        }
    }
}