package com.javarush.popkov.cmd;

import com.javarush.popkov.BaseIT;
import com.javarush.popkov.config.Winter;
import com.javarush.popkov.entity.Role;
import com.javarush.popkov.entity.User;
import com.javarush.popkov.service.QuestService;
import com.javarush.popkov.util.Go;
import com.javarush.popkov.util.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CreateQuestIT extends BaseIT {

    private final CreateQuest createQuest = Winter.find(CreateQuest.class);
    private final QuestService questService = Winter.find(QuestService.class);


    @Test
    void whenCreateQuest_thenQuestsCountIncreaseByOne() {
        User admin = User.builder().id(1L).role(Role.ADMIN).build();
        when(session.getAttribute(Key.USER)).thenReturn(admin);
        when(request.getParameter(Key.NAME)).thenReturn("TestQuest");
        when(request.getParameter(Key.TEXT)).thenReturn("1: Test OK?\n2< Да\n3< Нет\n2+ win\n3- lost\n");

        int count = questService.getAll().size();
        assertEquals(Go.HOME, createQuest.doPost(request));
        assertEquals(count + 1, questService.getAll().size());
    }

}
