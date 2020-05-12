package com.github.davidmoten.geo;

import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.github.davidmoten.geo.GeoHash.*;

import java.util.Set;

import static org.junit.Assert.*;

public class GeoHashTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
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
    public void testAdjacentWithDifferentDirectionAndSingularHash() {
        assertEquals("stt", GeoHash.adjacentHash("STV", Direction.BOTTOM));
        assertEquals("gzz", GeoHash.adjacentHash("STV", Direction.TOP));
        assertEquals("stu", GeoHash.adjacentHash("STV", Direction.LEFT));
        assertEquals("bpb", GeoHash.adjacentHash("STV", Direction.RIGHT));
    }

    @Test
    public void testAdjacentWithDifferentDirectionAndEvenHash() {
        assertEquals("stvu", GeoHash.adjacentHash("STVV", Direction.BOTTOM));
        assertEquals("stvvz8", GeoHash.adjacentHash("STVVxx", Direction.TOP));
        assertEquals("stvt", GeoHash.adjacentHash("STVV", Direction.LEFT));
        assertEquals("bpbp", GeoHash.adjacentHash("STVV", Direction.RIGHT));
    }

    @Test
    public void testAdjacentHashWithPositiveSteps() {
        assertEquals("stvb", GeoHash.adjacentHash("STVV", Direction.BOTTOM, 5));
        assertEquals("stvvzs", GeoHash.adjacentHash("STVVxx", Direction.TOP, 5));
        assertEquals("stut", GeoHash.adjacentHash("STVV", Direction.LEFT, 5));
        assertEquals("bpcp", GeoHash.adjacentHash("STVV", Direction.RIGHT, 5));
    }

    @Test
    public void testAdjacentHashWithNegativeSteps() {
        assertEquals("swjf", GeoHash.adjacentHash("STVV", Direction.BOTTOM, -5));
        assertEquals("stvvxd", GeoHash.adjacentHash("STVVxx", Direction.TOP, -5));
        assertEquals("bpcp", GeoHash.adjacentHash("STVV", Direction.LEFT, -5));
        assertEquals("stut", GeoHash.adjacentHash("STVV", Direction.RIGHT, -5));
    }

    @Test
    public void neighboursWithNegativeHash() {
        Set<String> neighbours = Sets.newHashSet("-1", "-3", "-4", "-d", "-5", "-e", "-7", "-9");
        assertEquals(neighbours, Sets.newHashSet(GeoHash.neighbours("-6")));
    }

    @Test
    public void neighboursWithPositiveHash() {
        Set<String> neighbours = Sets.newHashSet("stv49", "stufz", "stv4b", "stv42", "stufr", "stufx", "stv4c", "stv43");
        assertEquals(neighbours, Sets.newHashSet(GeoHash.neighbours("STV48")));
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void neighboursWithNegativeSpecialHash() {
        GeoHash.neighbours("-$#");
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void neighboursWithPositiveSpecialHash() {
        GeoHash.neighbours("@#$");
    }

    @Test
    public void hashLengthToCoverBoundingBoxWithSixteenBoundary() {
        assertEquals(12, GeoHash.hashLengthToCoverBoundingBox(1, 1, 1, 1));
        assertEquals(0, GeoHash.hashLengthToCoverBoundingBox(1, -1, 1, 1));
        assertEquals(0, GeoHash.hashLengthToCoverBoundingBox(1, 1, -1, 1));
        assertEquals(0, GeoHash.hashLengthToCoverBoundingBox(1, 1, 1, -1));
        assertEquals(0, GeoHash.hashLengthToCoverBoundingBox(1, -1, -1, 1));
        assertEquals(12, GeoHash.hashLengthToCoverBoundingBox(1, -1, 1, -1));
        assertEquals(0, GeoHash.hashLengthToCoverBoundingBox(1, -1, -1, -1));
        assertEquals(0, GeoHash.hashLengthToCoverBoundingBox(1, 1, -1, -1));

        assertEquals(12, GeoHash.hashLengthToCoverBoundingBox(-1, -1, -1, -1));
        assertEquals(0, GeoHash.hashLengthToCoverBoundingBox(-1, 1, -1, -1));
        assertEquals(0, GeoHash.hashLengthToCoverBoundingBox(-1, -1, 1, -1));
        assertEquals(0, GeoHash.hashLengthToCoverBoundingBox(-1, -1, -1, 1));
        assertEquals(0, GeoHash.hashLengthToCoverBoundingBox(-1, 1, 1, -1));
        assertEquals(12, GeoHash.hashLengthToCoverBoundingBox(-1, 1, -1, 1));
        assertEquals(0, GeoHash.hashLengthToCoverBoundingBox(-1, 1, 1, 1));
        assertEquals(0, GeoHash.hashLengthToCoverBoundingBox(-1, -1, 1, 1));
    }

    @Test
    public void widthDegreesWithNBiggerThanMAX_HASH_LENGTH() {
        assertEquals(4.190951585769653E-8, GeoHash.widthDegrees(13), 0.001);
    }

    @Test
    public void widthDegreesWithNSmallerThanMAX_HASH_LENGTH() {
        assertEquals(1.341104507446289E-6, GeoHash.widthDegrees(11), 0.001);
    }

    @Test
    public void heightDegreesWithNBiggerThanMAX_HASH_LENGTH() {
        assertEquals(4.190951585769653E-8, GeoHash.heightDegrees(13), 0.001);
    }

    @Test
    public void heightDegreesWithNSmallerThanMAX_HASH_LENGTH() {
        assertEquals(1.341104507446289E-6, GeoHash.heightDegrees(11), 0.001);
    }

    @Test
    public void coverBoundingBoxLongsWithSixteenBoundary() {
        assertEquals(CoverageLongs.class, GeoHash.coverBoundingBoxLongs(1, 1, 1, 1, 1).getClass());
        assertEquals(CoverageLongs.class, GeoHash.coverBoundingBoxLongs(1, -1, 1, 1, 1).getClass());
        assertEquals(CoverageLongs.class, GeoHash.coverBoundingBoxLongs(1, 1, -1, 1, 1).getClass());
        assertEquals(CoverageLongs.class, GeoHash.coverBoundingBoxLongs(1, -1, -1, 1, 1).getClass());
        assertEquals(CoverageLongs.class, GeoHash.coverBoundingBoxLongs(1, -1, 1, -1, 1).getClass());
        assertEquals(CoverageLongs.class, GeoHash.coverBoundingBoxLongs(1, -1, -1, -1, 1).getClass());
        assertEquals(CoverageLongs.class, GeoHash.coverBoundingBoxLongs(-1, -1, -1, -1, 1).getClass());
        assertEquals(CoverageLongs.class,GeoHash.coverBoundingBoxLongs(-1, -1, -1, 1, 1).getClass());
        assertEquals(CoverageLongs.class,GeoHash.coverBoundingBoxLongs(-1, 1, -1, 1, 1).getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void coverBoundingBoxLongsWithException1() {
        assertEquals(CoverageLongs.class, GeoHash.coverBoundingBoxLongs(1, 1, 1, -1, 1).getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void coverBoundingBoxLongsWithException2() {
        assertEquals(CoverageLongs.class, GeoHash.coverBoundingBoxLongs(1, 1, -1, -1, 1).getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void coverBoundingBoxLongsWithException3() {
        assertEquals(CoverageLongs.class, GeoHash.coverBoundingBoxLongs(-1, 1, -1, -1, 1).getClass());

    }

    @Test(expected = IllegalArgumentException.class)
    public void coverBoundingBoxLongsWithException4() {
        assertEquals(CoverageLongs.class,GeoHash.coverBoundingBoxLongs(-1, 1, 1, -1, 1).getClass());

    }

    @Test(expected = IllegalArgumentException.class)
    public void coverBoundingBoxLongsWithException5() {
        assertEquals(CoverageLongs.class,GeoHash.coverBoundingBoxLongs(-1, 1, 1, 1, 1).getClass());

    }

    @Test(expected = IllegalArgumentException.class)
    public void coverBoundingBoxLongsWithException6() {
        assertEquals(CoverageLongs.class,GeoHash.coverBoundingBoxLongs(-1, -1, 1, 1, 1).getClass());
        assertEquals(CoverageLongs.class,GeoHash.coverBoundingBoxLongs(-1, -1, 1, -1, 1).getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void coverBoundingBoxLongsWithException7() {
        assertEquals(CoverageLongs.class,GeoHash.coverBoundingBoxLongs(-1, -1, 1, -1, 1).getClass());
    }

    @Test
    public void coverBoundingBoxMaxHashesWithoutExceptionAndEnoughMaxHashes() {
        assertEquals(Coverage.class,GeoHash.coverBoundingBoxMaxHashes(1, 1, 1, 1, 2147483647).getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void coverBoundingBoxMaxHashesWithExceptionAndEnoughMaxHashes() {
        assertEquals(Coverage.class,GeoHash.coverBoundingBoxMaxHashes(1, 1, 1, -1, 2147483647).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void coverBoundingBoxMaxHashesWithoutExceptionAndNotEnoughMaxHashes() {
        assertEquals(Coverage.class,GeoHash.coverBoundingBoxMaxHashes(1, 1, 1, 1, -2147483648).getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void coverBoundingBoxMaxHashesWithExceptionAndNotEnoughMaxHashes() {
        assertEquals(Coverage.class,GeoHash.coverBoundingBoxMaxHashes(1, 1, 1, -1, -2147483648).getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void coverBoundingBoxMaxHashesWithExceptionAndNotEnoughMaxHashes2() {
        assertEquals(Coverage.class,GeoHash.coverBoundingBoxMaxHashes(-1, 1, 1, -1, -2147483648).getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void coverBoundingBoxMaxHashesWithExceptionAndNotEnoughMaxHashes3() {
        assertEquals(Coverage.class,GeoHash.coverBoundingBoxMaxHashes(-1, 1, 1, 1, -2147483648).getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void coverBoundingBoxMaxHashesWithExceptionAndEnoughMaxHashes2() {
        assertEquals(Coverage.class,GeoHash.coverBoundingBoxMaxHashes(-1, 1, 1, -1, 2147483647).getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void coverBoundingBoxMaxHashesWithExceptionAndEnoughMaxHashes3() {
        assertEquals(Coverage.class,GeoHash.coverBoundingBoxMaxHashes(-1, 1, 1, 1, 2147483647).getClass());
    }

    @Test
    public void right() {
        assertEquals("28",GeoHash.right("22"));
        assertEquals("b",GeoHash.right("i"));
        assertEquals("c",GeoHash.right("b"));
        assertEquals("f1",GeoHash.right("cc"));
    }

    @Test
    public void left() {
        assertEquals("20",GeoHash.left("22"));
        assertEquals("r",GeoHash.left("2"));
        assertEquals("p",GeoHash.left("0"));
        assertEquals("0c",GeoHash.left("11"));
    }

    @Test
    public void top() {
        assertEquals("ac",GeoHash.top("ab"));
        assertEquals("u",GeoHash.top("b"));
        assertEquals("r",GeoHash.top("p"));
        assertEquals("u0",GeoHash.top("bp"));
    }

    @Test
    public void bottom() {
        assertEquals("10",GeoHash.bottom("11"));
        assertEquals("1",GeoHash.bottom("3"));
        assertEquals("h",GeoHash.bottom("0"));
        assertEquals("hp",GeoHash.bottom("00"));
    }
}