package com.github.davidmoten.geo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DirectionTest {


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void opposite() {
        assertEquals(Direction.LEFT, Direction.RIGHT.opposite());
        assertEquals(Direction.RIGHT, Direction.LEFT.opposite());
        assertEquals(Direction.TOP, Direction.BOTTOM.opposite());
        assertEquals(Direction.BOTTOM, Direction.TOP.opposite());
    }
}