package com.javarush.popkov.cmd;

import com.javarush.popkov.BaseIT;
import com.javarush.popkov.config.Winter;
import com.javarush.popkov.entity.Game;
import com.javarush.popkov.entity.Question;
import com.javarush.popkov.util.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlayGameIT extends BaseIT {

    private final PlayGame playGame = Winter.find(PlayGame.class);

    @Test
    void whenStartGame_thenSetGameAndQuestionInRequest() {
        when(session.getAttribute(Key.USER)).thenReturn(testUser);
        when(request.getParameter(Key.QUEST_ID)).thenReturn("1");
        String jspPage = playGame.doGet(request);

        assertEquals("play-game", jspPage);
        verify(request).setAttribute(eq(Key.GAME), any(Game.class));
        verify(request).setAttribute(eq(Key.QUESTION), any(Question.class));
    }

}
