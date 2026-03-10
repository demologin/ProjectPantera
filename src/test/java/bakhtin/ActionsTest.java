package bakhtin;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ActionsTest {

    @Test
    void testAllActionsAreAvailable() {
        assertNotNull(Actions.ANSWER);
        assertNotNull(Actions.RESTART);
        assertNotNull(Actions.EXIT);
    }

    @Test
    void testNames() {
        assertEquals("ANSWER", Actions.ANSWER.name());
        assertEquals("RESTART", Actions.RESTART.name());
        assertEquals("EXIT", Actions.EXIT.name());
    }
}