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
    public void encodeBase32WithPositiveNumAndBiggerLength() throws Exception {
        String encode = Base32.encodeBase32(9223372036854775807L, 14);
        assertEquals("07zzzzzzzzzzzz", encode);
    }

    @Test
    public void encodeBase32WithPositiveNumAndSmallerLength() throws Exception {
        String encode = Base32.encodeBase32(9223372036854775807L, 13);
        assertEquals("7zzzzzzzzzzzz", encode);
    }

    @Test
    public void encodeBase32WithPositiveNumAndNegativeLength() throws Exception {
        String encode = Base32.encodeBase32(9223372036854775807L, -2);
        assertEquals("7zzzzzzzzzzzz", encode);
    }

    @Test
    public void encodeBase32WithNegativeNumAndBiggerLength() throws Exception {
        String encode = Base32.encodeBase32(-9223372036854775808L, 15);
        assertEquals("-008000000000000", encode);
    }

    @Test
    public void encodeBase32WithNegativeNumAndSmallerLength() throws Exception {
        String encode = Base32.encodeBase32(-9223372036854775808L, 9);
        assertEquals("-8000000000000", encode);
    }

    @Test
    public void encodeBase32WithNegativeNumAndNegativeLength() throws Exception {
        String encode = Base32.encodeBase32(-9223372036854775808L, -2);
        assertEquals("-8000000000000", encode);
    }

    @Test
    public void encodeBase32WithOnlyPositiveLong() throws Exception {
        String encode = Base32.encodeBase32(9223372036854775807L);
        assertEquals("7zzzzzzzzzzzz", encode);
    }

    @Test
    public void encodeBase32WithOnlyNegativeLong() throws Exception {
        String encode = Base32.encodeBase32(-9223372036854775808L);
        assertEquals("-8000000000000", encode);
    }

    @Test
    public void encodeBase32WithNegativeLongGraph() throws Exception {
        String encode = Base32.encodeBase32(-1,5);
        assertEquals("-00001", encode);
    }

    @Test
    public void encodeBase32WithBiggerNegativeLongGraph() throws Exception {
        String encode = Base32.encodeBase32(-64,5);
        assertEquals("-00020", encode);
    }

    @Test
    public void encodeBase32WithPositiveLongGraph() throws Exception {
        String encode = Base32.encodeBase32(32,5);
        assertEquals("00010", encode);
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

    @Test
    public void decodeBase32WithEmptyHash() throws Exception {
        long decode = Base32.decodeBase32("");
        assertEquals(0, decode);
    }

    @Test
    public void decodeBase32WithNegativeEmptyHash() throws Exception {
        long decode = Base32.decodeBase32("-");
        assertEquals(0, decode);
    }

    @Test
    public void decodeBase32WithPositiveHashGraph() throws Exception {
        long decode = Base32.decodeBase32("1");
        assertEquals(1, decode);
    }

    @Test
    public void decodeBase32WithNegativeHashGraph() throws Exception {
        long decode = Base32.decodeBase32("-1");
        assertEquals(-1, decode);
    }
}