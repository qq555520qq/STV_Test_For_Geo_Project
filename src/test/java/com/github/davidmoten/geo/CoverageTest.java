package com.github.davidmoten.geo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.Double;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CoverageTest {

    private Set<String> hashes;
    private Set<String> hashesWithZero;
    private Coverage cover;
    private Coverage coverWithLongs;
    private Coverage coverWithZero;
    private CoverageLongs coverLongs;
    private long[] longHashes = {108, 598, 140};

    @Before
    public void setUp() throws Exception {
        hashes = new HashSet();
        hashesWithZero = new HashSet();
        hashes.add("037k4ry");
        hashes.add("37k4ry");
        hashes.add("7k4ry");
        cover = new Coverage(hashes, 2.2);
        coverWithZero = new Coverage(hashesWithZero, 2.2);
        coverLongs = new CoverageLongs(longHashes, 3, 1.7976931348623157E308);
        coverWithLongs = new Coverage(coverLongs);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals("Coverage [hashes=[37k4ry, 037k4ry, 7k4ry], ratio=2.2]", cover.toString());
        assertEquals("Coverage [hashes=[000000, 000000000006, 000000000008], ratio=1.7976931348623157E308]", coverWithLongs.toString());
    }

    @Test
    public void getHashLengthTest() throws Exception {
        assertEquals(6, cover.getHashLength());
        assertEquals(6, coverWithLongs.getHashLength());
    }

    @Test
    public void getHashLengthTestWithZeroSize() throws Exception {
        assertEquals(0, coverWithZero.getHashLength());
    }

    @Test
    public void getRatioTest() throws Exception {
        assertEquals(0, Double.compare(2.2, cover.getRatio()));
        assertEquals(0, Double.compare(1.7976931348623157E308, coverWithLongs.getRatio()));
    }

    @Test
    public void getHashesTest() throws Exception {
        assertEquals("[37k4ry, 037k4ry, 7k4ry]", cover.getHashes().toString());
        assertEquals("[000000, 000000000006, 000000000008]", coverWithLongs.getHashes().toString());
    }
}