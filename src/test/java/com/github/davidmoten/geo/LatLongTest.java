package com.github.davidmoten.geo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LatLongTest {

    private LatLong latLong;
    private LatLong expectedLatLong;


    @Before
    public void setUp() throws Exception {
        latLong = new LatLong(1.7976931348623157E308, 4.9E-324);
        expectedLatLong = new LatLong(1.7976931348623157E308 + 1, 4.9E-324 - 1);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getLat() {
        assertEquals(0, Double.compare(1.7976931348623157E308, latLong.getLat()));
    }

    @Test
    public void getLon() {
        assertEquals(0, Double.compare(4.9E-324, latLong.getLon()));
    }

    @Test
    public void add() {
        assertEquals(0, Double.compare(expectedLatLong.getLat(), latLong.add(1, -1).getLat()));
        assertEquals(0, Double.compare(expectedLatLong.getLon(), latLong.add(1, -1).getLon()));
    }

    @Test
    public void testToString() {
        assertEquals("LatLong [lat=1.7976931348623157E308, lon=4.9E-324]", latLong.toString());
    }
}