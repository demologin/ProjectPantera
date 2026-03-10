package bakhtin;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ActionTest {

    @Test
    void testAllActionsAreAvailable() {
        assertNotNull(Action.ANSWER);
        assertNotNull(Action.RESTART);
        assertNotNull(Action.EXIT);
    }

    @Test
    void testNames() {
        assertEquals("ANSWER", Action.ANSWER.name());
        assertEquals("RESTART", Action.RESTART.name());
        assertEquals("EXIT", Action.EXIT.name());
    }
}