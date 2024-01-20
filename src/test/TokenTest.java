package test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import main.Token;


class TokenTest {

    @Test
    public void testTokenCreationWithTypeNull() {
        assertThrows(AssertionError.class, () -> new Token(null, "value"));
    }

    @Test
    public void testTokenCreationWithValueNull() {
        assertThrows(AssertionError.class, () -> new Token("Type", null));
    }
}