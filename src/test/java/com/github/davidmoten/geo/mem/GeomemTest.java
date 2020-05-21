package com.github.davidmoten.geo.mem;

import com.google.common.base.Optional;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeomemTest {

    private Info<String, String> expectInfo;
    private Geomem<String, String> gene;

    @Test
    public void testFindWithoutAddingData() {
        Geomem<String, String> gene = new Geomem<String, String>();
        gene.find(-5, 100, -45, 170, 0, 5);
    }

    @Test
    public void testFindWithAddingData() {
        Geomem<String, String> gene = new Geomem<String, String>();
        gene.add(-15, 120, 500, "A1", Optional.<String>absent());
        Iterable<Info<String, String>> expectInfo = gene.find(-5, 100, -45, 170, 0, 1000);
        assertEquals(-15, expectInfo.iterator().next().lat(), 0.00001);
        assertEquals(120, expectInfo.iterator().next().lon(), 0.00001);
        assertEquals(500L, expectInfo.iterator().next().time());
        assertEquals("A1", expectInfo.iterator().next().value());
        assertFalse(expectInfo.iterator().next().id().isPresent());
    }

    @Test
    public void addToMapWithAddingSomeKey() {
        Geomem<String, String> gene = new Geomem<String, String>();
        Info<String, String> info = new Info<String, String>(-15, 120, 500, "A2", Optional.of("A"));
        gene.add(info);
        Iterable<Info<String, String>> expectInfo = gene.find(-5, 100, -45, 170, 0, 1000);
        assertEquals(-15, expectInfo.iterator().next().lat(), 0.00001);
        assertEquals(120, expectInfo.iterator().next().lon(), 0.00001);
        assertEquals(500L, expectInfo.iterator().next().time());
        assertEquals("A2", expectInfo.iterator().next().value());
        assertTrue(expectInfo.iterator().next().id().isPresent());
        Info<String, String> info2 = new Info<String, String>(-15, 120, 500, "A2", Optional.of("A"));
        gene.add(info);
        Iterable<Info<String, String>> expectInfo2 = gene.find(-5, 100, -45, 170, 0, 1000);
        assertEquals(-15, expectInfo.iterator().next().lat(), 0.00001);
        assertEquals(120, expectInfo.iterator().next().lon(), 0.00001);
        assertEquals(500L, expectInfo.iterator().next().time());
        assertEquals("A2", expectInfo.iterator().next().value());
        assertTrue(expectInfo.iterator().next().id().isPresent());
    }

    @Test
    public void addToMapWithoutAddingSomeKey() {
        Geomem<String, String> gene = new Geomem<String, String>();
        Info<String, String> info = new Info<String, String>(-15, 120, 500, "A2", Optional.of("A"));
        gene.add(info);
        Iterable<Info<String, String>> expectInfo = gene.find(-5, 100, -45, 170, 0, 1000);
        assertEquals(-15, expectInfo.iterator().next().lat(), 0.00001);
        assertEquals(120, expectInfo.iterator().next().lon(), 0.00001);
        assertEquals(500L, expectInfo.iterator().next().time());
        assertEquals("A2", expectInfo.iterator().next().value());
        assertTrue(expectInfo.iterator().next().id().isPresent());
    }
}