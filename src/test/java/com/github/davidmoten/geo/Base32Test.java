package com.github.davidmoten.geo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Base32Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void encodeBase32WithOnlyNum() throws Exception {
        String encode = Base32.encodeBase32(9223372036854775807L);
        assertEquals("7zzzzzzzzzzzz", encode);
    }

    @Test
    public void encodeBase32WithPositiveNumAndBiggerLength() throws Exception {
        String encode = Base32.encodeBase32(9223372036854775807L, 14);
        assertEquals("07zzzzzzzzzzzz", encode);
    }

    @Test
    public void encodeBase32WithNegativeNumAndNormalLength() throws Exception {
        String encode = Base32.encodeBase32(-9223372036854775808L, 9);
        assertEquals("-8000000000000", encode);
    }

    @Test
    public void decodeBase32WithPositiveHash() throws Exception {
        long decode = Base32.decodeBase32("7zzzzzzzzzzzz");
        assertEquals(9223372036854775807L, decode);
    }

    @Test
    public void decodeBase32WithNegativeHash() throws Exception {
        long decode = Base32.decodeBase32("-8000000000000");
        assertEquals(-9223372036854775808L, decode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void decodeBase32WithInvalidCharacter() throws Exception {
            long decode = Base32.decodeBase32("-037a4ry");
    }
}