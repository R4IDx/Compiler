package test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import main.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    public void testParseValidExpression() {
        List<Token> tokens = Arrays.asList(
                new Token("NUMBER", "5"),
                new Token("SPECIAL", "+"),
                new Token("NUMBER", "3")
        );

        assertDoesNotThrow(() -> {
            Parser parser = new Parser();
            ASTNode testresult = parser.parse(tokens);
        });

    }
    @Test
    public void testParseInvalidExpression() {
        List<Token> tokens = Arrays.asList(
                new Token("NUMBER", "5"),
                new Token("SPECIAL", "+"),
                new Token("SPECIAL", "*")
        );

        assertThrows(IllegalArgumentException.class, () -> {
            Parser parser = new Parser();
            parser.parse(tokens);
        });
    }

    @Test
    public void testParseExpressionWithMismatchedParentheses() {
        List<Token> tokens = Arrays.asList(
                new Token("SPECIAL", "("),
                new Token("NUMBER", "5"),
                new Token("SPECIAL", "+"),
                new Token("NUMBER", "3"),
                new Token("SPECIAL", ")"),
                new Token("SPECIAL", ")")
        );

        assertThrows(IllegalArgumentException.class, () -> {
            Parser parser = new Parser();
            parser.parse(tokens);
        });
    }
}
