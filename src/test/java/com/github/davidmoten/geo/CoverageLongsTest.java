package com.github.davidmoten.geo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.Double;
import java.lang.Long;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CoverageLongsTest {

    private long[] hashes = {108, 598, 140};
    private CoverageLongs coverLongs;
    private CoverageLongs coverLongsWithZeroCount;

    @Before
    public void setUp() throws Exception {
        coverLongs = new CoverageLongs(hashes, 3, 2.2);
        coverLongsWithZeroCount = new CoverageLongs(hashes, 0, 2.2);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getCountTest() throws Exception {
        assertEquals(3, coverLongs.getCount());
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals("Coverage [hashes=", coverLongsWithZeroCount.toString().substring(0,17));
        assertEquals(" ratio=2.2]", coverLongsWithZeroCount.toString().split(",")[1]);
    }

    @Test
    public void getHashLengthTest() throws Exception {
        assertEquals(12, coverLongs.getHashLength());
    }

    @Test
    public void getHashLengthTestWithZeroSize() throws Exception {
        assertEquals(0, coverLongsWithZeroCount.getHashLength());
    }

    @Test
    public void getRatioTest() throws Exception {
        assertEquals(0, Double.compare(2.2, coverLongs.getRatio()));
    }

    @Test
    public void getHashesTest() throws Exception {
        assertArrayEquals(hashes, coverLongs.getHashes());
    }
}