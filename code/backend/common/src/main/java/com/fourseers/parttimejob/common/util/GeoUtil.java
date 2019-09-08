package com.fourseers.parttimejob.common.util;

import java.math.BigDecimal;

public class GeoUtil {

    public static class Point {
        private BigDecimal longitude;
        private BigDecimal latitude;

        public Point(BigDecimal longitude, BigDecimal latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public BigDecimal getLongitude() {
            return longitude;
        }

        public void setLongitude(BigDecimal longitude) {
            this.longitude = longitude;
        }

        public BigDecimal getLatitude() {
            return latitude;
        }

        public void setLatitude(BigDecimal latitude) {
            this.latitude = latitude;
        }
    }

    public static BigDecimal getDistanceSquare(Point p1, Point p2) {
        return (p1.getLatitude().subtract(p2.getLatitude()).pow(2)
                .add((p1.getLongitude().subtract(p2.getLongitude()).pow(2))));
    }

    public static boolean within(Point actual, Point dest, BigDecimal range) {
        return getDistanceSquare(actual, dest).compareTo(range.pow(2)) <= 0;
    }

    public static boolean within(Point actual, Point dest) {
        // 0.001 in longitude / latitude is about 100m
        // change to 0.01(1km) here for test purposes.
        return within(actual, dest, new BigDecimal(0.01f));
    }
}
