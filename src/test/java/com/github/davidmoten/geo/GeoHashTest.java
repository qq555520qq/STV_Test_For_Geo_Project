package com.github.davidmoten.geo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.github.davidmoten.geo.GeoHash.*;

import static org.junit.Assert.*;

public class GeoHashTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromLongToStringWithInvalidLong() {
        GeoHash.fromLongToString(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void adjacentHashWithNullHash() {
        GeoHash.adjacentHash(null, Direction.RIGHT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void adjacentHashWithEmptyHash() {
        GeoHash.adjacentHash("", Direction.RIGHT);
    }

    @Test
    public void testAdjacentWithDifferentDirection() {
        assertEquals("stt", GeoHash.adjacentHash("STV", Direction.BOTTOM));
        assertEquals("gzz", GeoHash.adjacentHash("STV", Direction.TOP));
        assertEquals("stu", GeoHash.adjacentHash("STV", Direction.LEFT));
        assertEquals("bpb", GeoHash.adjacentHash("STV", Direction.RIGHT));
    }
}