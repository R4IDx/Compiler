package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import main.Lexer;
import main.Token;
import org.junit.jupiter.api.Test;

class LexerTest {

    @Test
    void testLexNumbers() {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.lex("123.45 67.89");

        assertEquals(2, tokens.size());

        Token firstToken = tokens.get(0);
        assertEquals("NUMBER", firstToken.getType());
        assertEquals("123.45", firstToken.getValue());

        Token secondToken = tokens.get(1);
        assertEquals("NUMBER", secondToken.getType());
        assertEquals("67.89", secondToken.getValue());
    }

    @Test
    void testLexVariables() {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.lex("x y");

        assertEquals(2, tokens.size());

        Token firstToken = tokens.get(0);
        assertEquals("VARIABLE", firstToken.getType());
        assertEquals("x", firstToken.getValue());

        Token secondToken = tokens.get(1);
        assertEquals("VARIABLE", secondToken.getType());
        assertEquals("y", secondToken.getValue());
    }

    @Test
    void testLexOperatorsAndParentheses() {
        Lexer lexer = new Lexer();
        List<String> expectedValues = List.of("+", "-", "*", "/", "^", "(", ")");
        String expectedType = "SPECIAL";

        List<Token> tokens = lexer.lex("+ - * / ^ ( )");

        assertEquals(expectedValues.size(), tokens.size());

        for (int i = 0; i < expectedValues.size(); i++) {
            assertEquals(expectedType, tokens.get(i).getType());
            assertEquals(expectedValues.get(i), tokens.get(i).getValue());
        }
    }

    @Test
    void testLexInvalidCharacter() {
        Lexer lexer = new Lexer();
        assertThrows(IllegalArgumentException.class, () -> lexer.lex("2 + # 3"));
    }

    @Test
    void testLexIgnoreSpaces() {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.lex("1   +  2");

        assertEquals(3, tokens.size());

        assertEquals("NUMBER", tokens.get(0).getType());
        assertEquals("1", tokens.get(0).getValue());

        assertEquals("SPECIAL", tokens.get(1).getType());
        assertEquals("+", tokens.get(1).getValue());

        assertEquals("NUMBER", tokens.get(2).getType());
        assertEquals("2", tokens.get(2).getValue());
    }
}
