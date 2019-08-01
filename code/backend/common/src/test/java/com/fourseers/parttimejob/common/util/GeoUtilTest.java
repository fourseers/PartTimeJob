package com.fourseers.parttimejob.common.util;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class GeoUtilTest {

    @Test
    public void getDistanceSquare() {
        GeoUtil.Point p1 = new GeoUtil.Point(new BigDecimal(100f), new BigDecimal(80f));
        GeoUtil.Point p2 = new GeoUtil.Point(new BigDecimal(103f), new BigDecimal(84f));
        assertEquals(new BigDecimal(25f), GeoUtil.getDistanceSquare(p1, p2));
    }

    @Test
    public void within2ParamsTrue() {
        GeoUtil.Point p1 = new GeoUtil.Point(new BigDecimal(100f), new BigDecimal(80f));
        GeoUtil.Point p2 = new GeoUtil.Point(new BigDecimal(100.0003f), new BigDecimal(80.0004f));
        assertTrue(GeoUtil.within(p1, p2));
    }

    @Test
    public void within2ParamsFalse() {
        GeoUtil.Point p1 = new GeoUtil.Point(new BigDecimal(100f), new BigDecimal(80f));
        GeoUtil.Point p2 = new GeoUtil.Point(new BigDecimal(100.003f), new BigDecimal(80.004f));
        assertFalse(GeoUtil.within(p1, p2));
    }

    @Test
    public void within3ParamsTrue() {
        GeoUtil.Point p1 = new GeoUtil.Point(new BigDecimal(100f), new BigDecimal(80f));
        GeoUtil.Point p2 = new GeoUtil.Point(new BigDecimal(100.0003f), new BigDecimal(80.0004f));
        BigDecimal range = new BigDecimal(0.001f);
        assertTrue(GeoUtil.within(p1, p2, range));
    }

    @Test
    public void within3ParamsFalse() {
        GeoUtil.Point p1 = new GeoUtil.Point(new BigDecimal(100f), new BigDecimal(80f));
        GeoUtil.Point p2 = new GeoUtil.Point(new BigDecimal(100.003f), new BigDecimal(80.004f));
        BigDecimal range = new BigDecimal(0.001f);
        assertFalse(GeoUtil.within(p1, p2, range));
    }
}
