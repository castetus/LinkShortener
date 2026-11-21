package org.example.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShortCodeGeneratorTest {

    @Test
    void shouldGenerateCodeWithSixCharacters() {
        ShortCodeGenerator generator = new ShortCodeGenerator();

        String code = generator.generate();

        assertEquals(6, code.length());
    }

    @Test
    void shouldGenerateCodeWithAllowedCharacters() {
        ShortCodeGenerator generator = new ShortCodeGenerator();

        String code = generator.generate();

        assertTrue(code.matches("[a-zA-Z0-9]{6}"));
    }
}